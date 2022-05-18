package com.grappim.weatherninetwothree.utils.extensions

import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.google.android.material.snackbar.Snackbar
import com.grappim.weatherninetwothree.R

fun View.showSnackbar(
    @StringRes messageResId: Int = R.string.ok,
    length: Int = Snackbar.LENGTH_SHORT,
    @StringRes actionMessageId: Int,
    action: ((View) -> Unit)? = null
) {
    showSnackbar(
        msg = context.getString(messageResId),
        length = length,
        actionMessageId = actionMessageId,
        action = action
    )
}

fun View.showSnackbar(
    msg: String,
    length: Int = Snackbar.LENGTH_SHORT,
    @StringRes actionMessageId: Int = R.string.ok,
    action: ((View) -> Unit)? = null
) {
    val actionMessage = context.getString(actionMessageId)
    val snackbar = Snackbar.make(this, msg, length).apply {
        view.setBackgroundColor(context.color(R.color.nineTwoThreeMain))
    }
    if (action != null) {
        snackbar.setAction(actionMessage) {
            action(this)
        }
    }
    snackbar.show()
}

 fun View.isKeyboardVisible(): Boolean =
    ViewCompat
        .getRootWindowInsets(this)
        ?.isVisible(WindowInsetsCompat.Type.ime()) ?: false

fun View.fadeVisibility(visibility: Boolean, durationLong: Long = 400) {
    val transition: Transition = Fade().apply {
        duration = durationLong
        addTarget(this@fadeVisibility)
    }
    TransitionManager.beginDelayedTransition(this.parent as ViewGroup, transition)
    this.isVisible = visibility
}