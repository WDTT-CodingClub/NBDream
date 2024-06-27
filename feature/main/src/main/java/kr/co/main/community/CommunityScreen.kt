package kr.co.main.community

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.domain.entity.BulletinEntity
import kr.co.domain.entity.type.CropType
import kr.co.main.ui.DreamMainPostCard
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamBottomSheetWithTextButtons
import kr.co.ui.widget.DreamTopAppBar
import kr.co.ui.widget.TextAndOnClick

@Composable
internal fun CommunityRoute(
    navigateToWriting: (CropType, BulletinEntity.BulletinCategory) -> Unit,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CommunityScreen(
    modifier: Modifier = Modifier,
    state: CommunityViewModel.State = CommunityViewModel.State(),
    event: CommunityScreenEvent = CommunityScreenEvent.dummy,
    navigateToWriting: (CropType, BulletinEntity.BulletinCategory) -> Unit = { _, _ -> },
    navigateToNotification: () -> Unit = {},
    navigateToBulletinDetail: (Long) -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            DreamTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "${state.currentBoard.koreanName} 게시판",
                            style = MaterialTheme.typo.h2
                        )
                        IconButton(onClick = {
                            event.showBottomSheet(
                                CropType.entries.map {
                                    TextAndOnClick(
                                        text = it.koreanName,
                                        onClick = { event.onSelectBoard(it) }
                                    )
                                }
                            )
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "게시판 선택 ArrowDropDown",
                                modifier = Modifier
                                    .width(32.dp)
                                    .height(32.dp),
                            )
                        }
                    }
                },
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .heightIn(min = 48.dp)
                        .clickable(onClick = {
                            navigateToWriting(
                                state.currentBoard,
                                state.currentCategory,
                            )
                        }),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "게시판 탑바 Add 아이콘")
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
                val keyboardController = LocalSoftwareKeyboardController.current
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
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        event.onSearchRun()
                        keyboardController?.hide()
                    }),
                )
            }
            if (state.bulletinEntities.isEmpty()) {
                item {
                    Text("게시물이 없습니다.")
                }
            }
            items(
                state.bulletinEntities
            ) { bulletin ->
                DreamMainPostCard(
                    bulletin = bulletin,
                    onPostClick = { navigateToBulletinDetail(bulletin.bulletinId) },
                    onBookMarkClick = { event.bookmarkBulletin(bulletin.bulletinId) },
                )
            }
            item { /* 최하단 여백용 */ }
        }
    }

    if (state.isShowBottomSheet) {
        DreamBottomSheetWithTextButtons(
            onDismissRequest = { event.setIsShowBottomSheet(false) },
            textAndOnClicks = state.bottomSheetItems,
        )
    }

    if (!state.isEnable) CommunityDisableScreen()

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
private fun CommunityScreenPreview() {
    NBDreamTheme {
        CommunityScreen(
            state = CommunityViewModel.State(
                bulletinEntities = List(10) { i -> BulletinEntity.dummy(i) },
            )
        )
    }
}
