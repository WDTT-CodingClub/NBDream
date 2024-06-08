package kr.co.oauth.annotation

import dagger.MapKey
import kr.co.domain.model.AuthType

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class SocialLoginKey(
    val type: AuthType
)
