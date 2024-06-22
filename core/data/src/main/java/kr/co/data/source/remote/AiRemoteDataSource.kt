package kr.co.data.source.remote

interface AiRemoteDataSource {
    suspend fun send(m: String): String
}
