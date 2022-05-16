package com.grappim.weatherninetwothree.utils.delegate

import android.app.Activity
import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <reified T> Fragment.lazyArg(
    key: String,
    noinline builder: ((Any?) -> T)? = null
): Lazy<T> =
    lazy(mode = LazyThreadSafetyMode.NONE) {
        val value = arguments?.get(key)
        return@lazy if (builder != null) {
            builder.invoke(value)
        } else {
            value as T
        }
    }

inline fun <reified T> Activity.lazyArg(
    key: String,
    noinline builder: ((Any?) -> T)? = null
): Lazy<T> =
    lazy(mode = LazyThreadSafetyMode.NONE) {
        val value = intent?.extras?.get(key)
        return@lazy if (builder != null) {
            builder.invoke(value)
        } else {
            value as T
        }
    }

inline fun <reified T : Any> Fragment.lazyMutableArg(
    key: String
) = FragmentMutableArgumentDelegate<T>(key)

class FragmentMutableArgumentDelegate<T : Any>(
    private val key: String
) : ReadWriteProperty<Fragment, T> {
    private var value: T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        value?.let { return it }
        value = thisRef.requireArguments().get(key) as T
        return value as T
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        this.value = value
    }
}