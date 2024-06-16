package kr.co.main.model.my

import androidx.annotation.StringRes
import kr.co.main.R

internal enum class MyPageNotificationSetting(
    val group: Group,
    @StringRes val nameRes: Int,
) {
    KAKAO(
        group = Group.MESSAGING,
        nameRes = R.string.feature_main_my_setting_notification_kakao,
    ),
    MESSAGE(
        group = Group.MESSAGING,
        nameRes = R.string.feature_main_my_setting_notification_message
    ),
    NOTIFICATION(
        Group.MESSAGING,
        R.string.feature_main_my_setting_notification_notification
    ),

    LIKE(
        Group.COMMUNITY,
        R.string.feature_main_my_setting_notification_like
    ),
    COMMENT(
        Group.COMMUNITY,
        R.string.feature_main_my_setting_notification_comment
    ),

    SCHEDULE(
        Group.WARNING,
        R.string.feature_main_my_setting_notification_schedule
    ),
    PEST(
        Group.WARNING,
        R.string.feature_main_my_setting_notification_pest
    ),
    HAZARDOUS_WEATHER(
        Group.WARNING,
        R.string.feature_main_my_setting_notification_hazardous
    ),
    ;

    enum class Group(val text: String) {
        MESSAGING("혜택 및 마케팅 알림"),
        COMMUNITY("커뮤니티 알림"),
        WARNING("기능별 알림"),
    }

    companion object {
        fun getByGroup(group: Group): List<MyPageNotificationSetting> {
            return entries.filter { it.group == group }
        }
    }
}