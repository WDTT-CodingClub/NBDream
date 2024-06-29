package kr.co.main.accountbook.create

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kr.co.common.util.FileUtil
import kr.co.common.util.FileUtil.getFileFromUri
import kr.co.domain.entity.AccountBookEntity
import kr.co.main.accountbook.main.AccountBookCategoryBottomSheet
import kr.co.main.accountbook.main.AccountBookOptionButton
import kr.co.main.accountbook.main.CircleProgress
import kr.co.main.accountbook.model.AccountBookDialog
import kr.co.main.accountbook.model.CustomDatePickerDialog
import kr.co.main.accountbook.model.EntryType
import kr.co.main.accountbook.model.formatNumber
import kr.co.main.accountbook.model.getDisplay
import kr.co.main.accountbook.model.parseLocalDate
import kr.co.main.community.temp.UriUtil
import kr.co.nbdream.core.ui.R
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Arrowleft
import kr.co.ui.icon.dreamicon.DatePicker
import kr.co.ui.icon.dreamicon.Delete
import kr.co.ui.icon.dreamicon.Edit
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.Shapes
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar
import java.io.File


@Composable
internal fun AccountBookCreateRoute(
    popBackStack: () -> Unit,
    viewModel: AccountBookCreateViewModel = hiltViewModel(),
    navigationToAccountBook: () -> Unit,
    navigationToContent: (Long) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    var showErrorDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.complete.collect { complete ->
            if (complete) {
                when (viewModel.entryType) {
                    EntryType.CREATE -> navigationToAccountBook()
                    EntryType.UPDATE -> state.id?.let { id -> navigationToContent(id) }
                }
            } else {
                showErrorDialog = true
            }
        }
    }

    AccountBookCreateScreen(
        state = state,
        isLoading = isLoading,
        popBackStack = popBackStack,
        onUploadImage = { viewModel.uploadImage(it) },
        onPerformAccountBook = viewModel::performAccountBook,
        onUpdateAmount = { viewModel.updateAmount(it) },
        onUpdateAmountText = { viewModel.updateAmountText(it) },
        onUpdateTransactionType = { viewModel.updateTransactionType(it) },
        onUpdateTitle = { viewModel.updateTitle(it) },
        onRemoveImageUrl = { viewModel.deleteImage(it) },
        onUpdateRegisterDateTime = { viewModel.updateRegisterDateTime(it) },
        onUpdateCategory = { viewModel.updateCategory(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Paddings.large),
    )

    if (showErrorDialog) {
        AccountBookDialog(
            onDismissRequest = { showErrorDialog = false },
            title = "작성 실패",
            message = "오류가 발생하여 작성을 완료할 수 없습니다.",
            onConfirm = {
                showErrorDialog = false
            }
        )
    }
}

