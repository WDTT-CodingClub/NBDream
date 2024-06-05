package kr.co.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.FarmWorkEntity

interface FarmWorkRepository {
    /*
    suspend fun getCropCategoriesXml(): Flow<ApiResponse<NsrCropCategoriesResponse>>
    suspend fun getCropsByCategoryXml(categoryCode: String): Flow<ApiResponse<NsrCropsByCategoryResponse>>
    */
    suspend fun getFarmWorkByCrop(cropCode: String): List<FarmWorkEntity>
}