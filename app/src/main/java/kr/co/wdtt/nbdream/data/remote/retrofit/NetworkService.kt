package kr.co.wdtt.nbdream.data.remote.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface NetworkService {
    @GET
    suspend fun get(
        @Url url: String,
        @HeaderMap headers: Map<String, String>,
    ): Response<String>

    @GET
    suspend fun get(
        @Url url: String,
        @QueryMap params: Map<String, String>,
        @HeaderMap headers: Map<String, String>,
    ): Response<String>
    @POST
    suspend fun post(
        @Url url: String,
        @HeaderMap headers: Map<String, String>,
    ): Response<String>

    @POST
    suspend fun post(
        @Url url: String,
        @Body body: Any,
        @HeaderMap headers: Map<String, String>,
    ): Response<String>

    @PUT
    suspend fun put(
        @Url url: String,
        @HeaderMap headers: Map<String, String>,
    ): Response<String>

    @PUT
    suspend fun put(
        @Url url: String,
        @Body body: Any,
        @HeaderMap headers: Map<String, String>,
    ): Response<String>

    @DELETE
    suspend fun delete(
        @Url url: String,
        @HeaderMap headers: Map<String, String>,
    ): Response<String>


    @HTTP(method = "DELETE", hasBody = true)
    suspend fun delete(
        @Url url: String,
        @Body body: Any,
        @HeaderMap headers: Map<String, String>,
    ): Response<String>

}