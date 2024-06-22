package kr.co.domain.usecase

import kr.co.domain.repository.AiRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SendAiChatUseCase @Inject constructor(
    private val aiRepository: AiRepository
): SuspendUseCase<String, String>() {
    override suspend fun build(params: String?): String {
        checkNotNull(params)

        return aiRepository.send(params)
    }
}
