package kr.co.wdtt.nbdream.data.remote.retrofit

import android.util.Log
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class StringConverterFactory : Converter.Factory() {

    companion object {
        private val MEDIA_TYPE = "application/json; charset=UTF-8".toMediaTypeOrNull()
    }

    private val json = Json { ignoreUnknownKeys = true }
    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): RequestBodyConverter<*> {
        val serializer = json.serializersModule.serializer(type)
        return RequestBodyConverter(serializer)
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): Converter<ResponseBody, *>? =
        if (String::class.java == type) {
            ResponseBodyConverter()
        } else {
            super.responseBodyConverter(type, annotations, retrofit)
        }

    inner class RequestBodyConverter<T>(private val serializer: KSerializer<T>) :
        Converter<T, RequestBody> {
        override fun convert(p0: T): RequestBody {
            val jsonString = json.encodeToString(serializer, p0)
            return jsonString.toRequestBody(MEDIA_TYPE)
        }
    }

    inner class ResponseBodyConverter : Converter<ResponseBody, String> {
        override fun convert(p0: ResponseBody) = p0.string()
    }
}