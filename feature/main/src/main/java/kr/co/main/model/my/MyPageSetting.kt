package kr.co.main.model.my

import androidx.annotation.StringRes
import kr.co.main.R

internal enum class MyPageSetting(
    @StringRes val resId: Int
) {
    NOTIFICATION(
        resId = R.string.feature_main_my_setting_notification
    ),
    PRIVACY_POLICY(
        resId = R.string.feature_main_my_setting_privacy_policy
    ),
    LOGOUT(
        resId = R.string.feature_main_my_setting_logout
    ),
    APP_INFO(
        resId = R.string.feature_main_my_setting_app_info
    ),
    UNREGISTER(
        resId = R.string.feature_main_my_setting_unregister
    ),
}