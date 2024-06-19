package kr.co.main.model.my

import androidx.annotation.StringRes
import kr.co.main.R
import kr.co.main.navigation.MyPageRoute

internal enum class MyPageSetting(
    @StringRes val resId: Int,
    val route: String,
) {
    NOTIFICATION(
        resId = R.string.feature_main_my_setting_notification,
        route = MyPageRoute.SETTING_NOTIFICATION_ROUTE
    ),
    PRIVACY_POLICY(
        resId = R.string.feature_main_my_setting_privacy_policy,
        route = MyPageRoute.SETTING_PRIVACY_POLICY_ROUTE
    ),
    LOGOUT(
        resId = R.string.feature_main_my_setting_logout,
        route = MyPageRoute.SETTING_LOGOUT_ROUTE
    ),
    APP_INFO(
        resId = R.string.feature_main_my_setting_app_info,
        route = MyPageRoute.SETTING_APP_INFO_ROUTE
    ),
    DELETE_ACCOUNT(
        resId = R.string.feature_main_my_setting_unregister,
        route = MyPageRoute.SETTING_DELETE_ACCOUNT_ROUTE
    ),
}