package kr.co.wdtt.nbdream.data.mapper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.co.wdtt.core.domain.base.BaseMapper
import kr.co.wdtt.nbdream.data.remote.dto.FarmWorkResponse
import kr.co.wdtt.nbdream.domain.entity.FarmWorkEra
import kr.co.wdtt.nbdream.domain.entity.DreamCrop
import kr.co.wdtt.nbdream.domain.entity.FarmWorkCategory
import kr.co.wdtt.nbdream.domain.entity.FarmWorkEntity
import javax.inject.Inject

internal class FarmWorkMapper @Inject constructor() :
    BaseMapper<FarmWorkResponse, List<FarmWorkEntity>>() {

    override fun getSuccess(
        data: FarmWorkResponse?,
        extra: Any?
    ): Flow<EntityWrapper.Success<List<FarmWorkEntity>>> = flow {
        data?.let {
            emit(
                EntityWrapper.Success(
                    data = it.response.body.items.map { item ->
                        item.convert()
                    }
                )
            )
        } ?: emit(EntityWrapper.Success(emptyList()))
    }

    override fun getFailure(error: Throwable): Flow<EntityWrapper.Fail<List<FarmWorkEntity>>> =
        flow { emit(EntityWrapper.Fail(error)) }

    private fun FarmWorkResponse.Response.Body.Item.convert() =
        FarmWorkEntity(
            id = "0",
            dreamCrop = DreamCrop.getCropByCode(cropCode),
            startEra = beginEra.toFarmWorkEra(),
            endEra = endEra.toFarmWorkEra(),
            category = farmWorkTypeName.toFarmWorkCategory(),
            farmWork = farmWork,
            videoUrl = vodUrl
        )

    private fun String.toFarmWorkEra(): FarmWorkEra = when(this){
        "상" -> FarmWorkEra.EARLY
        "중" -> FarmWorkEra.MID
        "하" -> FarmWorkEra.LATE
        else -> throw IllegalArgumentException("Unknown era")
    }

    // TODO 응답으로 받아와지는 정확한 string 값 확인
    private fun String.toFarmWorkCategory(): FarmWorkCategory = when(this) {
        "생육과정(주요농작업)" -> FarmWorkCategory.GROWTH
        "병해충방제" -> FarmWorkCategory.PEST
        "기상재해" -> FarmWorkCategory.CLIMATE
        else -> throw IllegalArgumentException("unknown category")
    }
}
