package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kr.co.data.model.data.ServerImageResult
import kr.co.data.source.remote.ServerImageRemoteDataSource
import kr.co.nbdream.core.remote.BuildConfig
import kr.co.remote.mapper.ServerImageRemoteMapper
import kr.co.remote.model.response.GetServerImageResponse
import timber.log.Timber
import java.io.File
import javax.inject.Inject

internal class ServerImageRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient,
) : ServerImageRemoteDataSource {

    @OptIn(InternalAPI::class, ExperimentalSerializationApi::class)
    override suspend fun upload(domain: String, image: File): ServerImageResult {
        // TODO: client를 DI로 받은 걸 사용하게끔 해야 함. 근데 여러 HttpClient가 존재할텐데.
        val client = HttpClient(Android) {
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
                requestTimeoutMillis = 10_000L
                connectTimeoutMillis = 10_000L
            }

            install(DefaultRequest) {
                url("http://34.47.73.18/")
                contentType(ContentType.Application.Json)

//                runBlocking { session.getAccessToken() }?.let {
//                    headers {
//                        append(HttpHeaders.Authorization, NetworkModule.HEADER_RESPONSE_BEARER + it)
//                    }
//                }
            }

//            install(Auth) {
//                bearer {
//                    refreshTokens {
//                        val token = client.post {
//                            markAsRefreshTokenRequest()
//                            url(BuildConfig.BASE_URL + NetworkModule.REFRESH_AUTHORIZATION_URL)
//                            setBody(
//                                buildJsonObject {
//                                    put(NetworkModule.REFRESH_TOKEN, session.getRefreshToken())
//                                }
//                            )
//                        }.body<PostAuthRefreshResponse>()
//
//                        session.updateAccessToken(token.resultData.accessToken)
//
//                        BearerTokens(
//                            accessToken = token.resultData.accessToken,
//                            refreshToken = token.resultData.refreshToken
//                        )
//                    }
//                }
//            }

//            HttpResponseValidator {
//                validateResponse { response ->
//                    if (!response.status.isSuccess()) {
//                        throw if (response.status == HttpStatusCode.Unauthorized) { //401 Unauthorized
//                            session.removeAll()
//
//                            CustomException(
//                                message = "token expired",
//                                customError = CustomErrorType.UNAUTHORIZED
//                            )
//
//                        } else {
//                            val error = runCatching {
//                                JSONObject(response.bodyAsText()).getString(NetworkModule.RESPONSE_ERROR_MESSAGE)
//                            }.getOrNull()
//
//                            CustomException(
//                                message = error,
//                                customError = CustomErrorType.customError[response.status.value] ?: CustomErrorType.UNKNOWN
//                            )
//                        }
//                    }
//                }
//            }
        }

        return client.submitFormWithBinaryData(
            url = "api/$domain/images",
            formData = formData {
                append("image", image.readBytes(), Headers.build {
                    append(HttpHeaders.ContentType, "image/png")
                    append(HttpHeaders.ContentDisposition, "filename=${image.name}")
                }
                )
            },

            )
            .body<GetServerImageResponse>()
            .let(ServerImageRemoteMapper::convert)
    }
}
