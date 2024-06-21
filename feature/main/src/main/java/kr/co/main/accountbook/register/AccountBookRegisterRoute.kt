package kr.co.main.accountbook.register

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest
import kr.co.common.util.FileUtil
import kr.co.domain.entity.AccountBookEntity
import kr.co.main.accountbook.main.AccountBookCategoryBottomSheet
import kr.co.main.accountbook.main.AccountBookOptionButton
import kr.co.main.accountbook.main.formatNumber
import kr.co.main.accountbook.model.CategoryDisplayMapper.getDisplay
import kr.co.nbdream.core.ui.R
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.Shapes
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
internal fun AccountBookRegisterRoute(
    popBackStack: () -> Unit,
    viewModel: AccountBookRegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.complete.collectLatest {
            popBackStack()
        }
    }

    var showBottomSheet by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                FileUtil.getFileFromUri(it)?.let { file ->
                    viewModel.uploadImage(file)
                }
            }
        }
    )

    Surface(
        color = MaterialTheme.colors.white
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Paddings.xlarge)
        ) {
            item {
                DreamCenterTopAppBar(
                    title = "장부 작성하기",
                    navigationIcon = {
                        IconButton(onClick = popBackStack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "뒤로가기",
                                tint = Color.Black
                            )
                        }
                    },
                    actions = {
                        Button(
                            onClick = {
                                viewModel.createAccountBook()
                            },
                            colors = ButtonDefaults.buttonColors(Color.Transparent)
                        ) {
                            Text("등록", color = MaterialTheme.colors.black)
                        }
                    }
                )
            }

            item {
                Column(
                    modifier = Modifier.padding(top = Paddings.extra)
                ) {
                    Text(
                        text = "금액",
                        style = MaterialTheme.typo.h4,
                        color = MaterialTheme.colors.gray1
                    )
                    Spacer(modifier = Modifier.height(Paddings.large))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .background(
                                    color = MaterialTheme.colors.gray10,
                                    shape = Shapes.medium
                                ),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            AccountBookTextField(
                                value = state.amountText,
                                onValueChange = { newText ->
                                    val cleanedText = newText.replace(",", "")
                                    if (cleanedText.all { it.isDigit() }) {
                                        val newAmount = cleanedText.toLongOrNull() ?: 0L
                                        viewModel.updateAmount(newAmount)
                                        viewModel.updateAmountText(formatNumber(newAmount))
                                    }
                                },
                                placeholder = "0",
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                trailingIcon = {
                                    Text(
                                        text = "원",
                                        style = MaterialTheme.typo.body1,
                                        color = MaterialTheme.colors.gray4
                                    )
                                }
                            )
                        }
                        Spacer(modifier = Modifier.width(Paddings.xlarge))
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = MaterialTheme.colors.gray4
                        )
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.padding(top = Paddings.extra)
                ) {
                    Text(
                        text = "분류",
                        style = MaterialTheme.typo.h4,
                        color = MaterialTheme.colors.gray1
                    )
                    Spacer(modifier = Modifier.height(Paddings.large))
                    Row {
                        AccountBookOptionButton(
                            width = 96.dp,
                            height = 40.dp,
                            option = "지출",
                            isSelected = state.transactionType == AccountBookEntity.TransactionType.EXPENSE,
                            onSelected = {
                                viewModel.updateTransactionType(AccountBookEntity.TransactionType.EXPENSE)
                            }
                        )
                        Spacer(modifier = Modifier.width(Paddings.xlarge))
                        AccountBookOptionButton(
                            width = 96.dp,
                            height = 40.dp,
                            option = "수입",
                            isSelected = state.transactionType == AccountBookEntity.TransactionType.REVENUE,
                            onSelected = {
                                viewModel.updateTransactionType(AccountBookEntity.TransactionType.REVENUE)
                            }
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier.padding(top = Paddings.extra)
                ) {
                    Text(
                        text = "카테고리",
                        style = MaterialTheme.typo.h4,
                        color = MaterialTheme.colors.gray1
                    )
                    Spacer(modifier = Modifier.height(Paddings.large))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(
                                color = MaterialTheme.colors.gray10,
                                shape = Shapes.medium
                            )
                    ) {
                        AccountBookButton(
                            onClick = { showBottomSheet = true },
                            text = state.category?.let { getDisplay(it) } ?: "선택하세요",
                            buttonColors = ButtonDefaults.buttonColors(Color.Transparent),
                            contentPadding = PaddingValues(horizontal = Paddings.xlarge),
                            icon = Icons.AutoMirrored.Filled.KeyboardArrowRight
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier.padding(top = Paddings.extra)
                ) {
                    Text(
                        text = "내역",
                        style = MaterialTheme.typo.h4,
                        color = MaterialTheme.colors.gray1
                    )
                    Spacer(modifier = Modifier.height(Paddings.large))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(
                                color = MaterialTheme.colors.gray10,
                                shape = Shapes.medium
                            )
                    ) {
                        AccountBookTextField(
                            value = state.title,
                            onValueChange = { newValue -> viewModel.updateTitle(newValue) },
                            placeholder = "입력하세요",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier.padding(top = Paddings.extra)
                ) {
                    Text(
                        text = "일자",
                        style = MaterialTheme.typo.h4,
                        color = MaterialTheme.colors.gray1
                    )
                    Spacer(modifier = Modifier.height(Paddings.large))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(
                                color = MaterialTheme.colors.gray10,
                                shape = Shapes.medium
                            )
                    ) {
                        AccountBookButton(
                            onClick = { showDatePicker = true },
                            text = state.registerDateTime,
                            buttonColors = ButtonDefaults.buttonColors(Color.Transparent),
                            icon = Icons.Default.DateRange
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier.padding(top = Paddings.extra)
                ) {
                    Text(
                        text = "사진",
                        style = MaterialTheme.typo.h4,
                        color = MaterialTheme.colors.gray1
                    )
                }
                Spacer(modifier = Modifier.height(Paddings.large))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(
                                color = MaterialTheme.colors.gray10,
                                shape = Shapes.medium
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                imagePickerLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            },
                            modifier = Modifier.fillMaxSize(),
                            colors = ButtonDefaults.buttonColors(Color.Transparent),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_photo_camera_24),
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.gray5,
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = "사진 올리기",
                                    color = MaterialTheme.colors.gray4,
                                    style = MaterialTheme.typo.body1,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    }

                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(state.imageUrls.size) { index ->
                            val imageUri = state.imageUrls[index]
                            Box(
                                modifier = Modifier
                                    .padding(start = Paddings.large)
                                    .size(120.dp)
                                    .clip(Shapes.small)
                                    .background(MaterialTheme.colors.gray10),
                                contentAlignment = Alignment.Center,
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(Shapes.small),
                                    model = imageUri,
                                    contentDescription = "image",
                                    contentScale = ContentScale.Crop,
                                )
                                IconButton(
                                    onClick = {
                                        viewModel.removeImageUrl(imageUri)
                                    },
                                    modifier = Modifier
                                        .size(24.dp)
                                        .align(Alignment.TopEnd),
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = MaterialTheme.colors.gray1
                                    ),
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Clear,
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.white,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    if (showDatePicker) {
        AccountBookDatePickerDialog(
            onClickCancel = { showDatePicker = false },
            onClickConfirm = { selectedDate ->
                viewModel.updateRegisterDateTime(selectedDate)
                showDatePicker = false
            }
        )
    }
    if (showBottomSheet) {
        AccountBookCategoryBottomSheet(
            onSelectedListener = { category ->
                viewModel.updateCategory(category)
                showBottomSheet = false
            },
            dismissBottomSheet = { showBottomSheet = false }
        )
    }
}

@Composable
fun AccountBookTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color.Transparent
        ),
        textStyle = MaterialTheme.typo.body1,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray4
            )
        },
        trailingIcon = trailingIcon
    )
}

