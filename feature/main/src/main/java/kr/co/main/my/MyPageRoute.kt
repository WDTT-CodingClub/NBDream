package kr.co.main.my

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kr.co.ui.ext.noRippleClickable
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Dots
import kr.co.ui.icon.dreamicon.Edit
import kr.co.ui.icon.dreamicon.Settings
import kr.co.ui.icon.dreamicon.Tobot
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamProgress
import kr.co.ui.widget.DreamTopAppBar

@Composable
internal fun MyPageRoute(
    viewModel: MyPageViewModel = hiltViewModel(),
    navigateToProfileEdit: () -> Unit = {},
    navigateToSetting: () -> Unit = {},
    navigateToBookmark: () -> Unit = {},
    navigateToWrite: () -> Unit = {},
    navigateToCropSelect: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    MyPageScreen(
        state = state,
        onCropDeleteClick = viewModel::onCropDeleteClick,
        navigateToProfileEdit = navigateToProfileEdit,
        navigateToSetting = navigateToSetting,
        navigateToBookmark = navigateToBookmark,
        navigateToWrite = navigateToWrite,
        navigateToCropSelect = navigateToCropSelect
    )

    DreamProgress(isVisible = isLoading)
}

@Composable
private fun MyPageScreen(
    state: MyPageViewModel.State = MyPageViewModel.State(),
    onCropDeleteClick: (String) -> Unit = {},
    navigateToProfileEdit: () -> Unit = {},
    navigateToSetting: () -> Unit = {},
    navigateToBookmark: () -> Unit = {},
    navigateToWrite: () -> Unit = {},
    navigateToCropSelect: () -> Unit = {},
) {
    Surface {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.gray9)
                .padding(horizontal = 16.dp)
        ) {
            item {
                DreamTopAppBar(
                    title = "마이페이지",
                ) {
                    Row {
                        IconButton(onClick = navigateToSetting) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = DreamIcon.Settings,
                                contentDescription = "edit"
                            )
                        }

                        IconButton(onClick = navigateToProfileEdit) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = DreamIcon.Edit,
                                contentDescription = "edit"
                            )
                        }
                    }
                }
            }

            item {
                ProfileCard(
                    imageUrl = state.profileImageUrl ?: "",
                    userName = state.name ?: "",
                    address = state.address ?: "주소를 설정해 주세요"
                )
            }

            item {
                Spacer(modifier = Modifier.height(48.dp))
                CommunityCard(
                    navigateToBookmark = navigateToBookmark,
                    navigateToWrite = navigateToWrite,
                )
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
                BulletinCard(
                    crops = state.crops,
                    showCropModal = navigateToCropSelect,
                    onCropDeleteClick = onCropDeleteClick
                )
                
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
private fun CommunityCard(
    navigateToBookmark: () -> Unit = {},
    navigateToWrite: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.white,
                shape = MaterialTheme.shapes.medium
            )
            .padding(24.dp),
    ) {
        Text(
            text = "커뮤니티",
            style = MaterialTheme.typo.h4,
            color = MaterialTheme.colors.gray1,
        )

        Column(
            modifier = Modifier
                .padding(
                    top = 32.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listOf(
                "저장한 글 보러가기",
                "작성한 글/댓글 보러가기",
            ).forEachIndexed { index, text ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .noRippleClickable {
                            when (index) {
                                0 -> navigateToBookmark()
                                1 -> navigateToWrite()
                            }
                        },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = text,
                        style = MaterialTheme.typo.body1,
                        color = MaterialTheme.colors.gray1
                    )
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = text,
                        tint = MaterialTheme.colors.gray5,
                    )
                }
                if (index < 1)
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colors.gray8
                    )
            }
        }

    }
}

@Composable
private fun BulletinCard(
    crops: List<String>? = listOf(),
    showCropModal: () -> Unit = {},
    onCropDeleteClick: (String) -> Unit = {},
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.white,
                shape = MaterialTheme.shapes.medium
            )
            .padding(24.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "재배 작물",
                style = MaterialTheme.typo.h4,
                color = MaterialTheme.colors.gray1,
            )

            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .noRippleClickable(onClick = showCropModal),
                imageVector = Icons.Filled.Add,
                contentDescription = "add crop button",
                tint = MaterialTheme.colors.primary
            )
        }

        Column(
            modifier = Modifier
                .padding(
                    top = 32.dp,
                )
                .animateContentSize(
                    animationSpec = TweenSpec(
                        durationMillis = 300
                    )
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (crops.isNullOrEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    text = "작물을 등록해 보세요",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typo.body1,
                    color = MaterialTheme.colors.gray1
                )
            } else {
                if (!expanded) {
                    CropRow(
                        crops = crops.take(4),
                        onCropDeleteClick = onCropDeleteClick
                    )
                } else {
                    CropRow(
                        crops = crops,
                        onCropDeleteClick = onCropDeleteClick
                    )
                }

                if (crops.size >= 4) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .noRippleClickable { expanded = !expanded },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "전체 보기",
                            style = MaterialTheme.typo.label,
                            color = MaterialTheme.colors.gray3
                        )

                        Icon(
                            modifier = Modifier
                                .size(20.dp)
                                .graphicsLayer {
                                    if (expanded)
                                        rotationZ = 180f
                                },
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = "extend bulletin list",
                            tint = MaterialTheme.colors.gray5
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CropRow(
    crops: List<String> = listOf(),
    onCropDeleteClick: (String) -> Unit = {},
) {
    crops.forEachIndexed { index, s ->
        var isDropDownVisible by rememberSaveable { mutableStateOf(false) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = s,
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray1
            )
            Box {
                Icon(
                    modifier = Modifier
                        .graphicsLayer {
                            rotationZ = 90f
                        }
                        .size(20.dp)
                        .noRippleClickable { isDropDownVisible = true },
                    imageVector = DreamIcon.Dots,
                    contentDescription = "작물 제거"
                )
                DropdownMenu(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colors.white,
                            shape = RoundedCornerShape(4.dp)
                        ),
                    expanded = isDropDownVisible,
                    onDismissRequest = { isDropDownVisible = false }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minWidth = 140.dp)
                            .padding(12.dp)
                            .clickable {
                                onCropDeleteClick(s)
                                isDropDownVisible = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "삭제하기",
                            style = MaterialTheme.typo.body1,
                            color = MaterialTheme.colors.gray4
                        )
                    }
                }
            }
        }

        if (index != crops.lastIndex) {
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colors.gray8
            )
        }
    }

}

@Composable
private fun ProfileCard(
    imageUrl: String = "",
    userName: String = "",
    address: String = "",
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape),
            model = imageUrl,
            error = rememberVectorPainter(image = DreamIcon.Tobot),
            contentDescription = "User's profile image",
            contentScale = ContentScale.Crop,
            placeholder = rememberVectorPainter(image = DreamIcon.Tobot)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = userName,
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray1
            )

            Text(
                text = address,
                style = MaterialTheme.typo.body2,
                color = MaterialTheme.colors.gray3
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        MyPageScreen()
    }
}