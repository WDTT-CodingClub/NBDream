package kr.co.main.community.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kr.co.common.util.format
import kr.co.domain.entity.CommentEntity
import kr.co.main.R
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamBottomSheetWithTextButtons
import kr.co.ui.widget.DreamCenterTopAppBar
import kr.co.ui.widget.DreamDialog
import kr.co.ui.widget.TextAndOnClick
import timber.log.Timber


@Composable
internal fun BulletinDetailRoute(
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BulletinDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BulletinDetailScreen(
        modifier = modifier,
        state = state,
        event = viewModel as BulletinDetailEvent,
        popBackStack = popBackStack,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun BulletinDetailScreen(
    modifier: Modifier = Modifier,
    state: BulletinDetailViewModel.State = BulletinDetailViewModel.State(),
    event: BulletinDetailEvent = BulletinDetailEvent.dummy,
    popBackStack: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            DreamCenterTopAppBar(
                title = state.currentCategory.koreanName,
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = stringResource(R.string.feature_main_pop_back_stack)
                        )
                    }
                },
                actions = {
                    val iconId =
                        if (state.currentDetailBulletin.bookmarked) kr.co.nbdream.core.ui.R.drawable.baseline_bookmark_24
                        else kr.co.nbdream.core.ui.R.drawable.baseline_bookmark_border_24
                    IconButton(onClick = event::bookmarkBulletin) {
                        Icon(
                            painter = painterResource(id = iconId),
                            contentDescription = "북마크 아이콘",
                            modifier = Modifier.size(32.dp),
                        )
                    }
                },
            )
        },
    ) { paddingValues ->

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
            modifier = Modifier.scaffoldBackground(
                scaffoldPadding = PaddingValues(top = paddingValues.calculateTopPadding()),
                padding = PaddingValues(horizontal = 24.dp),
            ),
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
            ) {
                item {
                    Spacer(modifier = Modifier.height(52.dp))
                }
                item {
                    Row {
                        AsyncImage(
                            model = state.currentDetailBulletin.profileImageUrl,
                            contentDescription = "글쓴이 프로필 사진",
                            modifier = modifier
                                .width(54.dp)
                                .height(54.dp)
                                .clip(CircleShape),
                            placeholder = painterResource(id = kr.co.nbdream.core.ui.R.drawable.ic_person_32),
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                state.currentDetailBulletin.nickname,
                                color = MaterialTheme.colors.gray1,
                                style = MaterialTheme.typo.body1,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                state.currentDetailBulletin.createdTime.toString(),
                                color = MaterialTheme.colors.gray5,
                                style = MaterialTheme.typo.body2,
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            event.showBottomSheet(
                                listOf(
                                    TextAndOnClick("신고하기") { event.setIsShowFailedDialog(true) },
                                    TextAndOnClick("수정하기") { event.setIsShowFailedDialog(true) },
                                    TextAndOnClick("삭제하기") { event.setIsShowDeleteCheckDialog(true) },
                                ),
                            )

                            event.setIsShowBulletinMoreBottomSheet(true)
                        }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "글 MoreVert",
                                tint = MaterialTheme.colors.gray5,
                            )
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
                item {
                    Text(
                        state.currentDetailBulletin.content,
                        color = MaterialTheme.colors.black,
                        style = MaterialTheme.typo.body1,
                    )
                }
                item { Spacer(modifier = Modifier.height(12.dp)) }
                item {
                    val pagerState = rememberPagerState(pageCount = {
                        state.currentDetailBulletin.imageUrls.size
                    })
                    // TODO: 페이저 화살표 표시. 끝단에서는 해당 방향 화살표 숨기기.
                    // TODO: 인디케이터...는 나중에...
                    HorizontalPager(state = pagerState) { page ->
                        // TODO: 사진 사이즈 설정해야 할까?
                        AsyncImage(
                            model = state.currentDetailBulletin.imageUrls[page],
                            contentDescription = "사진 $page",
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 500.dp),
                            placeholder = painterResource(id = kr.co.nbdream.core.ui.R.drawable.place_holder_1),
                        )
                    }
                }
                item { Spacer(modifier = Modifier.height(40.dp)) }
                item {
                    Row {
                        Text(
                            "댓글 ${state.currentDetailBulletin.comments.size}개",
                            color = MaterialTheme.colors.gray1,
                            style = MaterialTheme.typo.h4,
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "북마크 ${state.currentDetailBulletin.bookmarkedCount}개",
                            color = MaterialTheme.colors.gray1,
                            style = MaterialTheme.typo.h4,
                        )

//                        Card {
//                            Row {
//                                Icon(
//                                    painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_bookmark_border_24),
//                                    contentDescription = "북마크 빈 아이콘",
//                                )
//                                Text(state.currentDetailBulletin.bookmarkedCount.toString())
//                            }
//                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
                items(state.currentDetailBulletin.comments) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CommentItem(
                        comment = it,
                        isAuthor = state.currentDetailBulletin.authorId == it.memberId,
                        onMoreVertClick = {
                            event.showBottomSheet(
                                listOf(
                                    TextAndOnClick("삭제하기") {
                                        event.showDialog(
                                            header = "정말 삭제하시겠습니까?",
                                            description = "",
                                            onConfirm = { event.deleteComment(it.commentId) },
                                            onDismiss = { event.setIsShowDialog(false) },
                                        )
                                    },
                                )
                            )
                        },
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // 댓글 작성란
            // TODO: ui
            Row(
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    model = null,
                    contentDescription = "본인 프로필 사진",
                    modifier = modifier
                        .width(40.dp)
                        .height(40.dp)
                        .clip(CircleShape),
                    placeholder = painterResource(id = kr.co.nbdream.core.ui.R.drawable.ic_person_32),
                )
                TextField(
                    value = state.commentWritingInput,
                    onValueChange = event::onCommentWritingInput,
                    modifier = Modifier.weight(1f),
                )
                IconButton(onClick = event::onPostCommentClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "보내기 아이콘",
                        modifier = Modifier.size(32.dp),
                    )
                }
            }
        }

        if (state.isShowBulletinMoreBottomSheet) {
            DreamBottomSheetWithTextButtons(
                onDismissRequest = { event.setIsShowBulletinMoreBottomSheet(false) },
                textAndOnClicks = state.bottomSheetItems,
            )
        }

        if (state.isShowDeleteCheckDialog) {
            DialogYesOrNo(
                onDismissRequest = { event.setIsShowDeleteCheckDialog(false) },
                onConfirmation = {
                    event.deleteBulletin(
                        popBackStack,
                    ) { event.setIsShowFailedDialog(true) }
                },
                dialogTitle = "정말 삭제하시겠습니까?",
            )
        }

        if (state.isShowFailedDialog) {
            DialogSimpleText(
                onDismissRequest = { event.setIsShowFailedDialog(false) },
                text = "처리하지 못했습니다.",
            )
        }

        if (state.isShowDialog) {
            DreamDialog(
                header = state.dialogHeader,
                description = state.dialogDescription,
                onConfirm = state.dialogOnConfirm,
                onDismiss = state.dialogOnDismiss,
            )
        }

    }
}

