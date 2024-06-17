package kr.co.main.model.my

import androidx.annotation.StringRes
import kr.co.main.R
import kr.co.main.navigation.MY_PAGE_SETTING_APP_INFO_ROUTE
import kr.co.main.navigation.MY_PAGE_SETTING_DELETE_ACCOUNT_ROUTE
import kr.co.main.navigation.MY_PAGE_SETTING_LOGOUT_ROUTE
import kr.co.main.navigation.MY_PAGE_SETTING_NOTIFICATION_ROUTE
import kr.co.main.navigation.MY_PAGE_SETTING_PRIVACY_POLICY_ROUTE

internal enum class MyPageSetting(
    @StringRes val resId: Int,
    val route: String,
) {
    NOTIFICATION(
        resId = R.string.feature_main_my_setting_notification,
        route = MY_PAGE_SETTING_NOTIFICATION_ROUTE
    ),
    PRIVACY_POLICY(
        resId = R.string.feature_main_my_setting_privacy_policy,
        route = MY_PAGE_SETTING_PRIVACY_POLICY_ROUTE
    ),
    LOGOUT(
        resId = R.string.feature_main_my_setting_logout,
        route = MY_PAGE_SETTING_LOGOUT_ROUTE
    ),
    APP_INFO(
        resId = R.string.feature_main_my_setting_app_info,
        route = MY_PAGE_SETTING_APP_INFO_ROUTE
    ),
    DELETE_ACCOUNT(
        resId = R.string.feature_main_my_setting_unregister,
        route = MY_PAGE_SETTING_DELETE_ACCOUNT_ROUTE
    ),
}