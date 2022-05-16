package com.grappim.weatherninetwothree.utils.extensions

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.grappim.weatherninetwothree.R

fun View.showSnackbar(
    @StringRes messageResId: Int,
    length: Int,
    @StringRes actionMessageId: Int,
    action: (View) -> Unit
) {
    val message = context.getString(messageResId)
    val actionMessage = context.getString(actionMessageId)
    val snackbar = Snackbar.make(this, message, length).apply {
        view.setBackgroundColor(context.color(R.color.nineTwoThreeMain))
    }
    snackbar.setAction(actionMessage) {
        action(this)
    }.show()
}