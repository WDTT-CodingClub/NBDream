package kr.co.main.accountbook.main


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import kr.co.domain.entity.SortOrder
import kr.co.main.accountbook.model.CategoryDisplayMapper
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
                .padding(horizontal = Paddings.xlarge)
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

            CalendarSection(
                start = state.start,
                end = state.end
            ) { startDate, endDate ->
                viewModel.updateDateRange(startDate.toString(), endDate.toString())
            }

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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                    .padding(Paddings.extra)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SelectorSection(
                        state = state.categories,
                        sortOrder = state.sort,
                        onCategoryChange = { viewModel.updateCategory(it) },
                        onSortOrderChange = { viewModel.updateSortOrder(it) },
                        onTransactionChange = { viewModel.updateTransactionType(it) }
                    )

                    AccountBookList(
                        hasNext = state.hasNext ?: false,
                        accountBooks = state.accountBooks,
                        onPageChange = { id -> viewModel.updatePage(id) },
                        onItemClicked = { id -> navigationToContent(id) },
                        isLoading = isLoading
                    )
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
    val categories = groupedData.keys.map { CategoryDisplayMapper.getDisplay(it) }
    val totalAmount = if (transactionType == AccountBookEntity.TransactionType.EXPENSE) {
        formatNumber(state.totalExpense ?: 0)
    } else {
        "-${formatNumber(state.totalRevenue ?: 0)}"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
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


@Composable
private fun FilterSelector(
    onTransactionChange: (AccountBookEntity.TransactionType?) -> Unit
) {
    var selectedOption by remember { mutableStateOf("전체") }
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
                isSelected = selectedOption == "전체",
                onSelected = {
                    onTransactionChange(null)
                    selectedOption = it
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            AccountBookOptionButton(
                option = "지출",
                isSelected = selectedOption == "지출",
                onSelected = {
                    onTransactionChange(AccountBookEntity.TransactionType.EXPENSE)
                    selectedOption = it
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            AccountBookOptionButton(
                option = "수입",
                isSelected = selectedOption == "수입",
                onSelected = {
                    onTransactionChange(AccountBookEntity.TransactionType.REVENUE)
                    selectedOption = it
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
    state: List<String>?,
    sortOrder: String,
    onCategoryChange: (String) -> Unit,
    onSortOrderChange: (String) -> Unit,
    onTransactionChange: (AccountBookEntity.TransactionType?) -> Unit
) {
    Column {
        CategorySelector(state, onCategoryChange = onCategoryChange)
        FilterSelector(onTransactionChange)
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
private fun SortOrderSelector(sortOrder: String, onSortOrderChange: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = { onSortOrderChange(SortOrder.EARLIEST.name) })
        ) {
            Text(
                text = "최신순",
                style = MaterialTheme.typo.body2,
                color = if (sortOrder == SortOrder.EARLIEST.name) MaterialTheme.colors.primary else MaterialTheme.colors.gray4,
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = { onSortOrderChange(SortOrder.OLDEST.name) })
        ) {
            Text(
                text = "과거순",
                style = MaterialTheme.typo.body2,
                color = if (sortOrder == SortOrder.OLDEST.name) MaterialTheme.colors.primary else MaterialTheme.colors.gray4,
            )
        }
    }
}

@Composable
private fun AccountBookList(
    hasNext: Boolean,
    accountBooks: List<AccountBookViewModel.State.AccountBook>,
    onPageChange: (Long) -> Unit,
    onItemClicked: (Long) -> Unit,
    isLoading: Boolean
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(accountBooks) { index, accountBook ->
            AccountBookItem(
                accountBook = accountBook,
                onItemClicked = onItemClicked
            )

            if (index == accountBooks.lastIndex && hasNext && !isLoading) {
                LaunchedEffect(index) {
                    onPageChange(accountBook.id)
                }
            }
        }

        item {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary2
                    )
                }
            }
        }
    }
}


@Composable
private fun AccountBookItem(
    accountBook: AccountBookViewModel.State.AccountBook,
    onItemClicked: (Long) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Paddings.xlarge)
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
        Text(
            text = CategoryDisplayMapper.getDisplay(accountBook.category),
            style = MaterialTheme.typo.label,
            color = MaterialTheme.colors.gray5
        )

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
                    .weight(2f)
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


@Composable
private fun ClickableTotalText(
    text: String,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .width(52.dp)
                .height(28.dp)
                .background(
                    if (isSelected) MaterialTheme.colors.green1 else MaterialTheme.colors.grey1,
                    RoundedCornerShape(14.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = if (isSelected) Color.White else Color.Black
            )
        }
    }
}

fun formatNumber(number: Long): String {
    val formatter = NumberFormat.getNumberInstance()
    return formatter.format(number)
}
