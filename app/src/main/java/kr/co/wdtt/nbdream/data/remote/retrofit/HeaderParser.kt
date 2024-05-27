package kr.co.wdtt.nbdream.data.remote.retrofit

import okhttp3.Headers
import java.util.Locale
import javax.inject.Inject

class HeaderParser @Inject constructor() {

    fun parseHeadersToMap(headers: Headers): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()

        headers.iterator().forEach { (key, value) ->
            if (key.equals("set-cookie", ignoreCase = true)) {
                val keyInLowerCase = key.lowercase(Locale.getDefault())

                if (headerMap[keyInLowerCase].isNullOrEmpty()) {
                    headerMap[keyInLowerCase] = value
                } else {
                    headerMap[keyInLowerCase] = "${headerMap[keyInLowerCase]}; $value"
                }
            } else {
                headerMap[key] = value
            }
        }
        return headerMap
    }
}
