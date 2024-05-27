package kr.co.wdtt.nbdream.data.remote.model

sealed class ApiResponse<out T> {
    class Success<T>(
        val data: T
    ): ApiResponse<T>()

    class Fail(
        val error: Throwable
    ): ApiResponse<Nothing>()
}