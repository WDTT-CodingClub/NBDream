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
import androidx.compose.material3.Surface
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
import java.time.temporal.ChronoUnit

@Composable
internal fun AccountBookRoute(
    viewModel: AccountBookViewModel = hiltViewModel(),
    navigationToRegister: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AccountBookScreen(
        state = state,
        navigationToRegister = navigationToRegister
    )
}

@Composable
private fun AccountBookScreen(
    state: AccountBookViewModel.State = AccountBookViewModel.State(),
    navigationToRegister: () -> Unit
) {
    var sortOrder by remember { mutableStateOf(SortOrder.RECENCY) }
    var showingExpenses by remember { mutableStateOf(true) }

    var daysInRange by remember { mutableLongStateOf(0L) }

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
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column {
                CalendarSection(onDaysInRangeChange = { daysInRange = it })
                GraphSection(
                    state = state,
                    daysInRange = daysInRange,
                    showingExpenses = showingExpenses,
                    onToggleTypeClick = { showingExpenses = !showingExpenses }
                )
                SelectorSection(state, sortOrder) {
                    sortOrder = it
                }
                AccountBooksList(state.accountBooks)
            }
        }
    }
}


@Composable
private fun CalendarSection(onDaysInRangeChange: (Long) -> Unit) {
    val currentDate = LocalDate.now()
    val currentYearMonth = YearMonth.from(currentDate)
    val firstDayOfMonth = currentYearMonth.atDay(1)
    val lastDayOfMonth = currentYearMonth.atEndOfMonth()

    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val startDate = remember { mutableStateOf(firstDayOfMonth) }
    val endDate = remember { mutableStateOf(lastDayOfMonth) }

    val daysInRange = ChronoUnit.DAYS.between(firstDayOfMonth, lastDayOfMonth) + 1
    onDaysInRangeChange(daysInRange)

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
//            selectedOption = selectedOption,
//            onOptionSelected = { newOption ->
//                selectedOption = newOption
//            },
            onSelectedListener = { selectedStartDate, selectedEndDate ->
                startDate.value = LocalDate.parse(selectedStartDate)
                endDate.value = LocalDate.parse(selectedEndDate)
                bottomSheetState = false
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
            .map { it.category }
    } else {
        data = if (totalRevenue > 0) listOf(totalRevenue.toFloat()) else emptyList()
        categories = state.accountBooks
            .filter { it.transactionType == AccountBookEntity.TransactionType.REVENUE }
            .map { it.category }
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
private fun FilterSelector() {
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
                onSelected = { selectedOption = it }
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            AccountBookOptionButton(
                option = "지출",
                isSelected = selectedOption == "지출",
                onSelected = { selectedOption = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            AccountBookOptionButton(
                option = "수입",
                isSelected = selectedOption == "수입",
                onSelected = { selectedOption = it }
            )
        }
    }
}

@Composable
private fun AccountBookOptionButton(option: String, isSelected: Boolean, onSelected: (String) -> Unit) {
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
    state: AccountBookViewModel.State,
    sortOrder: SortOrder,
    onSortOrderChange: (SortOrder) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterSelector()
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CategorySelector(state)
        SortOrderSelector(sortOrder, onSortOrderChange)
    }
}

@Composable
private fun CategorySelector(state: AccountBookViewModel.State) {
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
        state.categories?.let {
            AccountBookCategoryBottomSheet(
                onSelectedListener = { selectedCategory ->
                    // TODO Handle selected category
                    bottomSheetState = false
                },
                categories = it,
                dismissBottomSheet = { bottomSheetState = false }
            )
        }
    }
}


@Composable
private fun SortOrderSelector(sortOrder: SortOrder, onSortOrderChange: (SortOrder) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        ClickableText(
            text = "최신순",
            isSelected = sortOrder == SortOrder.RECENCY,
            onClick = { onSortOrderChange(SortOrder.RECENCY) }
        )
        Spacer(modifier = Modifier.width(16.dp))
        ClickableText(
            text = "과거순",
            isSelected = sortOrder == SortOrder.OLDEST,
            onClick = { onSortOrderChange(SortOrder.OLDEST) }
        )
    }
}

@Composable
private fun AccountBooksList(accountBooks: List<AccountBookViewModel.State.AccountBook>) {
    LazyColumn {
        items(accountBooks) { accountBook ->
            AccountBookItem(accountBook)
        }
    }
}

@Composable
private fun AccountBookItem(accountBook: AccountBookViewModel.State.AccountBook) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = accountBook.day?.toString() ?: "")
            Text(text = accountBook.dayName ?: "")
        }
        Column(modifier = Modifier.weight(2f)) {
            Text(text = accountBook.title)
            Text(
                text = "${accountBook.amount}원",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Text(text = accountBook.category)
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
