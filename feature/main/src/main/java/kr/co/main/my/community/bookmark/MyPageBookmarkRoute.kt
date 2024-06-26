package kr.co.main.my.community.bookmark

import Bookmarkon
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kr.co.main.R
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Tobot
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar

@Composable
internal fun MyPageBookmarkRoute(
    viewModel: MyPageBookmarkViewModel = hiltViewModel(),
    popBackStack : () -> Unit = {}
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    MyPageBookmarkScreen(
        state = state,
        popBackStack = popBackStack
    )
}

@Composable
private fun MyPageBookmarkScreen(
    state: MyPageBookmarkViewModel.State = MyPageBookmarkViewModel.State(),
    popBackStack : () -> Unit = {}
) {
    Scaffold(
        containerColor = MaterialTheme.colors.background,
        topBar = {
            DreamCenterTopAppBar(
                modifier = Modifier.background(MaterialTheme.colors.background),
                title = "저장한 글 보기",
                navigationIcon = {
                    IconButton(
                        onClick = popBackStack
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = stringResource(id = R.string.feature_main_pop_back_stack)
                        )
                    }
                }
            )
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .scaffoldBackground(
                    scaffoldPadding = scaffoldPadding,
                    padding = PaddingValues(horizontal = 24.dp)
                )
                .padding(top = 52.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

        }
    }
}

@Composable
private fun PostCard(

) {
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
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(54.dp),
                model = "",
                contentDescription = "작성자 프로필 이미지",
                contentScale = ContentScale.Crop,
                placeholder = rememberVectorPainter(image = DreamIcon.Tobot),
                error = rememberVectorPainter(image = DreamIcon.Tobot)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "불타는 감자",
                    style = MaterialTheme.typo.body1,
                    color = MaterialTheme.colors.gray1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = "2024/05/08 23:11:01",
                    style = MaterialTheme.typo.body2,
                    color = MaterialTheme.colors.gray5
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 4.dp),
                imageVector = DreamIcon.Bookmarkon,
                contentDescription = "북마크 아이콘",
                tint = MaterialTheme.colors.gray5
            )

            Text(
                text = "50",
                style = MaterialTheme.typo.body2,
                color = MaterialTheme.colors.gray5
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "이건 내가 심은 감자이고 나는 말하는 감자이다..이건 내가 심은 감자이고 나는 말하는 감자이다..이건 내가 심은 감자이고 나는 말하는 감자이다..이건 내가 심은 감자이고 나는 말하는 감자이다..이건 내가 심은 감자이고 나는 말하는 감자이다..",
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray1,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(12.dp))

    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        PostCard()
    }
}