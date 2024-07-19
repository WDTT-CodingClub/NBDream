package kr.co.data.source.remote


interface FcmRemoteDataSource {
    suspend fun saveFcmToken(token: String)
    suspend fun expireFcmToken()
    suspend fun sendFcmMessage(token: String, title: String, body: String)
}