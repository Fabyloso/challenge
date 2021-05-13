package com.fabyloso.core.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.fabyloso.core.R
import com.google.android.material.snackbar.Snackbar

class InAppMessageHelper {
    companion object {
        fun popToastLong(context: Context, @StringRes resId: Int) =
            Toast.makeText(context, context.getString(resId), Toast.LENGTH_LONG).show()

        fun popToastLong(context: Context, message: String) =
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()

        fun popSnackIndefinite(view: View, message: String) {
            Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).apply {
                setAction(R.string.ok) { dismiss() }
                show()
            }
        }
    }

}