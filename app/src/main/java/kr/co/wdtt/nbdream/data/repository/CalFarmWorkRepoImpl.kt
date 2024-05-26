package kr.co.wdtt.nbdream.data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.data.source.remote.ApiResponse
import kr.co.wdtt.nbdream.data.source.remote.ApiResponseHandler
import kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork.NsrFarmWorkApi
import kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork.dto.NsrFarmWorkByCropResponse
import kr.co.wdtt.nbdream.domain.repository.CalFarmWorkRepo
import javax.inject.Inject

class CalFarmWorkRepoImpl @Inject constructor(
    private val nsrFarmWorkApi: NsrFarmWorkApi
) : CalFarmWorkRepo {
    /*
    override suspend fun getCropCategoriesXml(): Flow<ApiResponse<NsrCropCategoriesResponse>> =
        ApiResponseHandler.handle { nsrFarmWorkApi.getCropCategoriesXml() }

    override suspend fun getCropsByCategoryXml(categoryCode: String): Flow<ApiResponse<NsrCropsByCategoryResponse>> =
        ApiResponseHandler.handle { nsrFarmWorkApi.getCropsByCategoryXml(categoryCode) }
    */
    override suspend fun getFarmWorkByCrop(cropCode: String): Flow<ApiResponse<NsrFarmWorkByCropResponse>> =
        ApiResponseHandler.handle { nsrFarmWorkApi.getFarmWorkByCrop(cropCode) }

}

