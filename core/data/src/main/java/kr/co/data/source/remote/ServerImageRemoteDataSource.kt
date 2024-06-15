package kr.co.data.source.remote

import kr.co.data.model.data.ServerImageResult
import java.io.File

interface ServerImageRemoteDataSource {
    suspend fun upload(
        domain: String,
        image: File,
    ): ServerImageResult

    // TODO: delete
}
