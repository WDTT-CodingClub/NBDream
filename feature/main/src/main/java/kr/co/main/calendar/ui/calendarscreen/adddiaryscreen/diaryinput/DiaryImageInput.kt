package kr.co.main.calendar.ui.calendarscreen.adddiaryscreen.diaryinput

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kr.co.main.R
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Delete
import kr.co.ui.icon.dreamicon.Edit
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

private const val ADD_IMAGE_BUTTON_SIZE = 66
private const val IMAGE_SIZE = 66
private const val DELETE_IMAGE_BUTTON_OFFSET = 2
private const val DELETE_IMAGE_BUTTON_SIZE = 16

@Composable
internal fun DiaryImageInput(
    images: List<String>,
    onAddImage: (String) -> Unit,
    onDeleteImage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Paddings.medium),
        verticalAlignment = Alignment.Bottom
    ) {
        AddImageButton(
            onAddImage = onAddImage
        )
        ImageList(
            images = images,
            onDeleteImage = onDeleteImage
        )
    }
}

@Composable
private fun AddImageButton(
    onAddImage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(ADD_IMAGE_BUTTON_SIZE.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.LightGray)
            .clickable {
                // TODO 갤러리에서 이미지 선택
                // onAddImage()
            }
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Icon(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                imageVector = DreamIcon.Edit, //TODO 카메라 아이콘으로 변경
                contentDescription = "",
                tint = MaterialTheme.colors.text1
            )
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.feature_main_calendar_add_diary_input_image),
                style = MaterialTheme.typo.bodyM,
                color = MaterialTheme.colors.text1
            )
        }
    }
}

@Composable
private fun ImageList(
    images: List<String>,
    onDeleteImage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        items(images, key = { it }) {
            ImageItem(
                imageUrl = it,
                onDeleteImage = onDeleteImage
            )
        }
    }
}

@Composable
private fun ImageItem(
    imageUrl: String,
    onDeleteImage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size((IMAGE_SIZE + DELETE_IMAGE_BUTTON_OFFSET).dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .align(Alignment.Center)
                .size(IMAGE_SIZE.dp)
                .offset(
                    x = (-DELETE_IMAGE_BUTTON_OFFSET).dp,
                    y = (DELETE_IMAGE_BUTTON_OFFSET).dp
                ),
            model = imageUrl,
            contentDescription = "",
            clipToBounds = true
        )
        ImageItemDeleteButton(
            modifier = Modifier
                .align(Alignment.TopEnd),
            onClick = { onDeleteImage(imageUrl) }
        )
    }
}

@Composable
private fun ImageItemDeleteButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .clip(shape = CircleShape)
        .background(Color.LightGray)
        .clickable { onClick() }
    ) {
        Icon(
            modifier = Modifier
                .size(DELETE_IMAGE_BUTTON_SIZE.dp)
                .padding(Paddings.xsmall)
                .align(Alignment.Center),
            imageVector = DreamIcon.Delete, // TODO X 아이콘으로 변경
            contentDescription = ""
        )
    }
}

@Preview
@Composable
private fun DiaryImageInputPreview() {
    DiaryImageInput(
        images = listOf(
            "https://src.hidoc.co.kr/image/lib/2021/6/11/1623368825118_0.jpg"
        ),
        onAddImage = { _ -> },
        onDeleteImage = { _ -> }
    )
}