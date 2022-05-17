package com.grappim.weatherninetwothree.utils.extensions

import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
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

fun View.fadeVisibility(visibility: Boolean, durationLong: Long = 400) {
    val transition: Transition = Fade().apply {
        duration = durationLong
        addTarget(this@fadeVisibility)
    }
    TransitionManager.beginDelayedTransition(this.parent as ViewGroup, transition)
    this.isVisible = visibility
}