@Composable
private fun CommentItem(
    comment: CommentEntity,
    isAuthor: Boolean,
    onMoreVertClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row {
        AsyncImage(
            model = comment.profileImageUrl,
            contentDescription = "댓글 프로필 사진",
            modifier = modifier
                .width(40.dp)
                .height(40.dp)
                .clip(CircleShape),
            placeholder = painterResource(id = kr.co.nbdream.core.ui.R.drawable.ic_person_32),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Row {
                Text(
                    comment.nickname,
                    color = MaterialTheme.colors.gray1,
                    style = MaterialTheme.typo.body1,
                )
                if (isAuthor) {
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "작성자",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typo.body2,
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                comment.content,
                color = MaterialTheme.colors.gray1,
                style = MaterialTheme.typo.body1,
            )
            Spacer(modifier = Modifier.height(4.dp))
            // TODO: 몇 분 전 형태 표시
            Text(
                comment.createdTime.format("yyyy/MM/dd HH:mm:ss"),
                color = MaterialTheme.colors.gray5,
                style = MaterialTheme.typo.body2,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onMoreVertClick) {
            Icon(
                painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_more_vert_24),
                contentDescription = "더보기",
                tint = MaterialTheme.colors.gray5,
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
        BulletinDetailScreen(
            popBackStack = {},
        )
    }
}
