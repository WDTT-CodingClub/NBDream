package kr.co.remote

import junit.framework.TestCase.assertNotNull
import kr.co.nbdream.core.remote.BuildConfig
import kotlin.test.Test

class LocalPropertiesTest {

    @Test
    fun `로컬 프로퍼티 테스트`() {
        val holidayApiKey = BuildConfig.HOLIDAY_API_KEY

        assertNotNull(holidayApiKey, "HOLIDAY_API_KEY가 존재하지 않습니다.")
    }
}