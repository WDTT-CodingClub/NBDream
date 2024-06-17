package kr.co.main.my

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Defaultprofile
import kr.co.ui.icon.dreamicon.OutlineEdit
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamTopAppBar

@Composable
internal fun MyPageRoute(
    navigateToProfileEdit: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    MyPageScreen(
        navigateToProfileEdit = navigateToProfileEdit,
    )
}

@Composable
private fun MyPageScreen(
    navigateToProfileEdit: () -> Unit = {},
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
                    IconButton(onClick = navigateToProfileEdit) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = DreamIcon.OutlineEdit,
                            contentDescription = "edit"
                        )
                    }
                }
            }

            item {
                ProfileCard()
            }

            item {
                Spacer(modifier = Modifier.height(48.dp))
                BulletinCard()
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
                CommunityCard()
            }
        }
    }
}

@Composable
private fun CommunityCard() {
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
                "작성한 글 보러가기",
                "작성한 댓글 보러가기"
            ).forEachIndexed { index, text ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
                        contentDescription = "navigate to bookmark page",
                        tint = MaterialTheme.colors.gray5,
                    )
                }
                if (index < 2)
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colors.gray8
                )
            }
        }

    }
}

@Composable
private fun BulletinCard() {
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
            text = "재배 작물",
            style = MaterialTheme.typo.h4,
            color = MaterialTheme.colors.gray1,
        )

        Column(
            modifier = Modifier
                .padding(
                    top = 32.dp,
                    bottom = 48.dp
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "작물 추가하기",
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray1
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colors.gray8
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "전체 보기",
                style = MaterialTheme.typo.label,
                color = MaterialTheme.colors.gray3
            )

            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "extend bulletin list",
                tint = MaterialTheme.colors.gray5
            )
        }
    }
}

@Composable
private fun ProfileCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(88 / 375f)
                .aspectRatio(1f),
            model = "",
            error = rememberVectorPainter(image = DreamIcon.Defaultprofile),
            contentDescription = "User's profile image",
            placeholder = rememberVectorPainter(image = DreamIcon.Defaultprofile)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "충북음성 홍길동",
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray1
            )

            Row {
                Text(
                    text = "honggildong@kakao.com",
                    style = MaterialTheme.typo.body2,
                    color = MaterialTheme.colors.gray3
                )
            }

            Text(
                text = "내 농장 주소지",
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