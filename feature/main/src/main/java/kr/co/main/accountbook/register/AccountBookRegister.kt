package kr.co.main.accountbook.register

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import kr.co.domain.entity.AccountBookEntity
import kr.co.main.accountbook.main.AccountBookCategoryBottomSheet
import kr.co.nbdream.core.ui.R
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Preview
@Composable
private fun PreviewAccountBookRegister() {
    MaterialTheme {
        AccountBookRegister()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountBookRegister() {
    var selectedCategory by remember { mutableStateOf("지출") }
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedCategoryText by remember { mutableStateOf("선택하세요") }
    var amount by remember { mutableStateOf(TextFieldValue("")) }
    var writingImages by remember { mutableStateOf(listOf<Uri>()) }
    var description by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var currentDateTime by remember {
        mutableStateOf(SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(Date()))
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                writingImages = writingImages + it
            }
        }
    )

    Scaffold(
        topBar = {
            DreamCenterTopAppBar(
                title = "장부 작성하기",
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = Color.Black
                        )
                    }
                },
                actions = {
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Text("등록", color = MaterialTheme.colors.black)
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "금액",
                    style = MaterialTheme.typo.header2M
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = amount,
                        onValueChange = { newValue ->
                            if (newValue.text.all { it.isDigit() }) {
                                amount = newValue
                            }
                        },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                        )
                    )
                    Text(text = "원",
                        style = MaterialTheme.typo.header2B)
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }

                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colors.grey2)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "분류",
                        modifier = Modifier.width(80.dp),
                        style = MaterialTheme.typo.titleM
                    )

                    Button(
                        onClick = { selectedCategory = "지출" },
                        modifier = Modifier
                            .width(85.dp)
                            .height(42.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedCategory == "지출") MaterialTheme.colors.primary else MaterialTheme.colors.white,
                            contentColor = if (selectedCategory == "지출") MaterialTheme.colors.white else MaterialTheme.colors.primary,
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colors.primary)
                    ) {
                        Text(
                            text = "지출",
                            style = MaterialTheme.typo.bodyM
                        )
                    }
                    Button(
                        onClick = { selectedCategory = "수입" },
                        modifier = Modifier
                            .width(85.dp)
                            .height(42.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedCategory == "수입") MaterialTheme.colors.primary else MaterialTheme.colors.white,
                            contentColor = if (selectedCategory == "수입") MaterialTheme.colors.white else MaterialTheme.colors.primary,
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colors.primary)
                    ) {
                        Text(
                            text = "수입",
                            style = MaterialTheme.typo.bodyM
                        )
                    }
                }

                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colors.grey2)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "카테고리",
                        modifier = Modifier.width(80.dp),
                        style = MaterialTheme.typo.titleM
                    )

                    Button(
                        onClick = { showBottomSheet = true },
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                    ) {
                        Text(
                            text = selectedCategoryText,
                            color = MaterialTheme.colors.black,
                            style = MaterialTheme.typo.bodyM
                        )
                    }
                }

                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colors.grey2)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "내역",
                        modifier = Modifier.width(80.dp),
                        style = MaterialTheme.typo.titleM
                    )

                    TextField(
                        value = description,
                        onValueChange = { newValue ->
                            description = newValue
                        },
                        placeholder = {
                            Text("입력하세요",
                                color = MaterialTheme.colors.grey6) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                        ),
                        textStyle = MaterialTheme.typo.bodyM
                    )
                }

                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colors.grey2)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "날짜",
                        modifier = Modifier.width(80.dp),
                        style = MaterialTheme.typo.titleM
                    )

                    Button(
                        onClick = { showDatePicker = true },
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                    ) {
                        Text(
                            text = currentDateTime,
                            color = MaterialTheme.colors.black,
                            style = MaterialTheme.typo.bodyM
                        )
                    }
                }

                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colors.grey2)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "사진",
                        modifier = Modifier.width(80.dp),
                        style = MaterialTheme.typo.titleM
                    )
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(writingImages.size) { index ->
                        val imageUri = writingImages[index]
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center,
                        ) {
                            AsyncImage(
                                model = imageUri,
                                contentDescription = "image",
                                contentScale = ContentScale.Crop,
                            )
                            IconButton(
                                onClick = {
                                    writingImages =
                                        writingImages.toMutableList().apply { removeAt(index) }
                                },
                                modifier = Modifier.align(Alignment.TopEnd),
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Color(0x99999999)
                                ),
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "이미지 삭제 아이콘",
                                )
                            }
                        }
                    }
                }

                Button(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_photo_camera_24),
                        contentDescription = null,
                        tint = MaterialTheme.colors.black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "사진 올리기",
                        color = MaterialTheme.colors.black,
                        style = MaterialTheme.typo.titleM
                    )
                }
            }
        }
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {},
            colors = DatePickerDefaults.colors(
                containerColor = Color.White,
                selectedDayContentColor = MaterialTheme.colors.primary,
                selectedDayContainerColor = MaterialTheme.colors.primary,
                dayInSelectionRangeContentColor = MaterialTheme.colors.primary,
            ),
            shape = RoundedCornerShape(6.dp),
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            val datePickerState = rememberDatePickerState(
                yearRange = IntRange(1900, 2100),
                initialDisplayMode = DisplayMode.Picker,
                initialSelectedDateMillis = System.currentTimeMillis(),
                selectableDates = object : SelectableDates {
                    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                        return true
                    }
                }
            )

            DatePicker(
                state = datePickerState,
            )

            LaunchedEffect(datePickerState.selectedDateMillis) {
                datePickerState.selectedDateMillis?.let { selectedDateMillis ->
                    val date = Date(selectedDateMillis)
                    val formatter = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
                    currentDateTime = formatter.format(date)
                    showDatePicker = false
                }
            }
        }
    }

    if (showBottomSheet) {
        AccountBookCategoryBottomSheet(
            onSelectedListener = { category ->
                selectedCategoryText = category
                showBottomSheet = false
            },
            categories = AccountBookEntity.Category.entries.map { it.value },
            dismissBottomSheet = { showBottomSheet = false }
        )
    }
}
