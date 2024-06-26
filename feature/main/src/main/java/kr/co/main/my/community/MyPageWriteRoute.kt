@file:OptIn(ExperimentalFoundationApi::class)

package kr.co.main.my.community

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MyPageCommunityScreen(
        state = state,
        popBackStack = popBackStack
    )
}

@Composable
private fun MyPageCommunityScreen(
    state: MyPageWriteViewModel.State = MyPageWriteViewModel.State(),
    pagerState: PagerState = rememberPagerState { 2 },
    scope: CoroutineScope = rememberCoroutineScope(),
    popBackStack: () -> Unit = {},
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
                        modifier =  Modifier
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(17.dp))
                    }
                    item {
                        when (pagerState.currentPage) {
                            0 -> {
                                PostCard()
                            }

                            1 -> {
                                CommentCard()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PostCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
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
                    text = "불타는 감자",
                    style = MaterialTheme.typo.body1,
                    color = MaterialTheme.colors.gray1,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )

                Text(
                    text = "2024/05/08 23:11:01",
                    style = MaterialTheme.typo.body2,
                    color = MaterialTheme.colors.gray5
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "댓글 29개",
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
                    text = "50",
                    style = MaterialTheme.typo.body2,
                    color = MaterialTheme.colors.gray5
                )
            }
        }

        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 10.dp),
            model = "https://storage.googleapis.com/nbdream_bucket_1/default/default-profile.png",
            contentDescription = "작성한 글 이미지",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun CommentCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
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
            text = "불타는 감자가요?",
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray1,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )

        Text(
            text = "어쩌구 저쩌구 댓글 내용",
            style = MaterialTheme.typo.body2,
            color = MaterialTheme.colors.gray5
        )

        Text(
            text = "2024/05/08 23:11:01",
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