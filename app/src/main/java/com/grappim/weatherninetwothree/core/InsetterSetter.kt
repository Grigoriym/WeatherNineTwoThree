package com.grappim.weatherninetwothree.core

import android.view.View
import dev.chrisbanes.insetter.applyInsetter

interface InsetterSetter {

    fun setInsetterForSystemBars(
        view: View
    )

    fun setInsetterForNavigationBarsAndIme(
        view: View,
        isMargin: Boolean
    )
}

class InsetterSetterDelegate : InsetterSetter {

    override fun setInsetterForSystemBars(view: View) {
        view.applyInsetter {
            type(statusBars = true) {
                margin()
            }
        }
    }

    override fun setInsetterForNavigationBarsAndIme(view: View, isMargin: Boolean) {
        view.applyInsetter {
            type(
                navigationBars = true,
                ime = true
            ) {
                if (isMargin) {
                    margin()
                } else {
                    padding()
                }
            }
        }
    }

}