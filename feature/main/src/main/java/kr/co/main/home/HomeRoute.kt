package kr.co.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Bell
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    state: HomeViewModel.State = HomeViewModel.State()
) {
    Scaffold(
        containerColor = MaterialTheme.colors.gray9,
        topBar = {}
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .scaffoldBackground(scaffoldPadding),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "내 농장",
                            style = MaterialTheme.typo.h2
                        )

                        Icon(
                            imageVector = DreamIcon.Bell,
                            contentDescription = "notification"
                        )
                    }
                    Text(
                        text = "산 좋고 물 좋 나만의 농장 1번지",
                        style = MaterialTheme.typo.body1,
                        color = MaterialTheme.colors.gray2
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(
                            horizontal = 24.dp,
                            vertical = 18.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "내 토지 상태 알아보기",
                        style = MaterialTheme.typo.button,
                        color = MaterialTheme.colors.gray10,
                    )
                    Text(
                        text = "챗봇 연결 >",
                        style = MaterialTheme.typo.body2,
                        color = MaterialTheme.colors.gray10
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colors.white,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(
                            horizontal = 51.dp
                        )
                        .padding(
                            top = 40.dp,
                            bottom = 24.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(48.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth(104 / 346f)
                                    .aspectRatio(104 / 69.33f),
                                painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_sunny),
                                contentDescription = "weather state"
                            )

                            Column(
                                modifier = Modifier
                                    .padding(start = 24.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "24°",
                                    style = MaterialTheme.typo.weather,
                                    color = MaterialTheme.colors.gray1
                                )
                                Row(
                                    modifier = Modifier,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "23°",
                                        style = MaterialTheme.typo.body2,
                                        color = MaterialTheme.colors.red
                                    )
                                    Icon(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .rotate(270f),
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = "up and down temp",
                                        tint = MaterialTheme.colors.red
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))

                                    Text(
                                        text = "23°",
                                        style = MaterialTheme.typo.body2,
                                        color = MaterialTheme.colors.gray4
                                    )
                                    Icon(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .rotate(90f),
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = "up and down temp",
                                        tint = MaterialTheme.colors.gray4
                                    )
                                }
                            }
                        }
                        Row(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            listOf("강수확률", "강수량", "습도", "풍속").forEachIndexed { index, value ->
                                WeatherState(
                                    title = value
                                )

                                if (index < 3) VerticalDivider(
                                    modifier = Modifier.padding(vertical = 7.dp),
                                    thickness = 1.dp,
                                    color = MaterialTheme.colors.gray8
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "펼쳐보기",
                            style = MaterialTheme.typo.label,
                            color = MaterialTheme.colors.gray3
                        )

                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = "expand",
                            tint = MaterialTheme.colors.gray5
                        )
                    }
                }
            }
        }
    }

}

@Composable
private fun WeatherState(
    title: String
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typo.body2,
            color = MaterialTheme.colors.gray4
        )
        Text(
            text = "0%",
            style = MaterialTheme.typo.body2,
            color = MaterialTheme.colors.gray4
        )
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        HomeScreen()
    }
}