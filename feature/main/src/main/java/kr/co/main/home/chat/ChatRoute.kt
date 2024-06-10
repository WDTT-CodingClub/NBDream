package kr.co.main.home.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.ui.ext.noRippleClickable
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar

@Composable
internal fun ChatRoute(
    viewModel: ChatViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ChatScreen(
        state = state,
        popBackStack = popBackStack
    )
}

@Composable
private fun ChatScreen(
    state: ChatViewModel.State = ChatViewModel.State(),
    popBackStack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            DreamCenterTopAppBar(
                title = "내 토지 상태 알아보기",
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .size(32.dp)
                            .noRippleClickable(onClick = popBackStack),
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "back",
                        tint = MaterialTheme.colors.gray1
                    )
                }
            )
        },
        bottomBar = {

        }
    ) { scaffolPadding ->
        LazyColumn(
            modifier = Modifier
                .scaffoldBackground(
                    scaffoldPadding = scaffolPadding,
                ),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                ChatTalk()
            }
        }
    }
}

@Composable
private fun ChatTalk(

) {
    Column(
        modifier = Modifier
            .padding(end = 18.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(9.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp),
                imageVector = Icons.Filled.Person,
                contentDescription = "tobot",
                tint = Color.Unspecified
            )
            Text(
                text = "토봇",
                style = MaterialTheme.typo.name,
                color = MaterialTheme.colors.gray4
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.gray9,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(24.dp)
        ) {
            Text(
                text = "안녕하세요 유수님의 토지친구 토봇입니다^^ \n 궁금한 내용을 숫자로 알려주세요!"
                        + "\n \n 1. 내 토지는 작물 재배에 적합할까? " +
                        "\n 2. 작물별로 어떤 토양이 적합한지 궁금해!",
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray1
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        ChatScreen()
    }
}
