package kr.co.main.calendar.common.dialog

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kr.co.ui.widget.DreamDialog

@Composable
internal fun CalendarDialogWithToast(
    context: Context,
    showDialog: Boolean,
    @StringRes headerId: Int,
    @StringRes descriptionId: Int,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
    @StringRes confirmToastId: Int? = null
) {
    if (showDialog) {
        DreamDialog(
            header = stringResource(id = headerId),
            description = stringResource(id = descriptionId),
            onConfirm = {
                onConfirm()
                confirmToastId?.let {
                    Toast.makeText(
                        context,
                        context.getString(confirmToastId),
                        Toast.LENGTH_LONG
                    ).show()
                }
                onDismissRequest()
            },
            onDismissRequest = onDismissRequest
        )
    }
}