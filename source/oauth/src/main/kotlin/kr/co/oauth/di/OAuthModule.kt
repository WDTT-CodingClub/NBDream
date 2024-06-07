package kr.co.oauth.di

import android.content.Context
import com.kakao.sdk.user.UserApiClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import kr.co.domain.model.AuthType
import kr.co.domain.proivder.SocialLoginProvider
import kr.co.oauth.KakaoLoginProviderImpl
import kr.co.oauth.SocialLoginProviderImpl
import kr.co.oauth.annotation.SocialLoginKey
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class OAuthModule {

    @Provides
    @Singleton
    fun providesSocialLoginProvider(
        params: Map<AuthType, @JvmSuppressWildcards Provider<SocialLoginProvider>>
    ): SocialLoginProvider = SocialLoginProviderImpl(params)

    @Provides
    @Singleton
    @IntoMap
    @SocialLoginKey(type = AuthType.KAKAO)
    fun providesKakaoLoginService(@ApplicationContext context: Context): SocialLoginProvider
    = KakaoLoginProviderImpl(UserApiClient.instance, context)
}