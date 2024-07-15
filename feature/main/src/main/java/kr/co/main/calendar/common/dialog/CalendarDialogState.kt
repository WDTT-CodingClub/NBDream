package kr.co.main.calendar.common.dialog

import kr.co.main.R

internal sealed class CalendarDialogState {
    abstract val headerId: Int
    abstract val descriptionId: Int
    abstract val onConfirm: () -> Unit
    abstract val onDismissRequest: () -> Unit
    open val confirmToastId: Int? = null

    data class DeleteScheduleDialogState(
        val _onConfirm: () -> Unit,
        val _onDismissRequest: () -> Unit
    ) : CalendarDialogState() {
        override val headerId = R.string.feature_main_calendar_delete_schedule_dialog_title
        override val descriptionId = R.string.feature_main_calendar_delete_schedule_dialog_description
        override val onConfirm = _onConfirm
        override val onDismissRequest = _onDismissRequest
        override val confirmToastId = R.string.feature_main_calendar_delete_schedule_dialog_confirm
    }

    data class DeleteDiaryDialogState(
        val _onConfirm: () -> Unit,
        val _onDismissRequest: () -> Unit
    ) : CalendarDialogState() {
        override val headerId = R.string.feature_main_calendar_delete_diary_dialog_title
        override val descriptionId = R.string.feature_main_calendar_delete_diary_dialog_description
        override val onConfirm = _onConfirm
        override val onDismissRequest = _onDismissRequest
        override val confirmToastId = R.string.feature_main_calendar_delete_diary_dialog_confirm
    }
}