package kr.co.domain.repository


interface UploadImageRepository {
    suspend fun uploadImage(
        id: String,
        image: Any,
    ): String

    suspend fun deleteImage(
        id: String,
        url: String,
    )
}
