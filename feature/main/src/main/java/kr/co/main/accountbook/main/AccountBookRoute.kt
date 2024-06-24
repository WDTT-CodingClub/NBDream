package kr.co.main.accountbook.main


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import kr.co.domain.entity.AccountBookEntity
import kr.co.main.accountbook.model.getDisplay
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Edit
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamTopAppBar
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
internal fun AccountBookRoute(
    viewModel: AccountBookViewModel = hiltViewModel(),
    navigationToRegister: () -> Unit,
    navigationToContent: (Long?) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

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

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                item {
                    CalendarSection(
                        start = state.start,
                        end = state.end
                    ) { startDate, endDate ->
                        viewModel.updateDateRange(startDate.toString(), endDate.toString())
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                            .padding(Paddings.extra)
                    ) {
                        GraphSection(
                            state = state,
                            showingExpenses = true
                        ) {

                        }
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
                            .padding(Paddings.extra)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            SelectorSection(
                                transactionType = state.transactionType,
                                categories = state.categories,
                                sortOrder = state.sort,
                                onCategoryChange = { viewModel.updateCategory(it) },
                                onSortOrderChange = { viewModel.updateSortOrder(it) },
                                onTransactionChange = { viewModel.updateTransactionType(it) }
                            )
                        }
                    }
                }

                if (state.accountBooks.isEmpty()) {
                    item {
                        Box(
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
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "등록된 데이터가 없습니다.",
                                style = MaterialTheme.typo.body1,
                                color = MaterialTheme.colors.gray5,
                                modifier = Modifier
                                    .padding(
                                        vertical = Paddings.xlarge,
                                        horizontal = Paddings.extra
                                    )
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
                                viewModel.updatePage(state.accountBooks[lastIndex].id)
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
                                        .padding(16.dp),
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
        }
    }
}


@Composable
private fun CalendarSection(
    start: String,
    end: String,
    onDaysInRangeChange: (LocalDate, LocalDate) -> Unit
) {
    var bottomSheetState by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("1개월") }

    val startDate = LocalDate.parse(start)
    val endDate = LocalDate.parse(end)
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = Paddings.xlarge)
            .clickable { bottomSheetState = true }
    ) {
        Text(
            text = "${startDate.format(formatter)} ~ ${endDate.format(formatter)}",
            modifier = Modifier.padding(end = Paddings.xlarge),
            style = MaterialTheme.typo.pageName, // TODO style 변경
            color = MaterialTheme.colors.gray1
        )
        Icon(
            imageVector = Icons.Filled.DateRange,
            contentDescription = null,
            tint = MaterialTheme.colors.gray4
        )
    }

    if (bottomSheetState) {
        AccountBookCalendarBottomSheet(
            selectedOption = selectedOption,
            onOptionSelected = { newOption ->
                selectedOption = newOption
            },
            onSelectedListener = { selectedStartDate, selectedEndDate ->
                val newStartDate = LocalDate.parse(selectedStartDate)
                val newEndDate = LocalDate.parse(selectedEndDate)
                bottomSheetState = false
                onDaysInRangeChange(newStartDate, newEndDate)
            },
            dismissBottomSheet = { bottomSheetState = false }
        )
    }
}

@Composable
private fun GraphSection(
    state: AccountBookViewModel.State,
    showingExpenses: Boolean,
    onToggleTypeClick: () -> Unit
) {
    val transactionType = if (showingExpenses) {
        AccountBookEntity.TransactionType.EXPENSE
    } else {
        AccountBookEntity.TransactionType.REVENUE
    }
    val filteredData = state.accountBooks.filter { it.transactionType == transactionType }
    val groupedData = filteredData.groupBy { it.category }
    val data = groupedData.values.map { group ->
        group.sumOf { it.amount?.toDouble() ?: 0.0 }.toFloat()
    }
    val categories = groupedData.keys.map { it.getDisplay() }
    val totalAmount = if (transactionType == AccountBookEntity.TransactionType.EXPENSE) {
        formatNumber(state.totalExpense ?: 0)
    } else {
        "-${formatNumber(state.totalRevenue ?: 0)}"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (filteredData.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "등록된 데이터가 없습니다.",
                    style = MaterialTheme.typo.body1,
                    color = MaterialTheme.colors.gray5
                )
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AccountBookOptionButton(
                    option = "지출",
                    isSelected = showingExpenses,
                    onSelected = {
                        onToggleTypeClick()
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                AccountBookOptionButton(
                    option = "수입",
                    isSelected = !showingExpenses,
                    onSelected = {
                        onToggleTypeClick()
                    }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(top = Paddings.extra),
            ) {
                AccountBookGraph(
                    data = data,
                    categories = categories,
                    modifier = Modifier.fillMaxSize(),
                    graphHeight = 150
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Paddings.extra),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${totalAmount}원",
                    style = MaterialTheme.typo.h3,
                    color = MaterialTheme.colors.gray1
                )
                Text(
                    text = "합계: ${formatNumber(state.totalCost ?: 0L)}원",
                    style = MaterialTheme.typo.h3,
                    color = MaterialTheme.colors.gray1
                )
            }
        }
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
    categories: List<String>?,
    sortOrder: AccountBookEntity.SortOrder,
    onCategoryChange: (String) -> Unit,
    onSortOrderChange: (AccountBookEntity.SortOrder) -> Unit,
    onTransactionChange: (AccountBookEntity.TransactionType?) -> Unit
) {
    Column {
        CategorySelector(categories, onCategoryChange = onCategoryChange)
        FilterSelector(transactionType, onTransactionChange)
        SortOrderSelector(sortOrder, onSortOrderChange)
    }
}

@Composable
private fun CategorySelector(state: List<String>?, onCategoryChange: (String) -> Unit) {
    var bottomSheetState by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(120.dp)
            .height(32.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.gray6,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { bottomSheetState = true }
    ) {
        Text(
            text = "카테고리",
            modifier = Modifier.padding(end = Paddings.xlarge),
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray2
        )
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null,
            tint = MaterialTheme.colors.gray1
        )
    }

    if (bottomSheetState) {
        state?.let {
            AccountBookCategoryBottomSheet(
                onSelectedListener = { selectedCategory ->
                    onCategoryChange(selectedCategory.name)
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
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${accountBook.month ?: 0}월 ${accountBook.day ?: 0}일",
                    style = MaterialTheme.typo.body2,
                    color = MaterialTheme.colors.gray1
                )
                Text(
                    text = accountBook.dayName ?: "",
                    style = MaterialTheme.typo.body2,
                    color = MaterialTheme.colors.gray1
                )
            }
            Column(modifier = Modifier.weight(2f)) {
                Text(
                    text = accountBook.title ?: "",
                    style = MaterialTheme.typo.body1,
                    color = MaterialTheme.colors.gray1
                )
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

            val imageUrl = accountBook.imageUrl.firstOrNull()
            if (imageUrl != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(56.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .size(56.dp)
                        .background(MaterialTheme.colors.gray8)
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colors.gray8
        )
    }
}

fun formatNumber(number: Long): String {
    val formatter = NumberFormat.getNumberInstance()
    return formatter.format(number)
}
