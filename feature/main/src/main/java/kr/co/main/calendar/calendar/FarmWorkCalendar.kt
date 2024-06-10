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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import kr.co.domain.entity.FarmWorkEntity
import kr.co.main.R
import kr.co.main.calendar.model.FarmWorkModel
import kr.co.main.calendar.providers.FakeFarmWorkModelListProvider
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

private const val FARM_WORK_ITEM_HEIGHT = 20

@Composable
internal fun FarmWorkCalendar(
    farmWorks: List<FarmWorkModel>,
    modifier: Modifier = Modifier
) {
    val graphCategories = remember {
        listOf(
            Pair(R.string.feature_main_calendar_farm_work_growth, FarmWorkEntity.Category.GROWTH),
            Pair(R.string.feature_main_calendar_farm_work_climate, FarmWorkEntity.Category.CLIMATE),
            Pair(R.string.feature_main_calendar_farm_work_pest, FarmWorkEntity.Category.PEST)
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
            FarmWorkCalendarContent(
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
            R.string.feature_main_calendar_farm_work_era_early,
            R.string.feature_main_calendar_farm_work_era_mid,
            R.string.feature_main_calendar_farm_work_era_late,
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
private fun FarmWorkCalendarContent(
    farmWorks: List<FarmWorkModel>,
    modifier: Modifier = Modifier
) {
    FarmWorkCalendarRow(
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
private fun FarmWorkItem(
    farmWork: FarmWorkModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(FARM_WORK_ITEM_HEIGHT.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .background(color = Color(farmWork.crop.color)),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = farmWork.farmWork,
            style = MaterialTheme.typo.labelR,
            color = MaterialTheme.colors.text1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun FarmWorkCalendarRow(
    farmWorks: List<FarmWorkModel>,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.mapIndexed { index, measurable ->
                val eraLength = with(farmWorks[index]) { endEra.value - startEra.value + 1 }
                measurable.measure(
                    constraints.copy(
                        minWidth = (constraints.maxWidth / 3) * eraLength
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
                            FarmWorkEntity.Era.EARLY -> 0
                            FarmWorkEntity.Era.MID -> constraints.maxWidth / 3
                            FarmWorkEntity.Era.LATE -> constraints.maxWidth / 3 * 2
                        },
                        y = yPosition
                    )
                    yPosition += placeable.height
                }
            }
        }
    )
}


@Preview
@Composable
private fun FarmWorkCalendarPreview(
    @PreviewParameter(FakeFarmWorkModelListProvider::class) farmWorks: List<FarmWorkModel>
) {
    Surface {
        FarmWorkCalendar(farmWorks = farmWorks)
    }
}