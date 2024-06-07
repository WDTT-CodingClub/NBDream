package kr.co.oauth.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import kr.co.domain.model.AuthType
import kr.co.domain.proivder.SocialLoginProvider
import kr.co.oauth.GoogleLoginProviderImpl
import kr.co.oauth.KakaoLoginProviderImpl
import kr.co.oauth.NaverLoginProviderImpl
import kr.co.oauth.SocialLoginProviderImpl
import kr.co.oauth.annotation.SocialLoginKey
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface OAuthModule {

    @Singleton
    @Binds
    fun bindSocialLoginProvider(
        provider: SocialLoginProviderImpl,
    ): SocialLoginProvider

    @Binds
    @IntoMap
    @SocialLoginKey(type = AuthType.GOOGLE)
    fun bindGoogleLoginProvider(
        provider: GoogleLoginProviderImpl
    ): SocialLoginProvider
    @Binds
    @IntoMap
    @SocialLoginKey(type = AuthType.KAKAO)
    fun bindKakaoLoginProvider(
        provider: KakaoLoginProviderImpl
    ): SocialLoginProvider
    @Binds
    @IntoMap
    @SocialLoginKey(type = AuthType.NAVER)
    fun bindNaverLoginProvider(
        provider: NaverLoginProviderImpl
    ): SocialLoginProvider
}