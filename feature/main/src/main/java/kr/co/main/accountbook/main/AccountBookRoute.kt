package kr.co.main.accountbook.main


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import kr.co.domain.entity.AccountBookEntity
import kr.co.main.accountbook.model.DATE_FORMAT_PATTERN
import kr.co.main.accountbook.model.DateRangeOption
import kr.co.main.accountbook.model.MAX_CATEGORY_COUNT
import kr.co.main.accountbook.model.formatNumber
import kr.co.main.accountbook.model.getColorList
import kr.co.main.accountbook.model.getDisplay
import kr.co.main.navigation.AccountBookRoute.ACCOUNT_KEY
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Arrowright
import kr.co.ui.icon.dreamicon.DatePicker
import kr.co.ui.icon.dreamicon.Edit
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamTopAppBar
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
internal fun AccountBookRoute(
    viewModel: AccountBookViewModel = hiltViewModel(),
    navigationToRegister: () -> Unit,
    navigationToContent: (Long?) -> Unit,
    navController: NavController,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    val reinitialize =
        navController.currentBackStackEntry?.savedStateHandle?.get<Boolean>(ACCOUNT_KEY) ?: false

    LaunchedEffect(reinitialize) {
        if (reinitialize) {
            viewModel.fetchAccountBooks(
                category = "",
                sort = AccountBookEntity.SortOrder.EARLIEST,
                transactionType = null
            )
        }
        navController.currentBackStackEntry?.savedStateHandle?.set(ACCOUNT_KEY, false)
    }

    AccountBookScreen(
        state = state,
        isLoading = isLoading,
        navigationToRegister = navigationToRegister,
        navigationToContent = navigationToContent,
        onUpdateDateRangeOption = { viewModel.updateDateRangeOption(it) },
        onUpdateDateRange = { startDate, endDate -> viewModel.updateDateRange(startDate, endDate) },
        onUpdateGraphTransactionType = { viewModel.updateGraphTransactionType(it) },
        onUpdateCategory = { viewModel.updateCategory(it) },
        onUpdateSortOrder = { viewModel.updateSortOrder(it) },
        onUpdateTransactionType = { viewModel.updateTransactionType(it) },
        onUpdatePage = { viewModel.updatePage(it) },
        onRefresh = viewModel::refreshItem
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountBookScreen(
    state: AccountBookViewModel.State = AccountBookViewModel.State(),
    isLoading: Boolean,
    navigationToRegister: () -> Unit,
    navigationToContent: (Long?) -> Unit,
    onUpdateDateRangeOption: (DateRangeOption) -> Unit = {},
    onUpdateDateRange: (String, String) -> Unit = { _, _ -> },
    onUpdateGraphTransactionType: (AccountBookEntity.TransactionType) -> Unit = {},
    onUpdateCategory: (AccountBookEntity.Category?) -> Unit = {},
    onUpdateSortOrder: (AccountBookEntity.SortOrder) -> Unit = {},
    onUpdateTransactionType: (AccountBookEntity.TransactionType?) -> Unit = {},
    onUpdatePage: (Long) -> Unit = {},
    onRefresh: () -> Unit
) {
    val refreshState = rememberPullToRefreshState()
    if (refreshState.isRefreshing) {
        onRefresh()
        refreshState.endRefresh()
    }

    Surface(
        color = MaterialTheme.colors.gray9
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            DreamTopAppBar(
                title = "내 장부",
                actions = {
                    IconButton(onClick = navigationToRegister) {
                        Icon(
                            imageVector = DreamIcon.Edit,
                            contentDescription = "AccountBook Register"
                        )
                    }
                }
            )
            Box(modifier = Modifier.nestedScroll(refreshState.nestedScrollConnection)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    item {
                        CalendarSection(
                            dateRangeOption = state.dateRangeOption,
                            start = state.start,
                            end = state.end,
                            onDateRangeOptionSelected = {
                                onUpdateDateRangeOption(it)
                            },
                            onDaysInRangeChange = { startDate, endDate ->
                                onUpdateDateRange(startDate.toString(), endDate.toString())
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                                .padding(Paddings.extra)
                        ) {
                            val amountPercent =
                                if (state.graphTransactionType == AccountBookEntity.TransactionType.EXPENSE)
                                    state.expensePercent else state.revenuePercent

                            GraphSection(
                                graphTransactionType = state.graphTransactionType,
                                totalAmount = if (state.graphTransactionType == AccountBookEntity.TransactionType.EXPENSE)
                                    state.totalExpense else state.totalRevenue,
                                totalCost = state.totalCost,
                                percents = amountPercent?.map { it.percent },
                                categories = amountPercent?.map { it.category.getDisplay() },
                                onGraphTransactionTypeSelected = {
                                    onUpdateGraphTransactionType(it)
                                }
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = Color.White, shape = RoundedCornerShape(
                                        topStart = 12.dp,
                                        topEnd = 12.dp,
                                        bottomStart = 0.dp,
                                        bottomEnd = 0.dp
                                    )
                                )
                                .padding(
                                    start = Paddings.extra,
                                    end = Paddings.extra,
                                    top = Paddings.extra
                                )
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                SelectorSection(
                                    transactionType = state.transactionType,
                                    category = state.category,
                                    categories = state.categories,
                                    sortOrder = state.sort,
                                    onCategoryChange = { onUpdateCategory(it) },
                                    onSortOrderChange = { onUpdateSortOrder(it) },
                                    onTransactionChange = { onUpdateTransactionType(it) }
                                )
                            }
                        }
                    }

                    if (state.accountBooks.isEmpty()) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(
                                            topStart = 0.dp,
                                            topEnd = 0.dp,
                                            bottomStart = 12.dp,
                                            bottomEnd = 12.dp
                                        )
                                    )
                                    .padding(Paddings.extra),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "장부를 작성해주세요!",
                                    style = MaterialTheme.typo.body2,
                                    color = MaterialTheme.colors.gray4,
                                    modifier = Modifier
                                        .clickable {
                                            navigationToRegister()
                                        }
                                        .padding(
                                            vertical = Paddings.xlarge,
                                            horizontal = Paddings.medium
                                        )
                                )
                                Icon(
                                    imageVector = DreamIcon.Arrowright,
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.gray5,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .align(Alignment.CenterVertically)
                                )
                            }
                        }
                    } else {
                        itemsIndexed(
                            state.accountBooks
                        ) { index, data ->
                            val lastIndex = state.accountBooks.lastIndex
                            if (index == lastIndex && state.hasNext!!) {
                                if (isLoading.not()) {
                                    onUpdatePage(state.accountBooks[lastIndex].id)
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(
                                            topStart = 0.dp,
                                            topEnd = 0.dp,
                                            bottomStart = if (index == lastIndex) 12.dp else 0.dp,
                                            bottomEnd = if (index == lastIndex) 12.dp else 0.dp
                                        )
                                    )
                            ) {
                                AccountBookItem(
                                    accountBook = data,
                                    onItemClicked = { navigationToContent(data.id) }
                                )

                                if (isLoading && index == lastIndex) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(Paddings.xlarge),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(
                                            color = MaterialTheme.colors.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                PullToRefreshContainer(
                    modifier = Modifier.align(Alignment.TopCenter),
                    state = refreshState,
                    containerColor = MaterialTheme.colors.gray9,
                    contentColor = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
private fun CalendarSection(
    dateRangeOption: DateRangeOption,
    start: String,
    end: String,
    onDateRangeOptionSelected: (DateRangeOption) -> Unit,
    onDaysInRangeChange: (LocalDate, LocalDate) -> Unit
) {
    var bottomSheetState by remember { mutableStateOf(false) }

    val startDate = LocalDate.parse(start)
    val endDate = LocalDate.parse(end)
    val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { bottomSheetState = true }
    ) {
        Text(
            text = "${startDate.format(formatter)} ~ ${endDate.format(formatter)}",
            modifier = Modifier.padding(end = Paddings.xlarge),
            style = MaterialTheme.typo.pageName,
            color = MaterialTheme.colors.gray1
        )
        Icon(
            imageVector = DreamIcon.DatePicker,
            contentDescription = null,
            tint = MaterialTheme.colors.gray4
        )
    }

    if (bottomSheetState) {
        AccountBookCalendarBottomSheet(
            startDate = if (dateRangeOption == DateRangeOption.OTHER) startDate else null,
            endDate = if (dateRangeOption == DateRangeOption.OTHER) endDate else null,
            selectedOption = dateRangeOption,
            onSelectedListener = { selectedStartDate, selectedEndDate, newOption ->
                val newStartDate = LocalDate.parse(selectedStartDate)
                val newEndDate = LocalDate.parse(selectedEndDate)
                bottomSheetState = false
                onDaysInRangeChange(newStartDate, newEndDate)
                onDateRangeOptionSelected(newOption)
            },
            dismissBottomSheet = { bottomSheetState = false }
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun GraphSection(
    graphTransactionType: AccountBookEntity.TransactionType,
    totalAmount: Long? = 0L,
    totalCost: Long? = 0L,
    percents: List<Float>?,
    categories: List<String>?,
    onGraphTransactionTypeSelected: (AccountBookEntity.TransactionType) -> Unit
) {
    val (showAll, setShowAll) = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (percents != null && categories != null && percents.size == categories.size) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AccountBookOptionButton(
                    option = "지출",
                    isSelected = graphTransactionType == AccountBookEntity.TransactionType.EXPENSE,
                    onSelected = {
                        onGraphTransactionTypeSelected(AccountBookEntity.TransactionType.EXPENSE)
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                AccountBookOptionButton(
                    option = "수입",
                    isSelected = graphTransactionType == AccountBookEntity.TransactionType.REVENUE,
                    onSelected = {
                        onGraphTransactionTypeSelected(AccountBookEntity.TransactionType.REVENUE)
                    }
                )
            }
            if (percents.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = Paddings.extra),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(
                            id = kr.co.nbdream.core.ui.R.drawable.img_graph
                        ),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(Paddings.extra))
                    Text(
                        text = if (graphTransactionType == AccountBookEntity.TransactionType.EXPENSE)
                            "이번 달은 어디서 많이 썼을까?"
                        else
                            "이번 달은 어디서 많이 벌었을까?",
                        style = MaterialTheme.typo.body2,
                        color = MaterialTheme.colors.gray5
                    )
                }
            } else {
                val colors = percents.size.getColorList()
                val total = percents.sum()
                val categoryPercent = categories.zip(percents)
                    .sortedByDescending { it.second }
                    .mapIndexed { index, (category, percent) ->
                        Triple(
                            category,
                            String.format("%.1f", (percent / total) * 100),
                            if (index < colors.size) colors[index] else MaterialTheme.colors.gray7
                        )
                    }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(top = Paddings.extra)
                ) {
                    AccountBookGraph(
                        modifier = Modifier.fillMaxSize(),
                        colors = colors,
                        data = categoryPercent.map { it.second.toFloat() },
                        graphHeight = 150
                    )
                }

                Column(
                    modifier = Modifier.padding(
                        top = Paddings.xxlarge,
                        bottom = Paddings.xlarge
                    )
                ) {
                    val itemsToShow =
                        if (showAll) categoryPercent else categoryPercent.take(MAX_CATEGORY_COUNT)

                    itemsToShow.chunked(2).forEach { rowItems ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                GraphCategoryItem(
                                    category = rowItems.first().first,
                                    percent = rowItems.first().second,
                                    color = rowItems.first().third
                                )
                            }
                            if (rowItems.size > 1) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Spacer(modifier = Modifier.width(16.dp))
                                    GraphCategoryItem(
                                        category = rowItems[1].first,
                                        percent = rowItems[1].second,
                                        color = rowItems[1].third
                                    )
                                }
                            }
                        }
                    }
                }

                if (categoryPercent.size > MAX_CATEGORY_COUNT) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { setShowAll(!showAll) },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (showAll) "접기" else "더보기",
                            style = MaterialTheme.typo.h3,
                            color = MaterialTheme.colors.gray5,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = if (showAll) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = MaterialTheme.colors.gray5,
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(Paddings.xlarge))

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colors.gray9,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(Paddings.xlarge),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "${formatNumber(totalAmount ?: 0L)}원",
                            style = MaterialTheme.typo.h3,
                            color = MaterialTheme.colors.gray1,
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "합계: ${formatNumber(totalCost ?: 0L)}원",
                            style = MaterialTheme.typo.h3,
                            color = MaterialTheme.colors.gray1,
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun GraphCategoryItem(
    category: String,
    percent: String,
    color: Color
) {
    if (category.length < 4) {
        GraphCategoryRowItem(category, percent, color)
    } else {
        GraphCategoryColumnItem(category, percent, color)
    }
}

@Composable
private fun GraphCategoryColumnItem(
    category: String,
    percent: String,
    color: Color
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        color,
                        CircleShape
                    )
            )
            Text(
                text = category,
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray1,
                maxLines = 1,
                modifier = Modifier.padding(start = Paddings.medium)
            )
        }
        Text(
            text = "$percent%",
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray5,
            maxLines = 1,
            modifier = Modifier.padding(start = Paddings.xxlarge, top = Paddings.medium)
        )
    }
}

