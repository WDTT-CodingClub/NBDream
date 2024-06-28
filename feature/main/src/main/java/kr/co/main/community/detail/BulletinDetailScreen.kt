package kr.co.main.community.detail

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kr.co.common.util.format
import kr.co.domain.entity.BulletinEntity
import kr.co.domain.entity.CommentEntity
import kr.co.main.R
import kr.co.main.accountbook.main.CircleProgress
import kr.co.main.community.CommunityDialogSimpleTitle
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
    navigateToUpdate: (Long) -> Unit,
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BulletinDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    BulletinDetailScreen(
        modifier = modifier,
        state = state,
        event = viewModel as BulletinDetailEvent,
        popBackStack = popBackStack,
        navigateToUpdate = navigateToUpdate,
        isLoading = isLoading
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BulletinDetailScreen(
    modifier: Modifier = Modifier,
    state: BulletinDetailViewModel.State = BulletinDetailViewModel.State(),
    event: BulletinDetailEvent = BulletinDetailEvent.empty,
    popBackStack: () -> Unit = {},
    navigateToUpdate: (Long) -> Unit = {},
    isLoading: Boolean = false,
) {
    Scaffold(
        modifier = modifier,
        containerColor = Color.White,
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

        if (!state.isInitialLoadingFinished) {
            Surface(Modifier.fillMaxSize()) {}
            return@Scaffold
        }

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
                            error = painterResource(id = kr.co.nbdream.core.ui.R.drawable.ic_person_32),
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
                                state.currentDetailBulletin.createdTime,
                                color = MaterialTheme.colors.gray5,
                                style = MaterialTheme.typo.body2,
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            event.showBottomSheet(
                                if (state.currentDetailBulletin.author) listOf(
                                    TextAndOnClick("수정하기") { navigateToUpdate(state.currentDetailBulletinId) },
                                    TextAndOnClick("삭제하기") {
                                        event.showDialog(
                                            header = "정말 삭제하시겠습니까?",
                                            description = "",
                                            onConfirm = {
                                                event.deleteBulletin(
                                                    popBackStack,
                                                    event::showFailedDialog,
                                                )
                                            },
                                            onDismiss = { event.setIsShowDialog(false) },
                                        )
                                    },
                                ) else listOf(
                                    TextAndOnClick("신고하기") { event.showReportBottomSheet() },
                                )
                            )
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
                    ImageViewPager(state, event)
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
                                if (it.isAuthor) listOf(
                                    TextAndOnClick("삭제하기") {
                                        event.showDialog(
                                            header = "정말 삭제하시겠습니까?",
                                            description = "",
                                            onConfirm = { event.deleteComment(it.commentId) },
                                            onDismiss = { event.setIsShowDialog(false) },
                                        )
                                    },
                                ) else listOf(
                                    TextAndOnClick("신고하기") { event.showReportBottomSheet() },
                                )
                            )
                        },
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            if (isLoading) {
                CircleProgress()
            }

            BottomCommentWritingBar(state, event)
        }


        if (state.isShowDreamBottomSheetWithTextButtons) {
            DreamBottomSheetWithTextButtons(
                onDismissRequest = { event.setIsShowDreamBottomSheetWithTextButtons(false) },
                textAndOnClicks = state.bottomSheetItems,
            )
        }

        if (state.isShowSimpleDialog) {
            CommunityDialogSimpleTitle(
                onDismissRequest = { event.setIsShowSimpleDialog(false) },
                text = state.simpleDialogText,
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ImageViewPager(
    state: BulletinDetailViewModel.State,
    event: BulletinDetailEvent,
//    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = {
        state.currentDetailBulletin.imageUrls.size
    })
    Box {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 500.dp),
            verticalAlignment = Alignment.Top,
        ) { page ->
            AsyncImage(
                model = state.currentDetailBulletin.imageUrls[page],
                contentDescription = "사진 $page",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium),
//                placeholder = painterResource(id = kr.co.nbdream.core.ui.R.drawable.place_holder_1),  // 테스트용
//            error = painterResource(id = kr.co.nbdream.core.ui.R.drawable.place_holder_1),  // 왜 로딩이 되기 전에 먼저 뜨는지 모르겠다...
            )
        }
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                //val color = if (pagerState.currentPage == iteration) Color.White else Color(0x52FFFFFF)  // 잘 안보이는데?
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
        if (pagerState.currentPage != 0) {
            PagerArrowBox(
                false,
                modifier = Modifier.align(Alignment.CenterStart),
            )
        }
        if (pagerState.currentPage < pagerState.pageCount - 1) {
            PagerArrowBox(
                true,
                modifier = Modifier.align(Alignment.CenterEnd),
            )
        }
    }
}

@Composable
private fun PagerArrowBox(
    isRight: Boolean,
    modifier: Modifier = Modifier,
) {
    val icon =
        if (isRight) Icons.AutoMirrored.Rounded.KeyboardArrowRight else Icons.AutoMirrored.Rounded.KeyboardArrowLeft
    Box(
        modifier = modifier
            .size(32.dp)
            .background(Color(0x22000000)),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "뷰페이저 화살표 아이콘",
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Center),
            tint = Color.White,
        )
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
            error = painterResource(id = kr.co.nbdream.core.ui.R.drawable.ic_person_32),
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
private fun BottomCommentWritingBar(
    state: BulletinDetailViewModel.State,
    event: BulletinDetailEvent,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    BasicTextField(
        modifier = modifier
            .background(Color.Transparent)
            .fillMaxWidth()
            .navigationBarsPadding()
            .imePadding()
            .padding(bottom = 12.dp)
            .background(
                color = MaterialTheme.colors.gray9,
                shape = CircleShape,
            )
            .padding(
                horizontal = 20.dp,
                vertical = 4.dp
            ),
        value = state.commentWritingInput,
        onValueChange = event::onCommentWritingInput,
        textStyle = MaterialTheme.typo.body1.copy(color = MaterialTheme.colors.gray1),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            event.onPostCommentClick()
            keyboardController?.hide()
        }),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            Box(
                modifier = Modifier.weight(1f),
            ) {
                it()
            }
            TextButton(
                onClick = {
                    event.onPostCommentClick()
                    keyboardController?.hide()
                },
                enabled = state.commentWritingInput.isNotBlank(),
            ) {
                Text(
                    "보내기",
                    color = if (state.commentWritingInput.isNotBlank()) MaterialTheme.colors.gray5
                    else MaterialTheme.colors.gray7,
                    style = MaterialTheme.typo.button,
                )
            }
        }
    }
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


@Preview(heightDp = 1200)
@Composable
private fun BulletinDetailScreenPreview() {
    NBDreamTheme {
        BulletinDetailScreen(
            state = BulletinDetailViewModel.State(currentDetailBulletin = BulletinEntity.dummy(3)),
        )
    }
}
