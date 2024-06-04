package kr.co.wdtt.nbdream.ui.accountbook.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kr.co.wdtt.nbdream.domain.entity.AccountBookEntity
import kr.co.wdtt.nbdream.domain.entity.SortOrder
import kr.co.wdtt.nbdream.ui.theme.colors
import java.text.NumberFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewAccountBookScreen() {
    AccountBookScreen()
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AccountBookScreen() {
    var sortOrder by remember { mutableStateOf(SortOrder.RECENCY) }
    var showTotalExpenses by remember { mutableStateOf(true) }
    var showTotalRevenue by remember { mutableStateOf(false) }
    val totalExpenses = accountBookList.sumOf { it.expense ?: 0L }
    val totalRevenue = accountBookList.sumOf { it.revenue ?: 0L }
    val totalCost = accountBookList[0].totalCost

    var daysInRange by remember { mutableStateOf(0L) }

    val sortedList = remember(accountBookList, sortOrder) {
        when (sortOrder) {
            SortOrder.RECENCY -> accountBookList.sortedWith(
                compareByDescending<AccountBookEntity> { it.year ?: Int.MIN_VALUE }
                    .thenByDescending { it.month ?: Int.MIN_VALUE }
                    .thenByDescending { it.day ?: Int.MIN_VALUE }
            )

            SortOrder.OLDEST -> accountBookList.sortedWith(
                compareBy<AccountBookEntity> { it.year ?: Int.MAX_VALUE }
                    .thenBy { it.month ?: Int.MAX_VALUE }
                    .thenBy { it.day ?: Int.MAX_VALUE }
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column {
                CalendarSection(onDaysInRangeChange = { daysInRange = it })
                GraphSection(
                    showTotalExpenses,
                    showTotalRevenue,
                    totalExpenses,
                    totalRevenue,
                    totalCost,
                    daysInRange,
                    { showTotalExpenses = true; showTotalRevenue = false },
                    { showTotalExpenses = false; showTotalRevenue = true }
                )
                SortingSection(sortOrder) {
                    sortOrder = it
                }
                AccountBooksList(sortedList)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarSection(onDaysInRangeChange: (Long) -> Unit) {
    val currentDate = LocalDate.now()
    val currentYearMonth = YearMonth.from(currentDate)
    val firstDayOfMonth = currentYearMonth.atDay(1)
    val lastDayOfMonth = currentYearMonth.atEndOfMonth()

    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val startDate = firstDayOfMonth.format(formatter)
    val endDate = lastDayOfMonth.format(formatter)

    val daysInRange = ChronoUnit.DAYS.between(firstDayOfMonth, lastDayOfMonth) + 1
    onDaysInRangeChange(daysInRange)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .clickable {  }
    ) {
        Text(
            text = "$startDate - $endDate",
            modifier = Modifier.padding(start = 8.dp, end = 4.dp)
        )
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = null,
            tint = Color.Black
        )
    }
}

@Composable
private fun GraphSection(
    showTotalExpenses: Boolean,
    showTotalRevenue: Boolean,
    totalExpenses: Long,
    totalRevenue: Long,
    totalCost: Long?,
    daysInRange: Long,
    onShowExpensesClick: () -> Unit,
    onShowRevenueClick: () -> Unit
) {
    val expensesMap = remember(accountBookList) {
        groupByCategory(accountBookList.filter { it.expense != null }, AccountBookEntity::expense)
    }

    val revenuesMap = remember(accountBookList) {
        groupByCategory(accountBookList.filter { it.revenue != null }, AccountBookEntity::revenue)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier.size(100.dp)
        ) {
            GraphView(
                data = if (showTotalExpenses) expensesMap.values.toList() else revenuesMap.values.toList(),
                categories = getCategoryNames(if (showTotalExpenses) expensesMap.keys.toList() else revenuesMap.keys.toList()),
                label = "${daysInRange}일",
                modifier = Modifier.size(100.dp)
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.End
        ) {
            ClickableTextWithTotal(
                text = "지출",
                onClick = onShowExpensesClick,
                isSelected = showTotalExpenses
            )
            ClickableTextWithTotal(
                text = "수입",
                onClick = onShowRevenueClick,
                isSelected = showTotalRevenue
            )
            if (showTotalExpenses) {
                Text(text = "-${formatNumber(totalExpenses)}원")
            } else {
                Text(text = "+${formatNumber(totalRevenue)}원")
            }
            Text(text = "합계: ${totalCost?.let { formatNumber(it) }}원")
        }
    }
}

@Composable
private fun SortingSection(sortOrder: SortOrder, onSortOrderChange: (SortOrder) -> Unit) {
    var bottomSheetState by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .clickable { bottomSheetState = true }
        ) {
            Text(
                text = "카테고리",
                modifier = Modifier.padding(start = 8.dp, end = 4.dp)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = Color.Black
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            ClickableText(
                text = "최신순",
                onClick = { onSortOrderChange(SortOrder.RECENCY) },
                isSelected = sortOrder == SortOrder.RECENCY
            )
            Spacer(modifier = Modifier.width(16.dp))
            ClickableText(
                text = "과거순",
                onClick = { onSortOrderChange(SortOrder.OLDEST) },
                isSelected = sortOrder == SortOrder.OLDEST
            )
        }
    }

    if (bottomSheetState) {
        BottomSheetScreen(
            onSelectedListener = { selectedCategory ->
                // TODO 선택된 카테고리 처리
                //  API 호출
                bottomSheetState = false
            },
            categories = listOf("농약", "종자/종묘", "비료", "농산물 판매","농약", "종자/종묘", "비료", "농산물 판매", "농약", "종자/종묘", "비료", "농산물 판매"),
            dismissBottomSheet = { bottomSheetState = false }
        )
    }
}

@Composable
private fun AccountBooksList(sortedList: List<AccountBookEntity>) {
    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
    ) {
        items(sortedList) { accountBook ->
            AccountBookItem(accountBook)
        }
    }
}

@Composable
fun AccountBookItem(accountBook: AccountBookEntity) {
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
            accountBook.revenue?.let {
                Text(text = "+${formatNumber(it)}원")
            } ?: accountBook.expense?.let {
                Text(text = "-${formatNumber(it)}원")
            }
        }
        Text(text = accountBook.category.value, modifier = Modifier.weight(2f))
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
fun ClickableText(
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
            color = if (isSelected) MaterialTheme.colors.primary else Color.Black
        )
    }
}

