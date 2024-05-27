package kr.co.wdtt.nbdream.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.data.mapper.EntityWrapper
import kr.co.wdtt.nbdream.domain.entity.FarmWorkEntity

interface FarmWorkRepository {
    /*
    suspend fun getCropCategoriesXml(): Flow<ApiResponse<NsrCropCategoriesResponse>>
    suspend fun getCropsByCategoryXml(categoryCode: String): Flow<ApiResponse<NsrCropsByCategoryResponse>>
    */
    suspend fun getFarmWorkByCrop(cropCode: String): Flow<EntityWrapper<List<FarmWorkEntity>>>
}