@Composable
fun AccountBookButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(Color.Transparent),
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    icon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = buttonColors,
        contentPadding = contentPadding
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = text,
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray1
            )
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colors.gray5
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountBookDatePickerDialog(
    onClickCancel: () -> Unit,
    onClickConfirm: (yyyyMMdd: String) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        yearRange = IntRange(2000, 2050),
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = System.currentTimeMillis(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return true
            }
        }
    )
    val initialSelectedDate = remember { datePickerState.selectedDateMillis }

    LaunchedEffect(datePickerState.selectedDateMillis) {
        if (initialSelectedDate != datePickerState.selectedDateMillis) {
            datePickerState.selectedDateMillis?.let { selectedDateMillis ->
                val date = Date(selectedDateMillis)
                val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
                val formattedDate = formatter.format(date)

                onClickConfirm(formattedDate)
            }
        }
    }

    DatePickerDialog(
        onDismissRequest = onClickCancel,
        confirmButton = {},
        colors = DatePickerDefaults.colors(
            containerColor = Color.White,
            selectedDayContentColor = MaterialTheme.colors.primary2,
            selectedDayContainerColor = MaterialTheme.colors.primary2,
            dayInSelectionRangeContentColor = MaterialTheme.colors.primary2,
        ),
        shape = Shapes.small,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        DatePicker(
            state = datePickerState,
        )
    }
}