package kr.co.main.calendar.common

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.Shapes
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@Composable
internal fun CalendarlImagePicker(
    imagePickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>,
    images: List<String>,
    onDeleteImage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    color = MaterialTheme.colors.gray9,
                    shape = Shapes.medium
                ),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    imagePickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
                modifier = Modifier.fillMaxSize(),
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                contentPadding = PaddingValues(0.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.outline_photo_camera_24),
                        contentDescription = null,
                        tint = MaterialTheme.colors.gray5,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "사진 올리기",
                        color = MaterialTheme.colors.gray4,
                        style = MaterialTheme.typo.body1,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items = images, key = { it }) { imageUrl ->
                Box(
                    modifier = Modifier
                        .padding(start = Paddings.large)
                        .size(120.dp)
                        .clip(Shapes.small)
                        .background(MaterialTheme.colors.gray10),
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(Shapes.small),
                        model = imageUrl,
                        contentDescription = "image",
                        contentScale = ContentScale.Crop,
                    )
                    IconButton(
                        onClick = {
                            onDeleteImage(imageUrl)
                        },
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.TopEnd),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colors.gray1
                        ),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = null,
                            tint = MaterialTheme.colors.white,
                        )
                    }
                }
            }
        }

    }
}
