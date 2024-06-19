package kr.co.domain.repository

import kr.co.domain.entity.ServerImageEntity
import java.io.File

interface ServerImageRepository {
    suspend fun upload(domain: String, image: File): ServerImageEntity?

    suspend fun delete(imageUrl: String): Boolean

}
