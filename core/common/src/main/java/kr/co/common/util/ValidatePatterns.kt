package kr.co.common.util

import java.util.regex.Pattern

object ValidatePatterns {
    @JvmStatic
    val nickName: Pattern = Pattern.compile("^[A-Za-z가-힣0-9]{2,12}\$")
}