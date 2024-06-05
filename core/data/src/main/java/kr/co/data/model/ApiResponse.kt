package kr.co.data.model

sealed class ApiResponse<out T> {
    class Success<T>(
        val resultCode: Int,
        val resultMessage: String,
        val data: T
    ): ApiResponse<T>()

    class Fail(
        val resultCode: Int,
        val resultMessage: String,
        val error: Throwable
    ): ApiResponse<Nothing>()
}