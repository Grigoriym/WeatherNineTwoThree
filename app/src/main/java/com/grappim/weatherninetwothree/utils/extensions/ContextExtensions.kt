package com.grappim.weatherninetwothree.utils.extensions

import android.content.Context
import android.content.pm.PackageManager
import android.util.DisplayMetrics
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.dpToPx(dp: Int): Int =
    dp * (this.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

fun Context.pxToDp(px: Int): Int =
    px / (this.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

fun Context.isPermissionGranted(permission: String): Boolean =
    ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED

fun Context.isPermissionNotGranted(permission: String): Boolean =
    ContextCompat.checkSelfPermission(
        this,
        permission
    ) != PackageManager.PERMISSION_DENIED

fun Context.color(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)