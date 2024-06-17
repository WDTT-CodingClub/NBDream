package kr.co.main.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CommunityScreen(
    viewModel: CommunityViewModel = hiltViewModel(),
    onWritingClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onBulletinClick: (String) -> Unit,
) {
    var tempTextFieldValue by remember {
        mutableStateOf(TextFieldValue())
    }
    val bulletinEntities = viewModel.bulletinEntities.collectAsState().value

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onWritingClick) {
                Icon(
                    imageVector = Icons.Filled.Create,
                    contentDescription = "Writing floating action button",
                )
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                CenterAlignedTopAppBar(title = { Text("감자 게시판") })
                // 나중에 알림버튼 추가
            }
            item {
                Card(
                    modifier = Modifier
                        .height(40.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    colors = CardColors(
                        containerColor = Color.Green,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.LightGray,
                        disabledContentColor = Color.Black,
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        Text("자유 주제")
                        // TODO: 디바이더에도 여백이 붙는데...
                        VerticalDivider(
                            modifier = Modifier
                                .width(1.dp),
                            color = Color.Red,
                        )
                        Text("질문")
                        VerticalDivider(
                            modifier = Modifier
                                .width(1.dp),
                            color = Color.Red,
                        )
                        Text("병해충")
                    }
                }
            }
            item {
                TextField(
                    value = viewModel.searchInput.collectAsState().value,
                    onValueChange = {
                        if ("\n" !in it) viewModel.onSearchInputChanged(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("검색어를 입력하세요") },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_search_24),
                            contentDescription = "serach icon",
                        )
                    },
                )
            }
            items(
                Array(10) { it }
            ) {
                val bulletin = bulletinEntities[it]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onBulletinClick("") },
                    colors = CardDefaults.cardColors(),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 4.dp,
                    ),  // ?
                ) {
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
                            Text("${bulletin.userId}의닉네임")
                            Text(bulletin.createdTime)
                        }
                        Icon(
                            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_bookmark_24),
                            contentDescription = "북마크 채워진 아이콘",
                        )
                        Icon(
                            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_bookmark_border_24),
                            contentDescription = "북마크 빈 아이콘",
                        )
                        Text("${bulletin.bookmarkedUsers.size}")
                    }
                    Text(
                        bulletin.content,
                        modifier = Modifier.height(80.dp),
                    )
                    Text(
                        "사진들 $it",
                        modifier = Modifier.height(240.dp),
                    )
                    Row {
                        Icon(
                            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_comment_24),
                            contentDescription = "댓글 아이콘",
                        )
                        Text("${bulletin.comments.size}")
                    }
                    if (bulletin.comments.isNotEmpty()) {
                        // 여기에 넣어야하는데 일단 표시용으로 밖에.
                    }
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
                    }
                    Text("댓글 내용 $it")
                }
            }
        }
    }
}

//@Preview(heightDp = 1200)
//@Composable
//private fun CommunityScreenPreview() {
//    MaterialTheme {
//        Surface {
//            CommunityScreen(
//                onWritingClick = {},
//                onNotificationClick = {},
//                onBulletinClick = {},
//            )
//        }
//    }
//}
