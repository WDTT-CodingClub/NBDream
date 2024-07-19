package kr.co.main.model.my

import androidx.annotation.StringRes
import kr.co.main.R

internal enum class MyPageNotificationSetting(
    val group: Group,
    @StringRes val nameRes: Int,
) {
    COMMENT(
        Group.COMMUNITY,
        R.string.feature_main_my_setting_notification_comment
    ),

    SCHEDULE(
        Group.WARNING,
        R.string.feature_main_my_setting_notification_schedule
    ),
    ;

    enum class Group(val text: String) {
        COMMUNITY("커뮤니티 알림"),
        WARNING("기능별 알림"),
    }

    companion object {
        fun getByGroup(group: Group): List<MyPageNotificationSetting> {
            return entries.filter { it.group == group }
        }
    }
}