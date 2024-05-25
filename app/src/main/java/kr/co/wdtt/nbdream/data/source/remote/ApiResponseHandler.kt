package kr.co.wdtt.nbdream.data.source.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

object ApiResponseHandler {
    fun <T> handle(
        execute: suspend () -> T
    ): Flow<ApiResponse<T>> =
        flow {
            emit(ApiResponse.Loading)
            try {
                emit(ApiResponse.Success(execute()))
            } catch (e: HttpException) {
                emit(ApiResponse.Fail.Error(e.code(), e.message()))
            } catch (e: Exception) {
                emit(ApiResponse.Fail.Exception(e))
            }
        }
}
sealed class ApiResponse<out T>{
    data object Loading: ApiResponse<Nothing>()
    data class Success<T>(val data: T): ApiResponse<T>()
    sealed class Fail: ApiResponse<Nothing>() {
        data class Error(val code: Int, val message: String?) : Fail()
        data class Exception(val e: Throwable) : Fail()
    }
}
