package kr.co.main.ui

import Bookmarkon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kr.co.common.util.format
import kr.co.core.ui.icon.buttericon.Bookmarkoff
import kr.co.domain.entity.BulletinEntity
import kr.co.domain.entity.CommentEntity
import kr.co.ui.ext.noRippleClickable
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Tobot
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@Composable
internal fun DreamMainPostCard(
    modifier: Modifier = Modifier,
    bulletin: BulletinEntity = BulletinEntity.empty(),
    onPostClick: (Long) -> Unit = {},
    onBookMarkClick: (Long) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onPostClick(bulletin.bulletinId) }
            .background(
                color = MaterialTheme.colors.white,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(
                horizontal = 24.dp,
                vertical = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(54.dp)
                    .clip(shape = CircleShape),
                model = bulletin.profileImageUrl,
                contentDescription = "작성자 프로필 이미지",
                contentScale = ContentScale.Crop,
                placeholder = rememberVectorPainter(image = DreamIcon.Tobot),
                error = rememberVectorPainter(image = DreamIcon.Tobot)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = bulletin.nickname,
                    style = MaterialTheme.typo.body1,
                    color = MaterialTheme.colors.gray1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = bulletin.createdTime.format("yyyy/MM/dd"),
                    style = MaterialTheme.typo.body2,
                    color = MaterialTheme.colors.gray5
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                modifier = Modifier
                    .noRippleClickable { onBookMarkClick(bulletin.bulletinId) }
                    .size(20.dp)
                    .padding(end = 4.dp),
                imageVector = if (bulletin.bookmarked) DreamIcon.Bookmarkon else DreamIcon.Bookmarkoff,
                contentDescription = "북마크 아이콘",
                tint = MaterialTheme.colors.gray5
            )

            Text(
                text = bulletin.bookmarkedCount.toString(),
                style = MaterialTheme.typo.body2,
                color = MaterialTheme.colors.gray5
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = bulletin.content,
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray1,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        ImageGrid(bulletin.imageUrls)

        Text(
            text = "댓글 ${bulletin.comments.size}개",
            fontFamily = MaterialTheme.typo.body1.fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 26.sp,
            color = MaterialTheme.colors.gray5
        )

        bulletin.comments.firstOrNull()?.let {
            CommentRow(
                it
            )
        }
    }
}

@Composable
private fun CommentRow(
    comment: CommentEntity
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(40.dp)
                .clip(shape = CircleShape),
            model = comment.profileImageUrl,
            contentDescription = "${comment.nickname}의 프로필 이미지"
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = comment.nickname,
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray1
            )

            Text(
                text = comment.content,
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun ImageGrid(imageUrls: List<String>) {
    when (imageUrls.size) {
        0 -> {}
        1 -> {
            AsyncImage(
                model = imageUrls[0],
                contentDescription = "게시글 이미지",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(294 / 218f),
                contentScale = ContentScale.Crop
            )
        }

        2 -> {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                imageUrls.forEach { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = "게시글 이미지",
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(147 / 218f),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        3 -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(294 / 218f),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = imageUrls[0],
                    contentDescription = "게시글 이미지",
                    modifier = Modifier
                        .fillMaxWidth(189 / 294f),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    imageUrls.drop(1).forEach { url ->
                        AsyncImage(
                            model = url,
                            contentDescription = "게시글 이미지",
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        else -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(294 / 218f),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = imageUrls[0],
                    contentDescription = "게시글 이미지",
                    modifier = Modifier
                        .fillMaxWidth(189 / 294f),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AsyncImage(
                        model = imageUrls[1],
                        contentDescription = "게시글 이미지",
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(97 / 105f),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(97 / 105f)
                    ) {
                        AsyncImage(
                            model = imageUrls[2],
                            contentDescription = "게시글 이미지",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(MaterialTheme.colors.black.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+${imageUrls.size - 3}",
                                style = MaterialTheme.typo.h4,
                                color = MaterialTheme.colors.white
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        DreamMainPostCard(bulletin = BulletinEntity.dummy(4))
    }
}