@Composable
private fun GraphCategoryRowItem(
    category: String,
    percent: String,
    color: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(
                    color,
                    CircleShape
                )
        )
        Text(
            text = category,
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray1,
            maxLines = 1,
            modifier = Modifier.padding(start = Paddings.medium)
        )
        Text(
            text = "$percent%",
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray5,
            maxLines = 1,
            modifier = Modifier.padding(start = Paddings.medium)
        )
    }
}

@Composable
private fun FilterSelector(
    transactionType: AccountBookEntity.TransactionType?,
    onTransactionChange: (AccountBookEntity.TransactionType?) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = Paddings.xextra)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AccountBookOptionButton(
                option = "전체",
                isSelected = transactionType == null,
                onSelected = {
                    onTransactionChange(null)
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            AccountBookOptionButton(
                option = "지출",
                isSelected = transactionType == AccountBookEntity.TransactionType.EXPENSE,
                onSelected = {
                    onTransactionChange(AccountBookEntity.TransactionType.EXPENSE)
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            AccountBookOptionButton(
                option = "수입",
                isSelected = transactionType == AccountBookEntity.TransactionType.REVENUE,
                onSelected = {
                    onTransactionChange(AccountBookEntity.TransactionType.REVENUE)
                }
            )
        }
    }
}

@Composable
fun AccountBookOptionButton(
    width: Dp = 80.dp,
    height: Dp = 32.dp,
    option: String,
    isSelected: Boolean,
    onSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .background(
                color = if (isSelected) MaterialTheme.colors.gray4 else MaterialTheme.colors.gray9,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onSelected(option) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = option,
            color = if (isSelected) MaterialTheme.colors.white else MaterialTheme.colors.gray4,
            style = MaterialTheme.typo.h3
        )
    }
}

