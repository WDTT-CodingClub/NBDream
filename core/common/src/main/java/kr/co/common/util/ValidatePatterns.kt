package kr.co.common.util

import java.util.regex.Pattern

object ValidatePatterns {
    @JvmStatic
    val name: Pattern = Pattern.compile("^[ㄱ-ㅎ가-힣]{2,12}\$")
}