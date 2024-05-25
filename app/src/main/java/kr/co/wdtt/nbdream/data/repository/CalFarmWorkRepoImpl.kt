package kr.co.wdtt.nbdream.data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.data.source.remote.ApiResponse
import kr.co.wdtt.nbdream.data.source.remote.ApiResponseHandler
import kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork.NsrFarmWorkApi
import kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork.dto.NsrCropCategoriesResponse
import kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork.dto.NsrCropsByCategoryResponse
import kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork.dto.NsrFarmWorkByCropResponse
import kr.co.wdtt.nbdream.domain.repository.CalFarmWorkRepo
import javax.inject.Inject

class CalFarmWorkRepoImpl @Inject constructor(
    private val nsrFarmWorkApi: NsrFarmWorkApi
) : CalFarmWorkRepo {
    override suspend fun getCropCategories(): Flow<ApiResponse<NsrCropCategoriesResponse>> =
        ApiResponseHandler.handle { nsrFarmWorkApi.getCropCategories() }

    override suspend fun getCropsByCategory(categoryCode: String): Flow<ApiResponse<NsrCropsByCategoryResponse>> =
        ApiResponseHandler.handle { nsrFarmWorkApi.getCropsByCategory(categoryCode) }

    override suspend fun getFarmWorkByCropJson(cropCode: String): Flow<ApiResponse<NsrFarmWorkByCropResponse>> =
        ApiResponseHandler.handle { nsrFarmWorkApi.getFarmWorkByCropJson(cropCode) }

}

