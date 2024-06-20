package kr.co.domain.usecase.image

import kr.co.domain.repository.ServerImageRepository
import kr.co.domain.repository.UploadImageRepository
import kr.co.domain.usecase.SuspendUseCase
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UploadImageUseCase @Inject constructor(
    private val imageRepository: ServerImageRepository
): SuspendUseCase<UploadImageUseCase.Params, String>() {
    override suspend fun build(params: Params?): String {
        checkNotNull(params)

        return imageRepository.upload(params.domain, params.file)!!.url
    }

    data class Params (
        val domain: String,
        val file: File
    )
}