@file:OptIn(ExperimentalMaterial3Api::class)

package kr.co.main.my.community.bookmark

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.main.R
import kr.co.main.ui.DreamMainPostCard
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.widget.DreamCenterTopAppBar

@Composable
internal fun MyPageBookmarkRoute(
    viewModel: MyPageBookmarkViewModel = hiltViewModel(),
    popBackStack : () -> Unit = {},
    navigateToBulletinDetail: (Long) -> Unit = {}
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    MyPageBookmarkScreen(
        state = state,
        popBackStack = popBackStack,
        navigateToBulletinDetail = navigateToBulletinDetail
    )
}

@Composable
private fun MyPageBookmarkScreen(
    state: MyPageBookmarkViewModel.State = MyPageBookmarkViewModel.State(),
    popBackStack : () -> Unit = {},
    navigateToBulletinDetail: (Long) -> Unit = {}
) {
    Scaffold(
        containerColor = MaterialTheme.colors.white,
        topBar = {
            DreamCenterTopAppBar(
                title = "저장한 글 보기",
                colorBackground = true,
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
            items(state.bulletin) {
                DreamMainPostCard(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colors.gray5
                        ),
                    bulletin = it,
                    onPostClick = navigateToBulletinDetail
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        MyPageBookmarkScreen()
    }
}

@Preview
@Composable
private fun PreviewCard() {
    NBDreamTheme {
        DreamMainPostCard()
    }
}