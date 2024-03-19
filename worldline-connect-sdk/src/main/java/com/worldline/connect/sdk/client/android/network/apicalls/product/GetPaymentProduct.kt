/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.apicalls.product

import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.network.drawable.GetDrawableFromUrl
import com.worldline.connect.sdk.client.android.model.paymentcontext.PaymentContext
import com.worldline.connect.sdk.client.android.model.paymentproduct.PaymentProduct
import com.worldline.connect.sdk.client.android.network.NetworkResponse
import com.worldline.connect.sdk.client.android.network.UnknownNetworkResponseException
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.Observables

internal class GetPaymentProduct {

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
        return Observable.create {
            if (!paymentProduct.displayHints.logoUrl.isNullOrBlank()) {
                GetDrawableFromUrl().invoke(
                    connectSDKConfiguration,
                    paymentProduct.displayHints.logoUrl
                ).subscribe({ drawable ->
                    paymentProduct.displayHints.logo = drawable
                    it.onComplete()
                }, {})
            } else it.onComplete()
        }
    }

    private fun getTooltipImages(
        paymentProduct: PaymentProduct,
        connectSDKConfiguration: ConnectSDKConfiguration,
    ): Observable<Unit> {
        return Observable.create {
            var count = 0

            if(paymentProduct.paymentProductFields.isNullOrEmpty()) it.onComplete()

            paymentProduct.paymentProductFields.forEach { paymentProductField ->
                if (!paymentProductField.displayHints?.tooltip?.imageURL.isNullOrBlank()) {
                    GetDrawableFromUrl().invoke(
                        connectSDKConfiguration,
                        paymentProductField.displayHints.tooltip.imageURL
                    ).doFinally {
                        count++
                        if (count == paymentProduct.paymentProductFields.count()) it.onComplete()
                    }
                        .subscribe({ drawable ->
                            paymentProductField.displayHints.tooltip.imageDrawable = drawable },
                            {}
                        )
                    return@forEach
                }
                count++
                if (count == paymentProduct.paymentProductFields.count()) it.onComplete()
            }
        }
    }
}
