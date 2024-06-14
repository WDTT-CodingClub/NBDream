package kr.co.remote.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
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
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kr.co.common.model.CustomErrorType
import kr.co.common.model.CustomException
import kr.co.nbdream.core.remote.BuildConfig
import kr.co.remote.model.response.PostAuthRefreshResponse
import kr.co.data.source.local.SessionLocalDataSource
import kr.co.remote.retrofit.api.HolidayApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Named
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
                    ignoreUnknownKeys = true // 알수없는 키 무시
                    encodeDefaults = true // 기본값 인코딩
                    explicitNulls = false // 명시적인 null생략
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
                            buildJsonObject {
                                put(REFRESH_TOKEN, session.getRefreshToken())
                            }
                        )
                    }.body<PostAuthRefreshResponse>()

                    session.updateAccessToken(token.resultData.accessToken)

                    BearerTokens(
                        accessToken = token.resultData.accessToken,
                        refreshToken = token.resultData.refreshToken
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
                            customError = CustomErrorType.customError[response.status.value] ?: CustomErrorType.UNKNOWN
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val HEADER_RESPONSE_BEARER = "Bearer "
        private const val REFRESH_AUTHORIZATION_URL = "/api/v1/auth/refresh"
        private const val REFRESH_TOKEN = "refreshToken"
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

    @Singleton
    @Provides
    @Named("holiday")
    fun provideHolidayRetrofit(
        okHttpClient: OkHttpClient,
    ) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    @Singleton
    @Provides
    fun provideHolidayService(
        @Named("holiday") retrofit: Retrofit
    ) = retrofit.create(HolidayApi::class.java)
}