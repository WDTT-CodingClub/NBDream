package kr.co.wdtt.nbdream.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.data.source.remote.ApiResponse
import kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork.dto.NsrCropCategoriesResponse
import kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork.dto.NsrCropsByCategoryResponse
import kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork.dto.NsrFarmWorkByCropResponse

interface CalFarmWorkRepo {
    suspend fun getCropCategoriesXml(): Flow<ApiResponse<NsrCropCategoriesResponse>>
    suspend fun getCropsByCategoryXml(categoryCode: String): Flow<ApiResponse<NsrCropsByCategoryResponse>>
    suspend fun getFarmWorkByCrop(cropCode: String): Flow<ApiResponse<NsrFarmWorkByCropResponse>>
}