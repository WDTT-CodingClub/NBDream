/** ui 작업 이전 버전 */

//package kr.co.main.community
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.itemsIndexed
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Create
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardColors
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.material3.TextField
//import androidx.compose.material3.VerticalDivider
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import kr.co.domain.entity.BulletinEntity
//import kr.co.ui.theme.NBDreamTheme
//
//@Composable
//internal fun CommunityRoute_bkup1(
//    navigateToWriting: () -> Unit,
//    navigateToNotification: () -> Unit,
//    navigateToBulletinDetail: (Long) -> Unit,
//    modifier: Modifier = Modifier,
//    viewModel: CommunityViewModel = hiltViewModel(),
//) {
//    val state by viewModel.state.collectAsStateWithLifecycle()
//    CommunityScreen_bkup1(
//        modifier = modifier,
//        state = state,
//        navigateToWriting = navigateToWriting,
//        navigateToNotification = navigateToNotification,
//        navigateToBulletinDetail = navigateToBulletinDetail,
//        onSearchInputChanged = viewModel::onSearchInputChanged,
//        onCategoryClick = viewModel::onCategoryClick,
//    )
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//internal fun CommunityScreen_bkup1(
//    modifier: Modifier = Modifier,
//    state: CommunityViewModel.State = CommunityViewModel.State(),
//    navigateToWriting: () -> Unit = {},
//    navigateToNotification: () -> Unit = {},
//    navigateToBulletinDetail: (Long) -> Unit = {},
//    onSearchInputChanged: (String) -> Unit = {},
//    onCategoryClick: (BulletinEntity.BulletinCategory) -> Unit = {},
//) {
////    var tempTextFieldValue by remember {
////        mutableStateOf(TextFieldValue())
////    }
//
//    Scaffold(
//        modifier = modifier,
//        floatingActionButton = {
//            FloatingActionButton(onClick = navigateToWriting) {
//                Icon(
//                    imageVector = Icons.Filled.Create,
//                    contentDescription = "Writing floating action button",
//                )
//            }
//        },
//    ) { paddingValues ->
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(paddingValues)
//                .padding(horizontal = 16.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp),
//        ) {
//            item {
//                CenterAlignedTopAppBar(title = { Text("${state.currentBoard.koreanName} 게시판") })
//                // 나중에 알림버튼 추가
//            }
//            item {
//                Card(
//                    modifier = Modifier
//                        .height(40.dp)
//                        .border(
//                            width = 1.dp,
//                            color = Color(0),
//                            shape = RoundedCornerShape(12.dp)
//                        ),
//                    colors = CardColors(
//                        containerColor = Color.Green,
//                        contentColor = Color.Black,
//                        disabledContainerColor = Color.LightGray,
//                        disabledContentColor = Color.Black,
//                    )
//                ) {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceAround,
//                    ) {
//                        TextButton(
//                            onClick = {
//                                onCategoryClick(BulletinEntity.BulletinCategory.Free)
//                            },
//                            colors = if (state.currentCategory == BulletinEntity.BulletinCategory.Free) ButtonDefaults.textButtonColors(
//                                containerColor = Color.Yellow,
//                            ) else ButtonDefaults.textButtonColors(),
//                        ) {
//                            Text("자유 주제")
//                        }
//                        // TODO: 디바이더에도 여백이 붙는데...
//                        VerticalDivider(
//                            thickness = 1.dp,
//                            color = Color.Red,
//                        )
//                        TextButton(
//                            onClick = {
//                                onCategoryClick(BulletinEntity.BulletinCategory.Qna)
//                            },
//                            colors = if (state.currentCategory == BulletinEntity.BulletinCategory.Qna) ButtonDefaults.textButtonColors(
//                                containerColor = Color.Yellow,
//                            ) else ButtonDefaults.textButtonColors(),
//                        ) {
//                            Text("질문")
//                        }
//                        VerticalDivider(
//                            thickness = 1.dp,
//                            color = Color.Red,
//                        )
//                        TextButton(
//                            onClick = {
//                                onCategoryClick(BulletinEntity.BulletinCategory.Disease)
//                            },
//                            colors = if (state.currentCategory == BulletinEntity.BulletinCategory.Disease) ButtonDefaults.textButtonColors(
//                                containerColor = Color.Yellow,
//                            ) else ButtonDefaults.textButtonColors(),
//                        ) {
//                            Text("병해충")
//                        }
//                    }
//                }
//            }
//            item {
//                TextField(
//                    value = state.searchInput,
//                    onValueChange = {
//                        if ("\n" !in it) onSearchInputChanged(it)
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    placeholder = { Text("검색어를 입력하세요") },
//                    leadingIcon = {
//                        Icon(
//                            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_search_24),
//                            contentDescription = "serach icon",
//                        )
//                    },
//                )
//            }
//            if (state.bulletinEntities.isEmpty()) {
//                item {
//                    Text("게시물이 없습니다.")
//                }
//            }
//            itemsIndexed(
//                state.bulletinEntities
//            ) { idx, bulletin ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                        .clickable {
//                            navigateToBulletinDetail(bulletin.bulletinId)
//                        },
//                    colors = CardDefaults.cardColors(),
//                    elevation = CardDefaults.elevatedCardElevation(
//                        defaultElevation = 4.dp,
//                    ),  // ?
//                ) {
//                    Row {
//                        Image(
//                            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.ic_person_32),
//                            contentDescription = "프로필 사진",
//                            modifier = Modifier
//                                .background(
//                                    color = Color.Gray,
//                                    shape = CircleShape,
//                                )
//                                .padding(4.dp),
//                        )
//                        Column {
//                            Text("${bulletin.authorId}의닉네임")
//                            Text(bulletin.createdTime)
//                        }
//                        Icon(
//                            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_bookmark_24),
//                            contentDescription = "북마크 채워진 아이콘",
//                        )
//                        Icon(
//                            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_bookmark_border_24),
//                            contentDescription = "북마크 빈 아이콘",
//                        )
//                        Text("${bulletin.bookmarkedCount}")
//                    }
//                    Text(
//                        bulletin.content,
//                        modifier = Modifier.height(80.dp),
//                    )
//                    Text(
//                        "사진들 $idx",
//                        modifier = Modifier.height(240.dp),
//                    )
//                    if (bulletin.comments.isNotEmpty()) {
//                        Row {
////                        Icon(
////                            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_comment_24),
////                            contentDescription = "댓글 아이콘",
////                        )
//                            Text("댓글 ${bulletin.comments.size}개")
//                        }
//                        Row {
//                            Image(
//                                painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.ic_person_32),
//                                contentDescription = "댓글 프사",
//                                modifier = Modifier
//                                    .background(
//                                        color = Color.Gray,
//                                        shape = CircleShape,
//                                    )
//                                    .padding(4.dp),
//                            )
//                            Text("댓글닉네임$idx")
//                        }
//                        Text("댓글 내용 $idx")
//                    }
//                    // TODO: 확인용, 나중에 지울 것.
//                    else {
//                        Row {
////                        Icon(
////                            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_comment_24),
////                            contentDescription = "댓글 아이콘",
////                        )
//                            Text("댓글 ${880 + idx}개")
//                        }
//                        Row {
//                            Image(
//                                painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.ic_person_32),
//                                contentDescription = "댓글 프사",
//                                modifier = Modifier
//                                    .background(
//                                        color = Color.Gray,
//                                        shape = CircleShape,
//                                    )
//                                    .padding(4.dp),
//                            )
//                            Text("댓글닉네임$idx")
//                        }
//                        Text("댓글 내용 $idx")
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Preview(heightDp = 1200)
//@Composable
//private fun CommunityScreen_bkup1Preview() {
//    NBDreamTheme {
//        CommunityScreen_bkup1(
//            state = CommunityViewModel.State(
//                bulletinEntities = List(10) { i -> BulletinEntity.dummy(i) },
//            )
//        )
//    }
//}
