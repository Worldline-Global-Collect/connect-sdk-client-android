/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.paymentrequest

/**
 * Contains all encrypted payment request data needed for doing a payment.
 */
data class EncryptedPaymentRequest(
    val encryptedFields: String,
    val encodedClientMetaInfo: String,
)
