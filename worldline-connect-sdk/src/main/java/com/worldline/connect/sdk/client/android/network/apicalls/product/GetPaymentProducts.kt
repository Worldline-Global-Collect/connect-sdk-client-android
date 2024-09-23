/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.apicalls.product

import android.util.Log
import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.network.drawable.GetDrawableFromUrl
import com.worldline.connect.sdk.client.android.model.paymentcontext.PaymentContext
import com.worldline.connect.sdk.client.android.model.paymentproduct.BasicPaymentProduct
import com.worldline.connect.sdk.client.android.model.paymentproduct.BasicPaymentProducts
import com.worldline.connect.sdk.client.android.network.NetworkResponse
import com.worldline.connect.sdk.client.android.network.UnknownNetworkResponseException
import io.reactivex.rxjava3.core.Observable

internal class GetPaymentProducts {

    private val tag = "GetPaymentProducts"

    operator fun invoke(
        paymentContext: PaymentContext,
        connectSDKConfiguration: ConnectSDKConfiguration
    ): Observable<NetworkResponse<BasicPaymentProducts>> {
        return if (connectSDKConfiguration.preLoadImages) {
            getPaymentProductsWithLogos(paymentContext, connectSDKConfiguration)
        } else {
            RemotePaymentProductRepository().getPaymentProducts(
                paymentContext,
                connectSDKConfiguration
            )
        }
    }

    private fun getPaymentProductsWithLogos(
        paymentContext: PaymentContext,
        connectSDKConfiguration: ConnectSDKConfiguration
    ): Observable<NetworkResponse<BasicPaymentProducts>> {
        return RemotePaymentProductRepository().getPaymentProducts(
            paymentContext,
            connectSDKConfiguration
        ).flatMap { networkResponse ->
            if (networkResponse is NetworkResponse.Success) {
                Observable.just(networkResponse.data?.let {
                    NetworkResponse.Success(it)
                }
                    ?: throw UnknownNetworkResponseException)
                    .delaySubscription(
                        getLogos(networkResponse.data.basicPaymentProducts, connectSDKConfiguration)
                    )
            } else {
                Observable.just(NetworkResponse.ApiError(networkResponse.apiErrorResponse, null))
            }
        }
    }

    private fun getLogos(
        paymentProducts: List<BasicPaymentProduct>,
        connectSDKConfiguration: ConnectSDKConfiguration,
    ): Observable<Unit> {
        return Observable.create { observable ->
            var count = 0
            paymentProducts.forEach { basicPaymentProduct ->
                if (!basicPaymentProduct.displayHints.logoUrl.isNullOrBlank()) {
                    GetDrawableFromUrl()(
                        connectSDKConfiguration,
                        basicPaymentProduct.displayHints.logoUrl
                    ).doFinally {
                        count++
                        if (count == paymentProducts.count()) observable.onComplete()
                    }.subscribe(DrawableObserver({
                        basicPaymentProduct.displayHints.logo = it
                    }) {
                        Log.w(tag, "Drawable for logo of paymentProduct: ${basicPaymentProduct.id} cannot be loaded")
                    })
                } else {
                    count++
                    if (count == paymentProducts.count()) observable.onComplete()
                }
            }
        }
    }
}
