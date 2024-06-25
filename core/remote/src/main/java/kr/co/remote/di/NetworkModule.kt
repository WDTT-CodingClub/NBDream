package kr.co.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kr.co.common.model.CustomErrorType
import kr.co.common.model.CustomException
import kr.co.data.source.local.SessionLocalDataSource
import kr.co.nbdream.core.remote.BuildConfig
import kr.co.remote.model.Dto
import kr.co.remote.model.request.auth.PostAuthRefreshTokenRequest
import kr.co.remote.model.response.auth.PostAuthRefreshResponse
import kr.co.remote.model.response.auth.PostAuthResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {
    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideHttpClient(
        session: SessionLocalDataSource,
    ) = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true //출력시 이쁘게 포맷팅
                    isLenient = true // 관대한 파싱
                    ignoreUnknownKeys = true // 알 수 없는 키 무시
                    encodeDefaults = true // 기본 값 인코딩
                    explicitNulls = false // 명시적인 null 생략
                }
            )
        }

        install(Logging) {
            level = if (BuildConfig.DEBUG) {
                LogLevel.ALL
            } else {
                LogLevel.NONE
            }

            logger = object : Logger {
                override fun log(message: String) {
                    Timber.tag("NetworkModule: Logger").d(message)
                }
            }
        }

        install(HttpTimeout) {
            requestTimeoutMillis = CONNECTION_TIMEOUT
            connectTimeoutMillis = CONNECTION_TIMEOUT
        }

        install(DefaultRequest) {
            url(BuildConfig.BASE_URL)
            contentType(ContentType.Application.Json)

            runBlocking { session.getAccessToken() }?.let {
                headers {
                    append(HttpHeaders.Authorization, HEADER_RESPONSE_BEARER + it)
                }
            }
        }

        install(Auth) {
            bearer {
                refreshTokens {
                    val token = client.post {
                        markAsRefreshTokenRequest()
                        url(BuildConfig.BASE_URL + REFRESH_AUTHORIZATION_URL)
                        setBody(
                            PostAuthRefreshTokenRequest(
                                accessToken = session.getAccessToken()!!,
                                refreshToken = session.getRefreshToken()!!
                            )
                        )
                    }.body<Dto<PostAuthResponse>>()

                    session.updateAccessToken(token.data.accessToken)

                    BearerTokens(
                        accessToken = token.data.accessToken,
                        refreshToken = token.data.refreshToken
                    )
                }
            }
        }

        HttpResponseValidator {
            validateResponse { response ->
                if (!response.status.isSuccess()) {
                    throw if (response.status == HttpStatusCode.Unauthorized) { //401 Unauthorized
                        session.removeAll()

                        CustomException(
                            message = "token expired",
                            customError = CustomErrorType.UNAUTHORIZED
                        )

                    } else {
                        val error = runCatching {
                            JSONObject(response.bodyAsText()).getString(RESPONSE_ERROR_MESSAGE)
                        }.getOrNull()

                        CustomException(
                            message = error,
                            customError = CustomErrorType.customError[response.status.value]
                                ?: CustomErrorType.UNKNOWN
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val HEADER_RESPONSE_BEARER = "Bearer "
        private const val REFRESH_AUTHORIZATION_URL = "refresh-tokens"
        private const val RESPONSE_ERROR_MESSAGE = "message"

        private const val CONNECTION_TIMEOUT = 10_000L
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
}