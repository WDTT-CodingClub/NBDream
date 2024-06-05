package kr.co.wdtt.nbdream.domain.repository

import io.ktor.http.ContentType

interface UploadImageRepository {
    suspend fun uploadImage(
        id: String,
        image: ContentType.MultiPart,
    ): String

    suspend fun deleteImage(
        id: String,
        url: String,
    )
}
