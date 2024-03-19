/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.apicalls.productsgroup

import android.util.Log
import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.network.drawable.GetDrawableFromUrl
import com.worldline.connect.sdk.client.android.network.NetworkResponse
import com.worldline.connect.sdk.client.android.network.UnknownNetworkResponseException
import com.worldline.connect.sdk.client.android.model.paymentcontext.PaymentContext
import com.worldline.connect.sdk.client.android.model.paymentproductgroup.PaymentProductGroup
import io.reactivex.rxjava3.core.Observable

internal class GetPaymentProductGroup {

    operator fun invoke(
        paymentContext: PaymentContext,
        connectSDKConfiguration: ConnectSDKConfiguration,
        paymentProductGroupId: String
    ): Observable<NetworkResponse<PaymentProductGroup>> {
        return if (connectSDKConfiguration.preLoadImages) {
            getPaymentProductGroupWithImage(
                paymentContext,
                connectSDKConfiguration,
                paymentProductGroupId
            )
        } else {
            RemotePaymentProductGroupRepository().getPaymentProductGroup(
                paymentContext,
                connectSDKConfiguration,
                paymentProductGroupId
            )
        }
    }

    private fun getPaymentProductGroupWithImage(
        paymentContext: PaymentContext,
        connectSDKConfiguration: ConnectSDKConfiguration,
        paymentProductGroupId: String
    ): Observable<NetworkResponse<PaymentProductGroup>> {
        return RemotePaymentProductGroupRepository().getPaymentProductGroup(
            paymentContext,
            connectSDKConfiguration,
            paymentProductGroupId
        ).flatMap { networkResponse ->
            if (networkResponse is NetworkResponse.Success) {
                networkResponse.data?.displayHints?.logoUrl?.let { logoUrl ->
                    GetDrawableFromUrl().invoke(connectSDKConfiguration, logoUrl).subscribe( { drawable ->
                        networkResponse.data.displayHints.logo = drawable
                    },{
                        Log.w(
                            "ConnectSDK",
                            "Drawable for paymentProductGroup: ${networkResponse.data.id} can't be loaded",
                            it
                        )
                    })
                }

                Observable.just(networkResponse.data?.let { NetworkResponse.Success(it) }
                    ?: throw UnknownNetworkResponseException)
            } else {
                Observable.just(NetworkResponse.ApiError(networkResponse.apiErrorResponse, null))
            }
        }
    }
}