@Composable
private fun SelectorSection(
    transactionType: AccountBookEntity.TransactionType?,
    category: AccountBookEntity.Category?,
    categories: List<AccountBookEntity.Category?>?,
    sortOrder: AccountBookEntity.SortOrder,
    onCategoryChange: (AccountBookEntity.Category?) -> Unit,
    onSortOrderChange: (AccountBookEntity.SortOrder) -> Unit,
    onTransactionChange: (AccountBookEntity.TransactionType?) -> Unit
) {
    Column {
        CategorySelector(category, categories, onCategoryChange = onCategoryChange)
        FilterSelector(transactionType, onTransactionChange)
        SortOrderSelector(sortOrder, onSortOrderChange)
    }
}

@Composable
private fun CategorySelector(
    category: AccountBookEntity.Category?,
    categories: List<AccountBookEntity.Category?>?,
    onCategoryChange: (AccountBookEntity.Category?) -> Unit
) {
    var bottomSheetState by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(32.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.gray6,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { bottomSheetState = true }
    ) {
        Text(
            text = category?.getDisplay() ?: "전체",
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray2,
            modifier = Modifier.padding(start = Paddings.medium, end = Paddings.large),
        )
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null,
            tint = MaterialTheme.colors.gray1,
            modifier = Modifier.padding(end = Paddings.medium),
        )
    }

    if (bottomSheetState) {
        categories?.let {
            AccountBookCategoryBottomSheet(
                categories = categories,
                onSelectedListener = { selectedCategory ->
                    onCategoryChange(selectedCategory)
                    bottomSheetState = false
                },
                dismissBottomSheet = { bottomSheetState = false }
            )
        }
    }
}


