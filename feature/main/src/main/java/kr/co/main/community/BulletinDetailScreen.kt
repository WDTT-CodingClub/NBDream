package kr.co.main.community

import android.icu.text.DecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
internal fun BulletinDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: CommunityViewModel = hiltViewModel(),
) {
    Column {
        LazyColumn(
            modifier = Modifier.weight(1f),
        ) {
            item {
                Row {
                    Image(
                        painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_keyboard_arrow_left_24),
                        contentDescription = "뒤로가기 아이콘",
                    )
                    Text("무슨무슨 게시판")
                    Image(
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
                            Image(
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
                    Image(
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
                value = viewModel.commentWritingInput.collectAsState().value,
                onValueChange = viewModel::onCommentWritingInput
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "보내기 아이콘")
            }
        }
    }

}

@Preview(heightDp = 1200)
@Composable
private fun BulletinDetailScreenPreview() {
    MaterialTheme {
        Surface {
            BulletinDetailScreen()
        }
    }
}
