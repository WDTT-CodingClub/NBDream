package kr.co.main.community.writing

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kr.co.domain.entity.BulletinEntity
import kr.co.main.R
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.Shapes
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar

@Composable
internal fun BulletinWritingRoute(
    popBackStack: () -> Unit,
    navigationToDetail: (Long) -> Unit,
    navigationToCommunity: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BulletinWritingViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.complete.collect { complete ->
            if (complete) {
                state.id?.let { id -> navigationToDetail(id) } ?: run {
                    navigationToCommunity()
                }
            }
        }
    }

    BulletinWritingScreen(
        modifier = modifier,
        state = state,
        popBackStack = popBackStack,
        onAddImagesClick = viewModel::onAddImagesClick,
        onBulletinWritingInputChanged = viewModel::onBulletinWritingInputChanged,
        onRemoveImageClick = viewModel::onRemoveImageClick,
        onFinishWritingClick = viewModel::onPerformBulletin,
        setIsShowWaitingDialog = viewModel::setIsShowWaitingDialog,
        onCategoryClick = viewModel::onCategoryClick,
    )
}

@Composable
internal fun BulletinWritingScreen(
    modifier: Modifier = Modifier,
    state: BulletinWritingViewModel.State = BulletinWritingViewModel.State(),
    popBackStack: () -> Unit = {},
    onAddImagesClick: (uris: List<Uri>) -> Unit = {},
    onBulletinWritingInputChanged: (input: String) -> Unit = {},
    onRemoveImageClick: (model: WritingSelectedImageModel) -> Unit = {},
    onFinishWritingClick: () -> Unit = {},
    setIsShowWaitingDialog: (Boolean) -> Unit = {},
    onCategoryClick: (BulletinEntity.BulletinCategory) -> Unit = {},
) {

    Scaffold(
        modifier = modifier
            .navigationBarsPadding()
            .imePadding(),
        topBar = {
            DreamCenterTopAppBar(
                title = "글쓰기",
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = stringResource(R.string.feature_main_pop_back_stack)
                        )
                    }
                },
                actions = {
                    TextButton(onClick = {
                        setIsShowWaitingDialog(true)
                        onFinishWritingClick()
                    }) {
                        Text(
                            text = "등록",
                            style = MaterialTheme.typo.body2,
                            color = MaterialTheme.colors.gray1
                        )
                    }
                },
                colorBackground = true
            )
        },
        contentColor = MaterialTheme.colors.white
    ) { paddingValues ->

        val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(),
            onResult = onAddImagesClick,
        )

        LazyColumn(
            modifier = Modifier
                .scaffoldBackground(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
//            item {
//                Row {
//                    IconButton(onClick = popBackStack) {
//                        Icon(
//                            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_keyboard_arrow_left_24),
//                            contentDescription = "뒤로가기 아이콘",
//                        )
//                    }
//                    Text(
//                        "${state.currentBoard.koreanName} 글쓰기",
//                        modifier = Modifier.weight(1f),
//                    )
//                    TextButton(onClick = {
//                        setIsShowWaitingDialog(true)
//                        onFinishWritingClick(popBackStack)
//                    }) {
////                TextButton(onClick = onFinishWritingClick) {
//                        Text(
//                            "등록",
//                            Modifier.padding(horizontal = 20.dp, vertical = 0.dp),
//                        )
//                    }
//                }
//            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    for (category in BulletinEntity.BulletinCategory.entries) {
                        CategoryButton(
                            onClick = { onCategoryClick(category) },
                            text = category.koreanName,
                            isSelected = state.currentCategory == category
                        )
                    }
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colors.gray9,
                            shape = Shapes.medium
                        )
                ) {
                    TextField(
                        value = state.bulletinWritingInput,
                        onValueChange = onBulletinWritingInputChanged,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            cursorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .heightIn(min = 300.dp),
                        placeholder = {
                            Text(
                                text = "'${state.currentBoard.koreanName}'에 대해 이야기해보세요!",
                                color = MaterialTheme.colors.gray4,
                                style = MaterialTheme.typo.body1
                            )
                        },
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${state.bulletinWritingInput.length}/3000",
                        style = MaterialTheme.typo.label,
                        color = MaterialTheme.colors.gray4,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }

            }
            item {
                Text(
                    text = "사진",
                    style = MaterialTheme.typo.h4
                )
            }
            item {
                LazyRow(
                    modifier = Modifier.height(120.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colors.gray9)
                                .clickable {
                                    multiplePhotoPickerLauncher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                },
                            contentAlignment = Alignment.Center,
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.outline_photo_camera_24),
                                    contentDescription = "카메라 아이콘",
                                    tint = MaterialTheme.colors.gray5
                                )
                                Text(
                                    text = "사진 추가",
                                    color = MaterialTheme.colors.gray5,
                                    style = MaterialTheme.typo.button,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    }
                    items(state.writingImages) {
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colors.gray9),
                            contentAlignment = Alignment.Center,
                        ) {
                            AsyncImage(
                                model = it.uri,
                                contentDescription = "image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(120.dp)
                            )
                            IconButton(
                                onClick = { onRemoveImageClick(it) },
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.TopEnd),
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = MaterialTheme.colors.gray1
                                ),
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "삭제 아이콘",
                                    tint = MaterialTheme.colors.white,
                                )
                            }
                        }
                    }
                }
            }
            item {}
        }
    }
}

@Composable
private fun CategoryButton(
    onClick: () -> Unit,
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        shape = Shapes.medium,
        colors = if (isSelected) ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colors.gray4,
        ) else ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colors.gray8,
        ),
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = if (isSelected) MaterialTheme.colors.white else MaterialTheme.colors.gray4,
            style = MaterialTheme.typo.body1
        )
    }
}

@Preview(heightDp = 1200)
@Composable
private fun BulletinWritingScreenPreview() {
    NBDreamTheme {
        BulletinWritingScreen()
    }
}
