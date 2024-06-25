package kr.co.main.community.detail

import android.icu.text.DecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.main.community.BulletinDetailMoreBottomSheet
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.theme.NBDreamTheme
import timber.log.Timber


@Composable
internal fun BulletinDetailRoute(
    popBackStack: () -> Unit,
    id: Long,
    modifier: Modifier = Modifier,
    viewModel: BulletinDetailViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = id) {
        viewModel.loadBulletin(id)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    BulletinDetailScreen(
        modifier = modifier,
        state = state,
        popBackStack = popBackStack,
        id = id,
        onCommentWritingInput = viewModel::onCommentWritingInput,
        setIsShowBulletinMoreBottomSheet = viewModel::setIsShowBulletinMoreBottomSheet,
        setIsShowDeleteCheckDialog = viewModel::setIsShowDeleteCheckDialog,
        deleteBulletin = viewModel::deleteBulletin,
        setIsShowFailedDialog = viewModel::setIsShowFailedDialog,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BulletinDetailScreen(
    modifier: Modifier = Modifier,
    state: BulletinDetailViewModel.State = BulletinDetailViewModel.State(),
    popBackStack: () -> Unit = {},
    id: Long = 0L,
    onCommentWritingInput: (String) -> Unit = {},
    setIsShowBulletinMoreBottomSheet: (Boolean) -> Unit = {},
    setIsShowDeleteCheckDialog: (Boolean) -> Unit = {},
    deleteBulletin: (() -> Unit, () -> Unit) -> Unit = { _, _ -> },
    setIsShowFailedDialog: (Boolean) -> Unit = {},
) {
    Scaffold(modifier = modifier) { paddingValues ->

//    Timber.d("currentDetailBulletinId: ${state.currentDetailBulletinId}")
        Timber.d("isLoadDetailSuccessful: ${state.isLoadDetailSuccessful}")
        if (!state.isLoadDetailSuccessful) {
            NoBulletinScreen(
                modifier = Modifier.scaffoldBackground(paddingValues),
                id = state.currentDetailBulletinId,
            )
            return@Scaffold
        }

        // check
        Timber.d("${state.currentDetailBulletin}")
        Timber.d("${state.currentDetailBulletin.bulletinId}")
        Timber.d(state.currentDetailBulletin.content)

        Column(
            modifier = Modifier.scaffoldBackground(paddingValues),
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
            ) {
                item {
                    Row {
                        IconButton(onClick = popBackStack) {
                            Icon(
                                painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_keyboard_arrow_left_24),
                                contentDescription = "뒤로가기 아이콘",
                            )
                        }
                        Text("무슨무슨 게시판")
                        Icon(
                            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_share_24),
                            contentDescription = "공유 아이콘",
                        )
                    }
                }
                item {
                    Row {
                        Image(
                            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.ic_person_32),
                            contentDescription = "프로필 사진",
                            modifier = Modifier
                                .background(
                                    color = Color.Gray,
                                    shape = CircleShape,
                                )
                                .padding(4.dp),
                        )
                        Column {
                            Text("닉네임")
                            Text("2000.00.00 00:00:00")
                        }
                        IconButton(onClick = {
                            setIsShowBulletinMoreBottomSheet(true)
                        }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "글 MoreVert",
                            )
                        }
                    }
                }
                item {
                    Text(
                        "본문",
                        modifier = Modifier.height(80.dp),
                    )
                }
                item {
                    Text(
                        "사진들",
                        modifier = Modifier.height(240.dp),
                    )
                }

                item {
                    Row {
                        Text("댓글 ${3}")
                        Card {
                            Row {
                                Icon(
                                    painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_bookmark_border_24),
                                    contentDescription = "북마크 빈 아이콘",
                                )
                                Text("50")
                            }
                        }
                        Text("등록순")
                        Text("최신순")
                    }
                }
                item {
                    HorizontalDivider()
                }
                items(Array(10) { it }) {
                    Row {
                        Image(
                            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.ic_person_32),
                            contentDescription = "댓글 프사",
                            modifier = Modifier
                                .background(
                                    color = Color.Gray,
                                    shape = CircleShape,
                                )
                                .padding(4.dp),
                        )
                        Text("댓글닉네임$it")
                        Text("2000.00.00 00:00:${DecimalFormat("00").format(it)}")
                        Icon(
                            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_more_vert_24),
                            contentDescription = "더보기",
                        )
                    }
                    Text("댓글 내용 $it")
                }
            }

            // 댓글 작성란
            Row {
                Image(
                    painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.ic_person_32),
                    contentDescription = "프로필 사진",
                    modifier = Modifier
                        .background(
                            color = Color.Gray,
                            shape = CircleShape,
                        )
                        .padding(4.dp),
                )
                TextField(
                    value = state.commentWritingInput,
                    onValueChange = onCommentWritingInput
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "보내기 아이콘"
                    )
                }
            }
        }

        if (state.isShowBulletinMoreBottomSheet) {
            BulletinDetailMoreBottomSheet(
                onDismissRequest = { setIsShowBulletinMoreBottomSheet(false) },
                onReportClick = { setIsShowFailedDialog(true) },
                onEditClick = { setIsShowFailedDialog(true) },
                onDeleteClick = { setIsShowDeleteCheckDialog(true) },
            )
        }

        if (state.isShowDeleteCheckDialog) {
            DialogYesOrNo(
                onDismissRequest = { setIsShowDeleteCheckDialog(false) },
                onConfirmation = {
                    deleteBulletin(
                        popBackStack,
                    ) { setIsShowFailedDialog(true) }
                },
                dialogTitle = "정말 삭제하시겠습니까?",
            )
        }

        if (state.isShowFailedDialog) {
            DialogSimpleText(
                onDismissRequest = { setIsShowFailedDialog(false) },
                text = "처리하지 못했습니다.",
            )
        }

    }
}

@Composable
fun DialogSimpleText(
    onDismissRequest: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    fontSize: Int = 18,
    textAlign: TextAlign = TextAlign.Center,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {},
        text = {
            Text(
                text = text,
                modifier = modifier.fillMaxWidth(),
                fontSize = fontSize.sp,
                textAlign = textAlign,
            )
        },
    )
}

@Preview
@Composable
fun DialogPreview(modifier: Modifier = Modifier) {
    DialogSimpleText(
        onDismissRequest = {},
        text = "처리하지 못했습니다.",
    )
}

@Composable
fun NoBulletinScreen(
    modifier: Modifier = Modifier,
    id: Long = -1,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "게시글을 찾을 수 없습니다."
        )
        Text("id: $id")
    }
}

@Composable
fun DialogYesOrNo(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String? = null,
    icon: ImageVector? = null,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                onDismissRequest()
                onConfirmation()
            }) {
                Text("확인")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("취소")
            }
        },
        icon = { icon?.let { Icon(it, contentDescription = "DialogYesOrNo Icon") } },
        title = { Text(dialogTitle) },
        text = { dialogText?.let { Text(it) } },
    )
}


@Preview(heightDp = 1200)
@Composable
private fun BulletinDetailScreenPreview() {
    NBDreamTheme {
        BulletinDetailScreen(popBackStack = {})
    }
}
