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
import com.worldline.connect.sdk.client.android.model.paymentproductgroup.BasicPaymentProductGroups
import io.reactivex.rxjava3.core.Observable

internal class GetPaymentProductGroups {

    operator fun invoke(
        paymentContext: PaymentContext,
        connectSDKConfiguration: ConnectSDKConfiguration
    ): Observable<NetworkResponse<BasicPaymentProductGroups>> {
        return if (connectSDKConfiguration.preLoadImages) {
            getPaymentProductGroupsWithImages(paymentContext, connectSDKConfiguration)
        } else {
            RemotePaymentProductGroupRepository().getPaymentProductGroups(
                paymentContext,
                connectSDKConfiguration
            )
        }
    }

    private fun getPaymentProductGroupsWithImages(
        paymentContext: PaymentContext,
        connectSDKConfiguration: ConnectSDKConfiguration
    ): Observable<NetworkResponse<BasicPaymentProductGroups>> {
        return RemotePaymentProductGroupRepository().getPaymentProductGroups(
            paymentContext,
            connectSDKConfiguration
        ).flatMap { networkResponse ->
            if (networkResponse is NetworkResponse.Success) {
                networkResponse.data?.basicPaymentProductGroups?.forEach { basicPaymentProductGroup ->
                    GetDrawableFromUrl().invoke(
                        connectSDKConfiguration,
                        basicPaymentProductGroup.displayHints.logoUrl
                    ).subscribe ({
                        basicPaymentProductGroup.displayHints.logo = it
                    },{
                        Log.w(
                            "ConnectSDK",
                            "Drawable for paymentProductGroup: ${basicPaymentProductGroup.id} can't be loaded",
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
