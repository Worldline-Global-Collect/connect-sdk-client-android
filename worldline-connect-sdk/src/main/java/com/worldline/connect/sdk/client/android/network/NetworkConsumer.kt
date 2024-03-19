/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network

@FunctionalInterface
fun interface Success<X> {
    fun success(it: X)
}

@FunctionalInterface
fun interface ApiError {
    fun apiError(it: ApiErrorResponse)
}

@FunctionalInterface
fun interface Failure {
    fun failure(it: Throwable)
}
