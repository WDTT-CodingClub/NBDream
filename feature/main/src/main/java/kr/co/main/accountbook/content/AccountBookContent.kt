package kr.co.main.accountbook.content

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kr.co.main.accountbook.main.formatNumber
import kr.co.main.accountbook.model.getDisplay
import kr.co.main.accountbook.model.getTransactionType
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.Shapes
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
internal fun AccountBookContentRoute(
    popBackStack: () -> Unit,
    viewModel: AccountBookContentViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

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
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                            onDismissRequest = { showDropDownMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Row {
                                        Text("수정하기")
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Icon(
                                            imageVector = Icons.Filled.Edit,
                                            contentDescription = null,
                                            tint = Color.Black
                                        )
                                    }
                                },
                                onClick = {
                                    // TODO 수정하기
                                    showDropDownMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Row {
                                        Text("삭제하기")
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = null,
                                            tint = Color.Black
                                        )
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
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = Paddings.xlarge)
                    ) {
                        repeat(5) {
                            LoadingShimmerEffect()
                        }
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
            title = { Text("장부 내역 삭제") },
            text = { Text("장부 내역을 삭제하겠습니까?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteAccountBookById()
                        showDeleteDialog = false
                    }
                ) {
                    Text("확인")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = false }) {
                    Text("취소")
                }
            }
        )
    }
}

fun formatDate(dateString: String): String {
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
fun LoadingShimmerEffect() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    ShimmerGridItem(brush = brush)
}

@Composable
fun ShimmerGridItem(brush: Brush) {
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
