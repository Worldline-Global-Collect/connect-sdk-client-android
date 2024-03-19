/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.apicalls.product

import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.model.paymentcontext.PaymentContext
import com.worldline.connect.sdk.client.android.model.directoryentry.PaymentProductDirectoryResponse
import com.worldline.connect.sdk.client.android.network.NetworkResponse
import io.reactivex.rxjava3.core.Observable

internal class GetPaymentProductDirectory {

    operator fun invoke(
        paymentContext: PaymentContext,
        connectSDKConfiguration: ConnectSDKConfiguration,
        paymentProductId: String
    ): Observable<NetworkResponse<PaymentProductDirectoryResponse>> {
        return RemotePaymentProductRepository()
                .getPaymentProductDirectory(paymentContext, connectSDKConfiguration, paymentProductId)
    }
}
