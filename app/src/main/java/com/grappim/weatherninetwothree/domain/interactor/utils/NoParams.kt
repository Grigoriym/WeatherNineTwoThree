package com.grappim.weatherninetwothree.domain.interactor.utils

/**
 * Use this type when the Use Case is should not receive
 * any arguments. This is more readable way.
 */
typealias NoParams = Any?

/**
 * Use this function to execute Use Case without parameters.
 */
fun withoutParams(): NoParams = null