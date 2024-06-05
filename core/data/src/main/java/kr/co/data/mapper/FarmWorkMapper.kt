//package kr.co.data.mapper
//
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import kr.co.common.mapper.BaseMapper
//import kr.co.common.mapper.Mapper
//import kr.co.domain.entity.CropScheduleEra
//import kr.co.domain.entity.FarmWorkEntity
//import kr.co.remote.model.response.FarmWorkResponse
//import javax.inject.Inject
//
//internal class FarmWorkMapper @Inject constructor() :
//    Mapper<FarmWorkResponse, List<FarmWorkEntity>>() {
//
//    override fun getSuccess(
//        data: FarmWorkResponse?,
//        extra: Any?
//    ): Flow<EntityWrapper.Success<List<FarmWorkEntity>>> = flow {
//        data?.let {
//            emit(
//                EntityWrapper.Success(
//                    data = it.response.body.items.map { item ->
//                        item.convert()
//                    }
//                )
//            )
//        } ?: emit(EntityWrapper.Success(emptyList()))
//    }
//
//    override fun getFailure(error: Throwable): Flow<EntityWrapper.Fail<List<FarmWorkEntity>>> =
//        flow { emit(EntityWrapper.Fail(error)) }
//
//    private fun FarmWorkResponse.Response.Body.Item.convert() =
//        FarmWorkEntity(
//            dreamCrop = kr.co.domain.entity.DreamCrop.getCropByCode(cropCode),
//            startMonth = beginMon,
//            startEra = CropScheduleEra.getEraByString(beginEra),
//            endMonth = endMon,
//            endEra = CropScheduleEra.getEraByString(endEra),
//            farmWorkTypeCode = farmWorkTypeCode,
//            farmWorkTypeName = farmWorkTypeName,
//            farmWork = farmWork,
//            videoUrl = vodUrl
//        )
//
//    override fun convert(param: FarmWorkResponse): List<FarmWorkEntity> {
//        TODO("Not yet implemented")
//    }
//}
