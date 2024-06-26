package kr.co.domain.usecase.image

import kr.co.domain.repository.ServerImageRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteImageUseCase @Inject  constructor(
    private val imageRepository: ServerImageRepository
) : SuspendUseCase<String,Unit>(){
    override suspend fun build(url: String?) {
        checkNotNull(url)

        imageRepository.delete(url)
    }
}