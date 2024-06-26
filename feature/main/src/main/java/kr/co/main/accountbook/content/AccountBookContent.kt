package kr.co.main.accountbook.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kr.co.main.accountbook.model.formatNumber
import kr.co.main.accountbook.model.getDisplay
import kr.co.main.accountbook.model.getTransactionType
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Arrowleft
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.Shapes
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar
import kr.co.ui.widget.LoadingShimmerEffect
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
internal fun AccountBookContentRoute(
    viewModel: AccountBookContentViewModel = hiltViewModel(),
    navigationToUpdate: (Long?) -> Unit,
    navigationTopAccountBook: () -> Unit,
    popBackStack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.complete.collect {
            navigationTopAccountBook()
        }
    }
    AccountBookContentScreen(
        state = state,
        isLoading = isLoading,
        navigationToUpdate = navigationToUpdate,
        popBackStack = popBackStack,
        onRemoveItem = viewModel::deleteAccountBookById
    )
}

@Composable
internal fun AccountBookContentScreen(
    state: AccountBookContentViewModel.State = AccountBookContentViewModel.State(),
    isLoading: Boolean,
    navigationToUpdate: (Long?) -> Unit,
    popBackStack: () -> Unit,
    onRemoveItem: () -> Unit = {}
) {
    var showDropDownMenu by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Surface(
        color = MaterialTheme.colors.white
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Paddings.xlarge)
        ) {
            item {
                DreamCenterTopAppBar(
                    title = "장부 상세",
                    navigationIcon = {
                        IconButton(onClick = popBackStack) {
                            Icon(
                                imageVector = DreamIcon.Arrowleft,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { showDropDownMenu = true }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "설정",
                                tint = Color.Black
                            )
                        }
                        DropdownMenu(
                            expanded = showDropDownMenu,
                            onDismissRequest = { showDropDownMenu = false },
                            modifier = Modifier.background(MaterialTheme.colors.white)
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Row {
                                        Text("수정하기")
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                },
                                onClick = {
                                    navigationToUpdate(state.id)
                                    showDropDownMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Row {
                                        Text("삭제하기")
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                },
                                onClick = {
                                    showDeleteDialog = true
                                    showDropDownMenu = false
                                }
                            )
                        }
                    }
                )
            }

            if (isLoading) {
                items(5) {
                    LoadingShimmerEffect {
                        ShimmerGridItem(brush = it)
                    }
                }
            } else {
                item {
                    Column(
                        modifier = Modifier.padding(top = Paddings.xextra)
                    ) {
                        Text(
                            text = state.transactionType.getTransactionType(),
                            style = MaterialTheme.typo.h4,
                            color = MaterialTheme.colors.gray1
                        )
                        Spacer(modifier = Modifier.height(Paddings.large))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "${formatNumber(state.amount)}원",
                                style = MaterialTheme.typo.h2,
                                color = MaterialTheme.colors.gray1
                            )
                        }

                    }
                }

                item {
                    Row(
                        modifier = Modifier
                            .padding(top = Paddings.xextra)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "카테고리",
                            style = MaterialTheme.typo.h4
                        )
                        Spacer(modifier = Modifier.width(Paddings.xxlarge))
                        Box(
                            modifier = Modifier
                                .height(32.dp)
                                .border(
                                    1.dp,
                                    MaterialTheme.colors.gray7,
                                    shape = Shapes.medium
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = state.category.getDisplay(),
                                style = MaterialTheme.typo.body1,
                                color = MaterialTheme.colors.gray2,
                                modifier = Modifier.padding(horizontal = Paddings.large)
                            )
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier
                            .padding(top = Paddings.xextra)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "내역",
                            style = MaterialTheme.typo.h4
                        )
                        Spacer(modifier = Modifier.width(Paddings.xxlarge))
                        Text(
                            text = state.title ?: "",
                            style = MaterialTheme.typo.body1,
                            color = MaterialTheme.colors.gray2
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier
                            .padding(top = Paddings.xextra)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "날짜",
                            style = MaterialTheme.typo.h4
                        )
                        Spacer(modifier = Modifier.width(Paddings.xxlarge))
                        Text(
                            text = state.registerDateTime?.let { formatDate(it) } ?: "",
                            style = MaterialTheme.typo.body1,
                            color = MaterialTheme.colors.gray2
                        )
                    }
                }

                item {
                    Column(
                        modifier = Modifier.padding(top = Paddings.xextra)
                    ) {
                        Text(
                            text = "사진",
                            style = MaterialTheme.typo.h4
                        )
                        Spacer(modifier = Modifier.height(Paddings.large))
                        state.imageUrls.forEachIndexed { index, value ->
                            Timber.d("imageUrls: ${state.imageUrls}")
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = Paddings.medium)
                                    .clip(Shapes.medium)
                                    .background(Color.LightGray),
                                contentAlignment = Alignment.Center,
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(Shapes.medium),
                                    model = value,
                                    contentDescription = "image",
                                    contentScale = ContentScale.Crop,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    text = "삭제 확인",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typo.h4
                )
            },
            text = {
                Text(
                    text = "정말 삭제하시겠습니까?",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typo.body1
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onRemoveItem()
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colors.black
                    ),
                    modifier = Modifier.widthIn(min = 72.dp)
                ) {
                    Text("네")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDeleteDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colors.black
                    ),
                    modifier = Modifier.widthIn(min = 72.dp)
                ) {
                    Text("아니오")
                }
            }
        )
    }

}

internal fun formatDate(dateString: String): String {
    return try {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val targetFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
        Timber.d("$originalFormat")
        Timber.d("$targetFormat")
        val date: Date? = originalFormat.parse(dateString)
        if (date != null) {
            targetFormat.format(date)
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}

@Composable
internal fun ShimmerGridItem(brush: Brush) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Paddings.xextra),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .height(32.dp)
                .weight(1f)
                .background(brush)
        )
        Spacer(modifier = Modifier.width(Paddings.xxlarge))
        Spacer(
            modifier = Modifier
                .height(32.dp)
                .weight(2f)
                .background(brush)
        )
    }
}
