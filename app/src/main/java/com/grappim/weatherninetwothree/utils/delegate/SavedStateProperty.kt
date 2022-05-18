package com.grappim.weatherninetwothree.utils.delegate

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SavedStateProperty<T : Any>(
    private val savedStateHandle: SavedStateHandle,
    private val key: String,
    private val defaultValue: T
) : ReadWriteProperty<ViewModel, T> {
    @Suppress("RemoveExplicitTypeArguments", "UNCHECKED_CAST")
    override operator fun getValue(thisRef: ViewModel, property: KProperty<*>): T =
        (savedStateHandle.get(key) as? T) ?: defaultValue

    override operator fun setValue(
        thisRef: ViewModel,
        property: KProperty<*>,
        value: T
    ) {
        savedStateHandle[key] = value
    }
}

class SavedStatePropertyNullable<T : Any?>(
    private val savedStateHandle: SavedStateHandle,
    private val key: String,
    private val defaultValue: T? = null
) : ReadWriteProperty<ViewModel, T?> {
    @Suppress("RemoveExplicitTypeArguments", "UNCHECKED_CAST")
    override operator fun getValue(thisRef: ViewModel, property: KProperty<*>): T? =
        (savedStateHandle.get(key) as? T) ?: defaultValue

    override operator fun setValue(
        thisRef: ViewModel,
        property: KProperty<*>,
        value: T?
    ) {
        savedStateHandle[key] = value
    }
}

fun SavedStateHandle.double(
    key: String,
    defaultValue: Double? = null
): SavedStatePropertyNullable<Double?> =
    SavedStatePropertyNullable(this, key, defaultValue)

fun SavedStateHandle.boolean(
    key: String,
    defaultValue: Boolean = false
): SavedStateProperty<Boolean> =
    SavedStateProperty(this, key, defaultValue)