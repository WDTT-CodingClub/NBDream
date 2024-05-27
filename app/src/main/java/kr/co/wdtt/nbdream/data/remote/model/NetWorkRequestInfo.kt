package kr.co.wdtt.nbdream.data.remote.model

class NetWorkRequestInfo private constructor(
    val method: RequestType,
    val headers: Map<String, String>,
    val queryMap: Map<String, String>?,
    val body: Any?,
){

    class Builder(private val method: RequestType = RequestType.GET) {
        private val headers = mutableMapOf<String, String>()
        private var queryMap: Map<String, String>? = null
        private var body: Any? = null

        fun withHeaders(headerMap: Map<String, String>) = apply {
            headers.putAll(headerMap)
        }

        fun withQueryMap(requestQueryMap: Map<String, String>) = apply {
            queryMap = requestQueryMap
        }

        fun withBody(requestBody: Any) = apply {
            body = requestBody
        }

        fun build() = NetWorkRequestInfo(method, headers, queryMap, body)
    }

}
