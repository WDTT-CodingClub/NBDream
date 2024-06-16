package kr.co.main.accountbook.content

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kr.co.main.accountbook.main.formatNumber
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar


@Composable
internal fun AccountBookContentScreen(
    viewModel: AccountBookContentViewModel,
    id: String
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(key1 = id) {
        viewModel.fetchAccountBookById(id)
    }

    val writingImages by remember { mutableStateOf(listOf<Uri>()) }
    var showDropDownMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            DreamCenterTopAppBar(
                title = "장부 상세",
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
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
                                // TODO 삭제하기
                                showDropDownMenu = false
                            }
                        )
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "금액",
                    style = MaterialTheme.typo.header2M
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = formatNumber(state.amount),
                        style = MaterialTheme.typo.header2M,
                        color = MaterialTheme.colors.black
                    )
                }

                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colors.grey2)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "카테고리",
                        modifier = Modifier.width(80.dp),
                        style = MaterialTheme.typo.titleM
                    )
                    Box(
                        modifier = Modifier
                            .width(75.dp)
                            .height(32.dp)
                            .background(MaterialTheme.colors.gray8)
                    ) {
                        Text(
                            text = state.category.name,
                            style = MaterialTheme.typo.bodyM,
                            color = MaterialTheme.colors.black
                        )
                    }
                }

                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colors.grey2)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "내역",
                        modifier = Modifier.width(80.dp),
                        style = MaterialTheme.typo.titleM
                    )
                    Text(
                        text = state.title,
                        color = MaterialTheme.colors.black,
                        style = MaterialTheme.typo.bodyM
                    )
                }

                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colors.grey2)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "날짜",
                        modifier = Modifier.width(80.dp),
                        style = MaterialTheme.typo.titleM
                    )
                    Text(
                        text = "${state.year}년 ${state.month}월 ${state.day}일",
                        color = MaterialTheme.colors.black,
                        style = MaterialTheme.typo.bodyM
                    )
                }

                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colors.grey2)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "사진",
                        modifier = Modifier.width(80.dp),
                        style = MaterialTheme.typo.titleM
                    )
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(writingImages.size) { index ->
                        val imageUri = writingImages[index]
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center,
                        ) {
                            AsyncImage(
                                model = imageUri,
                                contentDescription = "image",
                                contentScale = ContentScale.Crop,
                            )
                        }
                    }
                }
            }
        }
    )
}