@Composable
internal fun AccountBookCreateScreen(
    state: AccountBookCreateViewModel.State = AccountBookCreateViewModel.State(),
    isLoading: Boolean,
    popBackStack: () -> Unit,
    onUploadImage: (File) -> Unit = {},
    onPerformAccountBook: () -> Unit = {},
    onUpdateAmount: (Long) -> Unit = {},
    onUpdateAmountText: (String) -> Unit = {},
    onUpdateTransactionType: (AccountBookEntity.TransactionType) -> Unit = {},
    onUpdateTitle: (String) -> Unit = {},
    onRemoveImageUrl: (String) -> Unit = {},
    onUpdateRegisterDateTime: (String) -> Unit = {},
    onUpdateCategory: (AccountBookEntity.Category) -> Unit = {},
    modifier: Modifier
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uriList ->
            uriList.forEach { uri ->
                getFileFromUri(uri)?.let { file ->
                    onUploadImage(file)
                }
            }
        }
    )

    Surface(
        color = MaterialTheme.colors.white
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            DreamCenterTopAppBar(
                title = state.contentTitle,
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            imageVector = DreamIcon.Arrowleft,
                            contentDescription = "뒤로가기",
                            tint = Color.Black
                        )
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            if (!isLoading) {
                                onPerformAccountBook()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Text(
                            text = "등록",
                            color = MaterialTheme.colors.black
                        )
                    }
                },
                colorBackground = true
            )
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = Paddings.xlarge, vertical = Paddings.xlarge)
                    .imePadding(),
                verticalArrangement = Arrangement.spacedBy(Paddings.extra)
            ) {
                item {
                    Column {
                        AccountBookLabel(text = "금액")
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(52.dp)
                                    .background(
                                        color = MaterialTheme.colors.gray9,
                                        shape = Shapes.medium
                                    ),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                AccountBookCreateTextField(
                                    value = state.amountText ?: "",
                                    onValueChange = { newText ->
                                        val cleanedText = newText.replace(",", "")
                                        if (cleanedText.all { it.isDigit() }) {
                                            val newAmount = cleanedText.toLongOrNull() ?: 0L
                                            onUpdateAmount(newAmount)
                                            onUpdateAmountText(formatNumber(newAmount))
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
                                imageVector = DreamIcon.Edit,
                                contentDescription = null,
                                tint = MaterialTheme.colors.gray4
                            )
                        }
                    }
                }
                item {
                    Column {
                        AccountBookLabel(text = "분류")
                        Row {
                            AccountBookOptionButton(
                                width = 96.dp,
                                height = 40.dp,
                                option = "지출",
                                isSelected = state.transactionType == AccountBookEntity.TransactionType.EXPENSE,
                                onSelected = {
                                    onUpdateTransactionType(AccountBookEntity.TransactionType.EXPENSE)
                                }
                            )
                            Spacer(modifier = Modifier.width(Paddings.xlarge))
                            AccountBookOptionButton(
                                width = 96.dp,
                                height = 40.dp,
                                option = "수입",
                                isSelected = state.transactionType == AccountBookEntity.TransactionType.REVENUE,
                                onSelected = {
                                    onUpdateTransactionType(AccountBookEntity.TransactionType.REVENUE)
                                }
                            )
                        }
                    }
                }

                item {
                    Column {
                        AccountBookLabel(text = "카테고리")
                        AccountBookCreateButton(
                            onClick = { showBottomSheet = true },
                            text = state.category.getDisplay(),
                            icon = Icons.AutoMirrored.Filled.KeyboardArrowRight
                        )
                    }
                }

                item {
                    Column {
                        AccountBookLabel(text = "내역")
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .background(
                                    color = MaterialTheme.colors.gray9,
                                    shape = Shapes.medium
                                )
                        ) {
                            AccountBookCreateTextField(
                                value = state.title ?: "",
                                onValueChange = { newValue -> onUpdateTitle(newValue) },
                                placeholder = "입력하세요",
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                item {
                    Column {
                        AccountBookLabel("일자")
                        AccountBookCreateButton(
                            onClick = { showDatePicker = true },
                            text = state.registerDateTime,
                            icon = DreamIcon.DatePicker
                        )
                    }
                }

                item {
                    AccountBookLabel(text = "사진")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .background(
                                    color = MaterialTheme.colors.gray9,
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
                                        modifier = Modifier.padding(top = Paddings.medium)
                                    )
                                }
                            }
                        }
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Paddings.large),
                            horizontalArrangement = Arrangement.spacedBy(Paddings.large)
                        ) {
                            items(state.imageUrls) { imageUrl ->
                                Box(
                                    modifier = Modifier
                                        .size(130.dp)
                                        .clip(Shapes.small),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    AsyncImage(
                                        modifier = Modifier
                                            .size(120.dp)
                                            .clip(Shapes.small)
                                            .align(Alignment.BottomStart),
                                        model = imageUrl,
                                        contentDescription = "image",
                                        contentScale = ContentScale.Crop,
                                    )
                                    IconButton(
                                        onClick = {
                                            onRemoveImageUrl(imageUrl)
                                        },
                                        modifier = Modifier
                                            .size(24.dp)
                                            .align(Alignment.TopEnd),
                                        colors = IconButtonDefaults.iconButtonColors(
                                            containerColor = MaterialTheme.colors.gray1
                                        ),
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Clear,
                                            contentDescription = null,
                                            tint = MaterialTheme.colors.white,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
        if (isLoading) {
            CircleProgress()
        }
    }

    if (showDatePicker) {
        CustomDatePickerDialog(
            date = state.registerDateTime.parseLocalDate(),
            onClickCancel = { showDatePicker = false },
            onClickConfirm = { selectedDate ->
                onUpdateRegisterDateTime(selectedDate)
                showDatePicker = false
            }
        )
    }

    if (showBottomSheet) {
        AccountBookCategoryBottomSheet(
            onSelectedListener = { category ->
                onUpdateCategory(
                    category ?: AccountBookEntity.Category.OTHER
                )
                showBottomSheet = false
            },
            dismissBottomSheet = { showBottomSheet = false }
        )
    }
}

@Composable
internal fun AccountBookLabel(
    text: String,
    color: Color = MaterialTheme.colors.gray4,
    style: TextStyle = MaterialTheme.typo.body1,
    modifier: Modifier = Modifier.padding(top = Paddings.medium, bottom = Paddings.large)
) {
    Text(
        text = text,
        color = color,
        style = style,
        modifier = modifier
    )
}

@Composable
internal fun AccountBookCreateTextField(
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
internal fun AccountBookCreateButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(Color.Transparent),
    contentPadding: PaddingValues = PaddingValues(horizontal = Paddings.xlarge),
    icon: ImageVector? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(
                color = MaterialTheme.colors.gray9,
                shape = Shapes.medium
            )
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
}