@Composable
private fun SortOrderSelector(
    sortOrder: AccountBookEntity.SortOrder,
    onSortOrderChange: (AccountBookEntity.SortOrder) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = { onSortOrderChange(AccountBookEntity.SortOrder.EARLIEST) })
        ) {
            Text(
                text = "최신순",
                style = MaterialTheme.typo.body2,
                color = if (sortOrder == AccountBookEntity.SortOrder.EARLIEST) MaterialTheme.colors.primary else MaterialTheme.colors.gray4,
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = { onSortOrderChange(AccountBookEntity.SortOrder.OLDEST) })
        ) {
            Text(
                text = "과거순",
                style = MaterialTheme.typo.body2,
                color = if (sortOrder == AccountBookEntity.SortOrder.OLDEST) MaterialTheme.colors.primary else MaterialTheme.colors.gray4,
            )
        }
    }
}

@Composable
private fun AccountBookItem(
    accountBook: AccountBookViewModel.State.AccountBook,
    onItemClicked: (Long) -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = Paddings.xlarge,
                    horizontal = Paddings.extra
                )
                .clickable {
                    onItemClicked(accountBook.id)
                }
        ) {
            Column(modifier = Modifier.weight(2f)) {
                Text(
                    text = "${accountBook.month ?: 0}월 ${accountBook.day ?: 0}일",
                    style = MaterialTheme.typo.body2,
                    color = MaterialTheme.colors.gray1
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = accountBook.dayName ?: "",
                    style = MaterialTheme.typo.body2,
                    color = MaterialTheme.colors.gray1
                )
            }
            Column(modifier = Modifier.weight(3f)) {
                if (!accountBook.title.isNullOrBlank()) {
                    Text(
                        text = accountBook.title,
                        style = MaterialTheme.typo.body1,
                        color = MaterialTheme.colors.gray1
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Text(
                    text = "${formatNumber(accountBook.amount ?: 0)}원",
                    style = MaterialTheme.typo.body1,
                    color = MaterialTheme.colors.gray1
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = accountBook.category.getDisplay(),
                    style = MaterialTheme.typo.label,
                    color = MaterialTheme.colors.gray5,
                )
            }

            Image(
                painter = rememberAsyncImagePainter(accountBook.imageUrl?.first()),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp),
                contentScale = ContentScale.Crop
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Paddings.extra),
            thickness = 1.dp,
            color = MaterialTheme.colors.gray8
        )
    }
}

@Composable
internal fun CircleProgress() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.black.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary
        )
    }
}