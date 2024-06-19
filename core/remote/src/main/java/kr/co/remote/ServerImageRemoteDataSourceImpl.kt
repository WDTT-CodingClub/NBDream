package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType.*
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kr.co.data.model.data.ServerImageResult
import kr.co.data.source.remote.ServerImageRemoteDataSource
import kr.co.remote.mapper.ServerImageRemoteMapper
import kr.co.remote.model.request.community.DeleteImageRequest
import kr.co.remote.model.response.PostServerImageResponse
import java.io.File
import java.nio.file.Files
import javax.inject.Inject

internal class ServerImageRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient,
) : ServerImageRemoteDataSource {

    private companion object {
        const val IMG = "image"
        const val IMG_UPLOAD_URL = "api/images/upload/"
        const val IMG_DELETE_URL = "api/images/"
    }

    override suspend fun upload(domain: String, image: File): ServerImageResult {
        return client.post("$IMG_UPLOAD_URL$domain") {
            MultiPartFormDataContent(
                formData {
                    append(IMG, image.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, Files.probeContentType(image.toPath()))
                        append(HttpHeaders.ContentDisposition, "form-data; name=\"file\"; filename=\"${image.name}\"")
                    }
                    )
                }
            )
            contentType(MultiPart.FormData)
        }.body<PostServerImageResponse>()
            .let(ServerImageRemoteMapper::convert)
    }

    override suspend fun delete(url: String) {
        client.delete(IMG_DELETE_URL) {
            setBody(
                DeleteImageRequest(
                    imageUrl = url,
                )
            )
        }

    }
}