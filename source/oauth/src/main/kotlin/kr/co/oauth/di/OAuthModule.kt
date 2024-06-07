package kr.co.oauth.di

import android.content.Context
import com.kakao.sdk.user.UserApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.co.domain.model.AuthType
import kr.co.domain.proivder.SocialLoginProvider
import kr.co.oauth.KakaoLoginProviderImpl
import kr.co.oauth.annotation.SocialLoginKey
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class OAuthModule {
    @Singleton
    @Provides
    @SocialLoginKey(type = AuthType.KAKAO)
    fun providesKakaoLoginService(@ApplicationContext context: Context): SocialLoginProvider
    = KakaoLoginProviderImpl(UserApiClient.instance, context)
}