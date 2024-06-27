package kr.co.main.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kr.co.domain.entity.BulletinEntity
import kr.co.main.ui.DreamMainPostCard
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamTopAppBar

@Composable
internal fun CommunityRoute(
    navigateToWriting: () -> Unit,
    navigateToNotification: () -> Unit,
    navigateToBulletinDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CommunityViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CommunityScreen(
        modifier = modifier,
        state = state,
        event = viewModel as CommunityScreenEvent,
        navigateToWriting = navigateToWriting,
        navigateToNotification = navigateToNotification,
        navigateToBulletinDetail = navigateToBulletinDetail,
    )
}

@Composable
internal fun CommunityScreen(
    modifier: Modifier = Modifier,
    state: CommunityViewModel.State = CommunityViewModel.State(),
    event: CommunityScreenEvent = CommunityScreenEvent.dummy,
    navigateToWriting: () -> Unit = {},
    navigateToNotification: () -> Unit = {},
    navigateToBulletinDetail: (Long) -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            DreamTopAppBar(
                title = "${state.currentBoard.koreanName} 게시판",
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                Row(
                    modifier = Modifier.clickable(onClick = navigateToWriting)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "게시판 탑바 Add 아이콘")
//                    TextButton(onClick = navigateToWriting) {
//                    }
                    Text("글 쓰기")
                }
            }
        },
        containerColor = MaterialTheme.colors.gray9,
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier.scaffoldBackground(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            item {
                CommunityCategoryTabLayout(
                    selectedTab = state.currentCategory,
                    onSelectTab = { event.onCategoryClick(it) },
                )
            }
            item {
                TextField(
                    value = state.searchInput,
                    onValueChange = {
                        if ("\n" !in it) event.onSearchInputChanged(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("검색어를 입력하세요") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
                    },
                )
            }
            if (state.bulletinEntities.isEmpty()) {
                item {
                    Text("게시물이 없습니다.")
                }
            }
            // TODO: 나중에 테스트 필요 없어지면 index 필요 없음.
            itemsIndexed(
                state.bulletinEntities
            ) { idx, bulletin ->
                // TODO: -ing
                DreamMainPostCard(
                    bulletin = bulletin,
                    onPostClick = { navigateToBulletinDetail(bulletin.bulletinId) },
                    onBookMarkClick = { event.bookmarkBulletin(bulletin.bulletinId) },
                )
            }
        }
    }
}

@Composable
internal fun BulletinCard(
    bulletin: BulletinEntity,
    navigateToBulletinDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navigateToBulletinDetail(bulletin.bulletinId)
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 0.dp,
        ),  // ?
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
        ) {
            Column {
                Row(
                ) {
                    Image(
                        painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.ic_person_32),
                        contentDescription = "프로필 사진",
                        modifier = Modifier
                            .width(54.dp)
                            .height(54.dp)
                            .background(
                                color = Color.Gray,
                                shape = CircleShape,
                            )
                            .padding(4.dp),
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("${bulletin.authorId}의닉네임")
                        Text(
                            text = bulletin.createdTime.toString(),
                            color = Color.Gray,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    // TODO: FilledIconToggleButton 으로 변경
                    if (bulletin.bookmarked) Icon(
                        painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_bookmark_24),
                        contentDescription = "북마크 채워진 아이콘",
                    )
                    else Icon(
                        painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_bookmark_border_24),
                        contentDescription = "북마크 빈 아이콘",
                    )
                    Text("${bulletin.bookmarkedCount}")
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text(bulletin.content)
                // TODO: 사진 0~3개 표시하는 컴포저블
                if (bulletin.imageUrls.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    AsyncImage(
                        model = bulletin.imageUrls[0],
                        contentDescription = "글 사진",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
                if (bulletin.comments.isNotEmpty()) {
                    val comment = bulletin.comments[0]
                    Spacer(modifier = Modifier.height(12.dp))
                    Row {
                        Text(
                            text = "댓글 ${bulletin.comments.size}개",
                            color = Color.Gray,
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    // TODO: 댓글 컴포저블
                    Row {
                        Image(
                            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.ic_person_32),
                            contentDescription = "댓글 프사",
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                                .background(
                                    color = Color.Gray,
                                    shape = CircleShape,
                                )
                                .padding(4.dp),
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(comment.nickname)
                            Text(comment.content)
                        }
                    }
                }

//                // TODO: 확인용, 나중에 지울 것.
//                else {
//                    Row {
////                        Icon(
////                            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_comment_24),
////                            contentDescription = "댓글 아이콘",
////                        )
//                        Text("댓글 654개")
//                    }
//                    Row {
//                        Image(
//                            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.ic_person_32),
//                            contentDescription = "댓글 프사",
//                            modifier = Modifier
//                                .background(
//                                    color = Color.Gray,
//                                    shape = CircleShape,
//                                )
//                                .padding(4.dp),
//                        )
//                        Text("댓글닉네임")
//                    }
//                    Text("댓글 내용")
//                }

            }
        }
    }
}

@Composable
private fun CommunityCategoryTabLayout(
    selectedTab: BulletinEntity.BulletinCategory,
    onSelectTab: (BulletinEntity.BulletinCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        BulletinEntity.BulletinCategory.entries.forEach {
            CommunityCategoryTabLayoutItem(
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        onSelectTab(it)
                    },
                title = it.koreanName,
                isSelected = (it == selectedTab)
            )
        }
    }
}

@Composable
private fun measureTextWidth(text: String, style: TextStyle): Dp {
    val textMeasurer = rememberTextMeasurer()
    val widthInPixels = textMeasurer.measure(text, style).size.width
    return with(LocalDensity.current) { widthInPixels.toDp() }
}

@Composable
private fun CommunityCategoryTabLayoutItem(
    title: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val textWidth = measureTextWidth(
        text = title,
        style = MaterialTheme.typo.h2
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.width(textWidth),
            text = title,
            style = MaterialTheme.typo.h2,
            color = if (isSelected) MaterialTheme.colors.text1 else MaterialTheme.colors.text2,
            textAlign = TextAlign.Center
        )
        HorizontalDivider(
            modifier = Modifier.width(textWidth),
            thickness = 2.dp,
            color = if (isSelected) Color.Black else Color.Transparent
        )
    }
}


@Preview
@Composable
private fun BulletinCardPreview() {
    NBDreamTheme {
        BulletinCard(
            bulletin = BulletinEntity.dummy(),
            navigateToBulletinDetail = {},
        )
    }
}

@Preview
@Composable
private fun CommunityScreenPreview() {
    NBDreamTheme {
        CommunityScreen(
            state = CommunityViewModel.State(
                bulletinEntities = List(10) { i -> BulletinEntity.dummy(i) },
            )
        )
    }
}
