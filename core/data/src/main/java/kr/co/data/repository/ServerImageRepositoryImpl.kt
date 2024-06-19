package kr.co.data.repository

import kr.co.data.mapper.ServerImageMapper
import kr.co.data.source.remote.ServerImageRemoteDataSource
import kr.co.domain.entity.ServerImageEntity
import kr.co.domain.repository.ServerImageRepository
import java.io.File
import javax.inject.Inject

internal class ServerImageRepositoryImpl @Inject constructor(
    private val remote: ServerImageRemoteDataSource
) : ServerImageRepository {

    override suspend fun upload(domain: String, image: File): ServerImageEntity? =
        remote.upload(domain, image).let(ServerImageMapper::convert)

    override suspend fun delete(url: String) {
        remote.delete(url)
    }
}
