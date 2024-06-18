package kr.co.domain.usecase.validate

import kr.co.common.util.ValidatePatterns
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidateNameUseCase @Inject constructor() {
    operator fun invoke(name: String?): Boolean {
        checkNotNull(name)
        return ValidatePatterns.name.matcher(name.trim()).matches()
    }
}