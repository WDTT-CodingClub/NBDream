package kr.co.main.accountbook.main


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import kr.co.domain.entity.AccountBookEntity
import kr.co.domain.entity.SortOrder
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import java.text.NumberFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
internal fun AccountBookScreen(
    viewModel: AccountBookViewModel = hiltViewModel(),
    navigationToRegister: () -> Unit,
    navigationToContent: (Long?) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {},
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigationToRegister,
                shape = CircleShape,
                containerColor = MaterialTheme.colors.green1
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            SelectorSection(
                state = state.categories,
                sortOrder = state.sort,
                onCategoryChange = { viewModel.updateCategory(it) },
                onSortOrderChange = { viewModel.updateSortOrder(it) },
                onTransactionChange = { viewModel.updateTransactionType(it) }
            )

            CalendarSection(onDaysInRangeChange = { startDate, endDate ->
                viewModel.updateDateRange(startDate.toString(), endDate.toString())
            })

            AccountBookList(
                accountBooks = state.accountBooks,
                onPageChange = { viewModel.updatePage(it) },
                onItemClicked = { id ->
                    navigationToContent(id)

                }
            )
        }
    }
}

@Composable
private fun CalendarSection(onDaysInRangeChange: (LocalDate, LocalDate) -> Unit) {
    // TODO 제거
    val currentDate = LocalDate.now()
    val currentYearMonth = YearMonth.from(currentDate)
    val firstDayOfMonth = currentYearMonth.atDay(1)
    val lastDayOfMonth = currentYearMonth.atEndOfMonth()

    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val startDate = remember { mutableStateOf(firstDayOfMonth) }
    val endDate = remember { mutableStateOf(lastDayOfMonth) }
    // TODO 여기까지~

    var bottomSheetState by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("1개월") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .clickable { bottomSheetState = true }
    ) {
        Text(
            text = "${startDate.value.format(formatter)} - ${endDate.value.format(formatter)}",
            modifier = Modifier.padding(start = 8.dp, end = 4.dp),
            style = MaterialTheme.typo.header2M
        )
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = null,
            tint = Color.Black
        )
    }

    if (bottomSheetState) {
        AccountBookCalendarBottomSheet(
            selectedOption = selectedOption,
            onOptionSelected = { newOption ->
                selectedOption = newOption
            },
            onSelectedListener = { selectedStartDate, selectedEndDate ->
                startDate.value = LocalDate.parse(selectedStartDate)
                endDate.value = LocalDate.parse(selectedEndDate)
                bottomSheetState = false
                onDaysInRangeChange(startDate.value, endDate.value)
            },
            dismissBottomSheet = { bottomSheetState = false }
        )
    }
}

@Composable
private fun GraphSection(
    state: AccountBookViewModel.State,
    daysInRange: Long,
    showingExpenses: Boolean,
    onToggleTypeClick: () -> Unit
) {
    val data: List<Float>
    val categories: List<String>

    val totalExpense = state.totalExpense ?: 0L
    val totalRevenue = state.totalRevenue ?: 0L

    if (showingExpenses) {
        data = if (totalExpense > 0) listOf(totalExpense.toFloat()) else emptyList()
        categories = state.accountBooks
            .filter { it.transactionType == AccountBookEntity.TransactionType.EXPENSE }
            .map { it.category.toString() }
    } else {
        data = if (totalRevenue > 0) listOf(totalRevenue.toFloat()) else emptyList()
        categories = state.accountBooks
            .filter { it.transactionType == AccountBookEntity.TransactionType.REVENUE }
            .map { it.category.toString() }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier.size(100.dp)
        ) {
            AccountBookGraph(
                data = data,
                categories = categories,
                label = "${daysInRange}일",
                modifier = Modifier.size(100.dp)
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.End
        ) {
            ClickableTotalText(
                text = "지출",
                onClick = { onToggleTypeClick() },
                isSelected = showingExpenses
            )
            ClickableTotalText(
                text = "수입",
                onClick = { onToggleTypeClick() },
                isSelected = !showingExpenses
            )
            Text(
                text = "합계: ${state.totalCost?.let { formatNumber(it) }}원"
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
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
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
private fun AccountBookOptionButton(
    option: String,
    isSelected: Boolean,
    onSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .width(75.dp)
            .height(32.dp)
            .background(
                color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.grey1,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onSelected(option) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = option,
            color = if (isSelected) Color.White else MaterialTheme.colors.grey6,
            style = MaterialTheme.typo.bodyM
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterSelector(onTransactionChange)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CategorySelector(state, onCategoryChange = onCategoryChange)
        SortOrderSelector(sortOrder, onSortOrderChange)
    }
}

@Composable
private fun CategorySelector(state: List<String>?, onCategoryChange: (String) -> Unit) {
    var bottomSheetState by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { bottomSheetState = true }
    ) {
        Text(
            text = "카테고리",
            modifier = Modifier.padding(end = 4.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null,
            tint = Color.Black
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
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        ClickableText(
            text = "최신순",
            isSelected = sortOrder == SortOrder.EARLIEST.name,
            onClick = { onSortOrderChange(SortOrder.EARLIEST.name) }
        )
        Spacer(modifier = Modifier.width(16.dp))
        ClickableText(
            text = "과거순",
            isSelected = sortOrder == SortOrder.OLDEST.name,
            onClick = { onSortOrderChange(SortOrder.OLDEST.name) }
        )
    }
}

@Composable
private fun AccountBookList(
    accountBooks: List<AccountBookViewModel.State.AccountBook>,
    onPageChange: (Long) -> Unit,
    onItemClicked: (Long) -> Unit,
) {
    LazyColumn {
        items(accountBooks) { accountBook ->
            AccountBookItem(
                accountBook = accountBook,
                onItemClicked = {
                    onItemClicked(it)
                })
        }
    }

}

@Composable
private fun AccountBookItem(
    accountBook: AccountBookViewModel.State.AccountBook,
    onItemClicked: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onItemClicked(accountBook.id)
            },
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = accountBook.day?.toString() ?: "")
            Text(text = accountBook.dayName ?: "")
        }
        Column(modifier = Modifier.weight(2f)) {
            Text(text = accountBook.title ?: "")
            Text(
                text = "${accountBook.amount}원",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Text(text = accountBook.category ?: "")
        val imageUrl = accountBook.imageUrl.firstOrNull()
        if (imageUrl != null) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Spacer(
                modifier = Modifier
                    .size(64.dp)
                    .weight(2f)
            )
        }
    }
}

@Composable
private fun ClickableText(
    text: String,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            text = text,
            color = if (isSelected) MaterialTheme.colors.primary else Color.Black,
        )
    }
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
