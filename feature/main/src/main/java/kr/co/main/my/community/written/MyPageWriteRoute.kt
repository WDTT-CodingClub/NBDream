@file:OptIn(ExperimentalFoundationApi::class)

package kr.co.main.my.community.written

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kr.co.core.ui.icon.buttericon.Bookmarkoff
import kr.co.main.R
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.icon.DreamIcon
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar

@Composable
internal fun MyPageWriteRoute(
    viewModel: MyPageWriteViewModel = hiltViewModel(),
    popBackStack: () -> Unit = {},
    navigateToBulletinDetail: (Long) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MyPageCommunityScreen(
        state = state,
        popBackStack = popBackStack,
        navigateToBulletinDetail = navigateToBulletinDetail
    )
}

@Composable
private fun MyPageCommunityScreen(
    state: MyPageWriteViewModel.State = MyPageWriteViewModel.State(),
    pagerState: PagerState = rememberPagerState { 2 },
    scope: CoroutineScope = rememberCoroutineScope(),
    popBackStack: () -> Unit = {},
    navigateToBulletinDetail: (Long) -> Unit = {}
) {
    Scaffold(
        containerColor = MaterialTheme.colors.background,
        topBar = {
            DreamCenterTopAppBar(
                modifier = Modifier.background(MaterialTheme.colors.background),
                title = "작성한 글/댓글 보기",
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = stringResource(R.string.feature_main_pop_back_stack)
                        )
                    }
                }
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .scaffoldBackground(
                    scaffoldPadding = scaffoldPadding,
                    padding = PaddingValues(0.dp)
                )
                .background(MaterialTheme.colors.background)
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        height = 3.dp,
                        color = Color(0xFF292929)
                    )
                }
            ) {
                listOf("작성한 글", "작성한 댓글").forEachIndexed { index, s ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        selectedContentColor = MaterialTheme.colors.gray1,
                        unselectedContentColor = MaterialTheme.colors.gray7,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }) {
                        Text(
                            modifier = Modifier.padding(vertical = 10.dp),
                            text = s,
                            style = MaterialTheme.typo.mainDate,
                            color = if (pagerState.currentPage == index) MaterialTheme.colors.gray1 else MaterialTheme.colors.gray5
                        )
                    }
                }
            }

            HorizontalPager(
                beyondBoundsPageCount = pagerState.pageCount,
                userScrollEnabled = false,
                state = pagerState
            ) {
                when (pagerState.currentPage) {
                    0 -> ContentPage(
                        items = state.bulletins,
                        emptyText = "아직 작성한 게시글이 없어요",
                        content = { bulletin ->
                            PostCard(
                                bulletin = bulletin,
                                navigateToBulletinDetail = navigateToBulletinDetail
                            )
                        }
                    )
                    1 -> ContentPage(
                        items = state.comments,
                        emptyText = "아직 작성한 댓글이 없어요",
                        content = { comment ->
                            CommentCard(
                                comment = comment,
                                navigateToBulletinDetail = navigateToBulletinDetail
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun <T> ContentPage(
    items: List<T>,
    emptyText: String,
    content: @Composable (T) -> Unit
) {
    if (items.isEmpty()) {
        EmptyCard(emptyText)
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(1.dp)) }
            items(items) { item ->
                content(item)
            }
        }
    }
}

@Composable
private fun EmptyCard(
    text: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(
                375 / 780f
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = MaterialTheme.typo.h4,
            color = MaterialTheme.colors.gray5
        )
    }
}

@Composable
private fun PostCard(
    bulletin: MyPageWriteViewModel.State.Bulletin,
    navigateToBulletinDetail: (Long) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navigateToBulletinDetail(bulletin.id) }
            .background(
                color = MaterialTheme.colors.white,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(
                horizontal = 24.dp,
                vertical = 16.dp
            )
    ) {
        Column(
            modifier = Modifier
                .height(90.dp)
                .weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = bulletin.content,
                    style = MaterialTheme.typo.body1,
                    color = MaterialTheme.colors.gray1,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )

                Text(
                    text = bulletin.createdAt,
                    style = MaterialTheme.typo.body2,
                    color = MaterialTheme.colors.gray5
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "댓글 ${bulletin.commentCount}개",
                    fontFamily = MaterialTheme.typo.body1.fontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 26.sp,
                    color = MaterialTheme.colors.gray5
                )

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = DreamIcon.Bookmarkoff,
                    contentDescription = "북마크한 유저수",
                    tint = MaterialTheme.colors.gray5
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = bulletin.bookmarkedCount.toString(),
                    style = MaterialTheme.typo.body2,
                    color = MaterialTheme.colors.gray5
                )
            }
        }

        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 10.dp),
            model = bulletin.thumbnail,
            contentDescription = "작성한 글 이미지",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun CommentCard(
    comment: MyPageWriteViewModel.State.Comment,
    navigateToBulletinDetail: (Long) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navigateToBulletinDetail(comment.bulletinId) }
            .background(
                color = MaterialTheme.colors.white,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(
                horizontal = 24.dp,
                vertical = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = comment.authorName,
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray1,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )

        Text(
            text = comment.content,
            style = MaterialTheme.typo.body2,
            color = MaterialTheme.colors.gray5
        )

        Text(
            text = comment.createAt.let { "${it.year}/${it.monthValue}/${it.dayOfMonth} ${it.hour}:${it.minute}" },
            style = MaterialTheme.typo.body2,
            color = MaterialTheme.colors.gray5
        )
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        MyPageCommunityScreen()
    }
}