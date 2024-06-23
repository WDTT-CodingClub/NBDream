package kr.co.main.home.chat

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.domain.entity.CropEntity
import kr.co.ui.ext.noRippleClickable
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar

@Composable
internal fun ChatRoute(
    viewModel: ChatViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ChatScreen(
        state = state,
        popBackStack = popBackStack,
        onSelectedChat = viewModel::onSelectedChat,
        onSelectedCrop = viewModel::onSelectedCrop
    )
}

@Composable
private fun ChatScreen(
    state: ChatViewModel.State = ChatViewModel.State(),
    popBackStack: () -> Unit = {},
    onSelectedChat: (Int) -> Unit = {},
    onSelectedCrop: (String) -> Unit ={}
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
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .scaffoldBackground(
                    scaffoldPadding = scaffoldPadding,
                ),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            itemsIndexed(state.chats) { index, (isAi, text) ->
                if (isAi) {
                    AiTalk(
                        text = text,
                        isProfileVisible = if (index >= 1) state.chats[index - 1].first.not() else true,
                    )
                } else {
                    UserTalk(
                        text = text
                    )
                }
            }

            item {
                if (state.isAiLoading) {
                    AiTalk(text = "토지를 분석중 입니다")
                }
            }

            item {
                if (state.isChatVisible) {
                    SelectedTab(
                        type = state.chatType,
                        onSelectedChat = onSelectedChat,
                        onSelectedCrop = onSelectedCrop
                    )
                }
            }
        }
    }
}

@Composable
private fun AiTalk(
    text: String,
    isProfileVisible: Boolean = true,
) {
    Column(
        modifier = Modifier
            .padding(end = 12.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (isProfileVisible){
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
        }
        Box(
            modifier = Modifier
                .padding(start = 24.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ) {
            BalloonTail(
                modifier = Modifier
                    .padding(start = 7.5.dp)
                    .size(12.dp)
                    .padding(
                        top = 8.5.dp,
                    )
                    .aspectRatio((7.5 / 12.93).toFloat())
            )
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 12.dp,
                            bottomStart = 12.dp,
                            bottomEnd = 12.dp
                        )
                    )
                    .padding(24.dp)
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typo.body1,
                    color = MaterialTheme.colors.white
                )
            }
        }
    }
}

@Composable
private fun UserTalk(
    text: String = "",
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Spacer(
            modifier = Modifier
                .defaultMinSize(10.dp)
                .weight(1f)
        )
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.white,
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.gray9,
                    shape = CircleShape
                )
                .padding(
                    vertical = 12.dp,
                    horizontal = 24.dp
                ),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray1
            )
        }
    }
}

@Composable
private fun SelectedTab(
    type: Int = 0,
    onSelectedChat: (Int) -> Unit = {},
    onSelectedCrop: (String) -> Unit ={}
) {
    when(type) {
        0 -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                listOf("1. 내 토지는 작물 재배에 적합 할까?", "2. 작물별로 어떤 토양이 적합한지 궁금해!").forEachIndexed { index, s ->
                    DefaultSelector(
                        text = s,
                    ) {
                        onSelectedChat(index + 1)
                    }
                }
            }
        }
        1 -> {
            CropSelector{
                onSelectedCrop(it.koreanName)
            }
        }
    }
}

@Composable
private fun DefaultSelector(
    text: String,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Spacer(
            modifier = Modifier
                .defaultMinSize(10.dp)
                .weight(1f)
        )
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.white,
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.gray9,
                    shape = CircleShape
                )
                .padding(
                    vertical = 12.dp,
                    horizontal = 24.dp
                ),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray1
            )
        }
    }
}

@Composable
private fun BalloonTail(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
) {
    Canvas(modifier = modifier) {
        val path = Path().apply {
            moveTo(0f, 0f)
            cubicTo(
                size.width * 3 / 5f, size.height * 1 / 13f,
                size.width * 4 / 5f, size.height * 2 / 13f,
                size.width, size.height * 3 / 10f
            )
            lineTo(size.width, size.height)
            lineTo(size.width * 3 / 7.5f, size.height)
            cubicTo(
                size.width * 3 / 7.5f, size.height * 10 / 13f,
                size.width * 2.5f / 7.5f, size.height * 5 / 13f,
                0f, 0f
            )
            close()
        }
        drawPath(path = path, color = color)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CropSelector(
    onClick: (CropEntity.Name) -> Unit = {},
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CropEntity.Name.entries.forEach {
            Text(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.white,
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.gray9,
                        shape = CircleShape
                    )
                    .padding(
                        vertical = 12.dp,
                        horizontal = 24.dp
                    )
                    .clickable(onClick = { onClick(it) }),
                text = it.koreanName,
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray1
            )
            Spacer(modifier = Modifier.width(8.dp))
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