@Composable
fun ClickableTextWithTotal(
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

fun getCategoryNames(categories: List<AccountBookEntity.Category>): List<String> {
    return categories.map { it.value }
}

private fun groupByCategory(
    accountBooks: List<AccountBookEntity>,
    selector: (AccountBookEntity) -> Long?
): Map<AccountBookEntity.Category, Float> {
    return accountBooks
        .groupBy { it.category }
        .mapValues { (_, accounts) ->
            accounts.sumOf { selector(it)!! }.toFloat()
        }
}

val accountBookList = listOf(
    AccountBookEntity(
        id = "1",
        title = "옥수수 판매",
        category = AccountBookEntity.Category.FARM_PRODUCT_SALES,
        day = 12,
        dayName = "월요일",
        revenue = 50000,
        totalExpense = 45000,
        totalRevenue = 80000,
        totalCost = 35000,
        imageUrl = listOf("https://cdn.mkhealth.co.kr/news/photo/202206/58096_61221_124.jpg")
    ),
    AccountBookEntity(
        id = "2",
        title = "비료 구입",
        category = AccountBookEntity.Category.FERTILIZER,
        day = 14,
        dayName = "수요일",
        expense = 20000,
        totalExpense = 45000,
        totalRevenue = 80000,
        totalCost = 35000,
        imageUrl = listOf("https://godomall.speedycdn.net/6686a1fdb6f0fed93d4f44074b951d13/goods/1000000025/image/main/1000000025_main_032.jpg")
    ),
    AccountBookEntity(
        id = "3",
        title = "종자 구매",
        category = AccountBookEntity.Category.SEEDS_AND_SEEDLINGS,
        day = 16,
        dayName = "금요일",
        expense = 15000,
        totalExpense = 45000,
        totalRevenue = 80000,
        totalCost = 35000,
        imageUrl = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSnN4P5HnBeFFnpRoF3rUPmGt8lb9YEkqB2YA&s")
    ),
    AccountBookEntity(
        id = "4",
        title = "농약 살포",
        category = AccountBookEntity.Category.PESTICIDES,
        day = 18,
        dayName = "일요일",
        expense = 10000,
        totalExpense = 45000,
        totalRevenue = 80000,
        totalCost = 35000,
    ),
)
