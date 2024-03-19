/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.configuration

import com.worldline.connect.sdk.client.android.model.paymentcontext.PaymentContext

class PaymentConfiguration private constructor(
    val paymentContext: PaymentContext,
    var groupPaymentProducts: Boolean,
) {

    data class Builder(
        var paymentContext: PaymentContext,
        var groupPaymentProducts: Boolean = false,
        var isRecurring: Boolean = false
    ) {
        constructor(paymentContext: PaymentContext) : this(
            paymentContext, false, false
        )

        fun groupPaymentProducts(groupPaymentProducts: Boolean) =
            apply { this.groupPaymentProducts = groupPaymentProducts }

        fun build() = PaymentConfiguration(
            paymentContext,
            groupPaymentProducts
        )
    }
}
