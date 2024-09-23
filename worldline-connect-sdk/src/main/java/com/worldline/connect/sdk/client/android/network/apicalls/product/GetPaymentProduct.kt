/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.apicalls.product

import android.util.Log
import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.network.drawable.GetDrawableFromUrl
import com.worldline.connect.sdk.client.android.model.paymentcontext.PaymentContext
import com.worldline.connect.sdk.client.android.model.paymentproduct.PaymentProduct
import com.worldline.connect.sdk.client.android.network.NetworkResponse
import com.worldline.connect.sdk.client.android.network.UnknownNetworkResponseException
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.Observables

internal class GetPaymentProduct {

    private val tag = "GetPaymentProduct"

    operator fun invoke(
        paymentContext: PaymentContext,
        connectSDKConfiguration: ConnectSDKConfiguration,
        paymentProductId: String
    ): Observable<NetworkResponse<PaymentProduct>> {
        return if (connectSDKConfiguration.preLoadImages) {
            getPaymentProductWithImages(paymentContext, connectSDKConfiguration, paymentProductId)
        } else {
            RemotePaymentProductRepository().getPaymentProduct(
                paymentContext,
                connectSDKConfiguration,
                paymentProductId
            )
        }
    }

    private fun getPaymentProductWithImages(
        paymentContext: PaymentContext,
        connectSDKConfiguration: ConnectSDKConfiguration,
        paymentProductId: String
    ): Observable<NetworkResponse<PaymentProduct>> {
        return RemotePaymentProductRepository().getPaymentProduct(
            paymentContext,
            connectSDKConfiguration,
            paymentProductId
        ).flatMap { networkResponse ->
            if (networkResponse is NetworkResponse.Success) {
                Observable.just(networkResponse.data?.let {
                    NetworkResponse.Success(it)
                }
                    ?: throw UnknownNetworkResponseException)
                    .delaySubscription(
                        Observables.combineLatest(
                            getLogo(networkResponse.data, connectSDKConfiguration),
                            getTooltipImages(networkResponse.data, connectSDKConfiguration)
                        )
                    )
            } else {
                Observable.just(NetworkResponse.ApiError(networkResponse.apiErrorResponse, null))
            }
        }
    }

    private fun getLogo(
        paymentProduct: PaymentProduct,
        connectSDKConfiguration: ConnectSDKConfiguration,
    ): Observable<Unit> {
        return Observable.create { observable ->
            if (!paymentProduct.displayHints.logoUrl.isNullOrBlank()) {
                GetDrawableFromUrl()(
                    connectSDKConfiguration,
                    paymentProduct.displayHints.logoUrl
                ).doFinally {
                    observable.onComplete()
                }.subscribe(DrawableObserver({
                    paymentProduct.displayHints.logo = it
                }) {
                    Log.w(tag, "Drawable for logo of paymentProduct: ${paymentProduct.id} cannot be loaded")
                })

            } else observable.onComplete()
        }
    }

    private fun getTooltipImages(
        paymentProduct: PaymentProduct,
        connectSDKConfiguration: ConnectSDKConfiguration,
    ): Observable<Unit> {
        return Observable.create { observable ->
            var count = 0

            if(paymentProduct.paymentProductFields.isNullOrEmpty()) observable.onComplete()

            paymentProduct.paymentProductFields.forEach { paymentProductField ->
                if (!paymentProductField.displayHints?.tooltip?.imageURL.isNullOrBlank()) {
                    GetDrawableFromUrl()(
                        connectSDKConfiguration,
                        paymentProductField.displayHints.tooltip.imageURL
                    ).doFinally {
                        count++
                        if (count == paymentProduct.paymentProductFields.count()) observable.onComplete()
                    }.subscribe(DrawableObserver({
                        paymentProductField.displayHints.tooltip.imageDrawable = it
                    }) {
                        Log.w(tag, "Drawable for tooltip of paymentProduct: ${paymentProduct.id} cannot be loaded")
                    })
                    return@forEach
                }
                count++
                if (count == paymentProduct.paymentProductFields.count()) observable.onComplete()
            }
        }
    }
}
