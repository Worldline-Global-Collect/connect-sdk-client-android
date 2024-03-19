/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network

data class ApiErrorResponse(
    val errorId: String,
    val errors: List<Error>
)

data class Error(
    val category: String,
    val code: String,
    val httpStatusCode: Int,
    val id: String,
    val message: String,
    val propertyName: String?,
    val requestId: String?
)
