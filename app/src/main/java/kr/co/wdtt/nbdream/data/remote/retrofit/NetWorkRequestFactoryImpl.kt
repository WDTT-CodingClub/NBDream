package kr.co.wdtt.nbdream.data.remote.retrofit

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kr.co.wdtt.nbdream.data.remote.api.NetworkService
import kr.co.wdtt.nbdream.data.remote.model.ApiResponse
import kr.co.wdtt.nbdream.data.remote.model.ApiResult
import kr.co.wdtt.nbdream.data.remote.model.NetWorkRequestInfo
import kr.co.wdtt.nbdream.data.remote.model.RequestType
import javax.inject.Inject
import kotlin.reflect.KClass

class NetWorkRequestFactoryImpl @Inject constructor(
    private val remote: NetworkService,
    private val headerParser: HeaderParser,
):NetWorkRequestFactory {

    private val json = Json { ignoreUnknownKeys = true }
    @OptIn(InternalSerializationApi::class)
    override suspend fun <T: Any> create(
        url: String,
        requestInfo: NetWorkRequestInfo,
        type: KClass<T>,
    ): ApiResult<T> = try {
        val response = when(requestInfo.method) {
            RequestType.GET -> performGetRequest(url, requestInfo.queryMap, requestInfo.headers)
            RequestType.POST -> performPostRequest(url, requestInfo.body, requestInfo.headers)
            RequestType.PUT -> performPutRequest(url, requestInfo.body, requestInfo.headers)
            RequestType.DELETE -> performDeleteRequest(url, requestInfo.body, requestInfo.headers)
        }

        val responseHeaders = headerParser.parseHeadersToMap(response.headers())
        val requestCode = response.code()

        val apiResponse = if (response.isSuccessful) {
            val body = response.body()
            val serializer: KSerializer<T> = type.serializer()
            val responseModel: T = Json.decodeFromString(serializer, body ?: throw IllegalArgumentException("Response body is null"))
            ApiResponse.Success(responseModel)
        } else {
            val errorMessage = response.errorBody()?.toString() ?: response.message()

            ApiResponse.Fail(Throwable(errorMessage))
        }
        ApiResult(apiResponse, responseHeaders, requestCode)
    } catch(e: Throwable) {
        ApiResult(ApiResponse.Fail(e))
    }

    private suspend fun performGetRequest(
        url: String,
        queryMap: Map<String, String>?,
        header: Map<String, String>,
    ) = queryMap?.let {
        remote.get(url, queryMap, header)
    }?: remote.get(url,header)


    private suspend fun performPostRequest(
        url: String,
        body: Any?,
        header: Map<String, String>,
    ) = body?.let { remote.post(url, it, header)
    }?: remote.post(url, header)

    private suspend fun performPutRequest(
        url: String,
        body: Any?,
        header: Map<String, String>,
    ) = body?.let { remote.put(url, it, header)
    }?: remote.put(url, header)

    private suspend fun performDeleteRequest(
        url: String,
        body: Any?,
        header: Map<String, String>,
    ) = body?.let { remote.delete(url, it, header)
    }?: remote.delete(url, header)
}