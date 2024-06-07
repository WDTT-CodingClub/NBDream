package kr.co.oauth.di

import android.content.Context
import com.kakao.sdk.user.UserApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.domain.proivder.SocialLoginProvider
import kr.co.oauth.KakaoLoginProviderImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class OAuthModule {
    @Singleton
    @Provides
    fun providesKakaoLoginService(context: Context): SocialLoginProvider
    = KakaoLoginProviderImpl(UserApiClient.instance, context)
}