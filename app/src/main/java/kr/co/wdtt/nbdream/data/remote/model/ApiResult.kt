package kr.co.wdtt.nbdream.data.remote.model

class ApiResult<out T> (
    val response: ApiResponse<T>,
    val headers: Map<String, String> = emptyMap(),
    val code: Int = -1
)