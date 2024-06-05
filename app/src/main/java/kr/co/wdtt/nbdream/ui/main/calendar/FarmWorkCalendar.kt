package kr.co.wdtt.nbdream.ui.main.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import kr.co.wdtt.nbdream.R
import kr.co.wdtt.nbdream.domain.entity.FarmWorkCategory
import kr.co.wdtt.nbdream.domain.entity.FarmWorkEntity
import kr.co.wdtt.nbdream.domain.entity.FarmWorkEra
import kr.co.wdtt.nbdream.ui.main.calendar.providers.FakeFarmWorkEntityListProvider
import kr.co.wdtt.nbdream.ui.theme.Paddings
import kr.co.wdtt.nbdream.ui.theme.colors
import kr.co.wdtt.nbdream.ui.theme.typo
import kotlin.math.abs

private const val FARM_WORK_ITEM_HEIGHT = 20

@Composable
fun FarmWorkCalendar(
    farmWorks: List<FarmWorkEntity>,
    modifier: Modifier = Modifier
) {
    val graphCategories = remember {
        listOf(
            Pair(R.string.calendar_farm_work_growth, FarmWorkCategory.GROWTH),
            Pair(R.string.calendar_farm_work_climate, FarmWorkCategory.CLIMATE),
            Pair(R.string.calendar_farm_work_pest, FarmWorkCategory.PEST)
        )
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        FarmWorkEraHeader(
            modifier = Modifier.padding(bottom = Paddings.medium)
        )
        graphCategories.forEach { graphCategory ->
                Text(
                    text = stringResource(id = graphCategory.first),
                    style = MaterialTheme.typo.bodyM,
                    color = MaterialTheme.colors.text1
                )
                FarmWorkGraph(
                    modifier = Modifier.fillMaxWidth(),
                    farmWorks = farmWorks.filter { it.category == graphCategory.second }
                )
            }
    }
}

@Composable
private fun FarmWorkEraHeader(
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        listOf(
            R.string.calendar_farm_work_era_early,
            R.string.calendar_farm_work_era_mid,
            R.string.calendar_farm_work_era_late,
        ).forEach {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = it),
                style = MaterialTheme.typo.labelM,
                color = MaterialTheme.colors.text2,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun FarmWorkGraph(
    farmWorks: List<FarmWorkEntity>,
    modifier: Modifier = Modifier
) {
    FarmWorkRow(
        modifier = modifier,
        farmWorks = farmWorks,
        content = {
            farmWorks.forEach {
                FarmWorkItem(
                    modifier = Modifier.padding(bottom = Paddings.xsmall),
                    farmWork = it
                )
            }
        }
    )
}

@Composable
private fun FarmWorkRow(
    farmWorks: List<FarmWorkEntity>,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.mapIndexed{ index, measurable ->
                val eraLength = with(farmWorks[index]){ endEra.value - startEra.value + 1 }
                measurable.measure(
                    constraints.copy(
                        minWidth =  (constraints.maxWidth/3) * eraLength
                    )
                )
            }

            layout(
                width = constraints.maxWidth,
                height = placeables.first().height * placeables.size
            ) {
                var yPosition = 0
                placeables.forEachIndexed { index, placeable ->
                    placeable.place(
                        x = when (farmWorks[index].startEra) {
                            FarmWorkEra.EARLY -> 0
                            FarmWorkEra.MID -> constraints.maxWidth / 3
                            FarmWorkEra.LATE -> constraints.maxWidth / 3 * 2
                        },
                        y = yPosition
                    )
                    yPosition += placeable.height
                }
            }
        }
    )
}

@Composable
private fun FarmWorkItem(
    farmWork: FarmWorkEntity,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(FARM_WORK_ITEM_HEIGHT.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .background(color = Color(farmWork.dreamCrop.cropColor)),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = farmWork.farmWork,
            style = MaterialTheme.typo.labelR,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun FarmWorkCalendarPreview(
    @PreviewParameter(FakeFarmWorkEntityListProvider::class) farmWorks: List<FarmWorkEntity>
) {
    Surface{
        FarmWorkCalendar(farmWorks = farmWorks)
    }
}