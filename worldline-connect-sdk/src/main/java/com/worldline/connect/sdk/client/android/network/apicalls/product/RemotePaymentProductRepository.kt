/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.apicalls.product

import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.model.paymentcontext.PaymentContext
import com.worldline.connect.sdk.client.android.model.directoryentry.PaymentProductDirectoryResponse
import com.worldline.connect.sdk.client.android.model.paymentproduct.BasicPaymentProducts
import com.worldline.connect.sdk.client.android.model.paymentproduct.PaymentProduct
import com.worldline.connect.sdk.client.android.network.NetworkResponse
import com.worldline.connect.sdk.client.android.network.OkHttpClientBuilder
import com.worldline.connect.sdk.client.android.network.extension.mapRetrofitResponseToNetworkResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

internal class RemotePaymentProductRepository : PaymentProductRepository {

    override fun getPaymentProducts(
        paymentContext: PaymentContext,
        connectSDKConfiguration: ConnectSDKConfiguration
    ): Observable<NetworkResponse<BasicPaymentProducts>> {
        return getPaymentProductService(connectSDKConfiguration).getPaymentProducts(
            paymentContext.convertToNetworkRequestParameters()
        )
            .flatMap { response ->
                mapRetrofitResponseToNetworkResponse(response)
            }
    }

    override fun getPaymentProduct(
        paymentContext: PaymentContext,
        connectSDKConfiguration: ConnectSDKConfiguration,
        paymentProductId: String
    ): Observable<NetworkResponse<PaymentProduct>> {
        return getPaymentProductService(connectSDKConfiguration).getPaymentProduct(
            paymentProductId,
            paymentContext.convertToNetworkRequestParameters()
        )
            .flatMap { response ->
                response.body()?.let { paymentProduct ->
                    for (field in paymentProduct.paymentProductFields) {
                        field.setValidationRules()
                    }
                }
                mapRetrofitResponseToNetworkResponse(response)
            }
    }

    override fun getPaymentProductDirectory(
        paymentContext: PaymentContext,
        connectSDKConfiguration: ConnectSDKConfiguration,
        paymentProductId: String
    ): Observable<NetworkResponse<PaymentProductDirectoryResponse>> {
        val parameters: MutableMap<String, String> = HashMap()
        parameters["countryCode"] = paymentContext.countryCode
        parameters["currencyCode"] = paymentContext.amountOfMoney.currencyCode

        return getPaymentProductService(connectSDKConfiguration).getPaymentProductDirectory(
            paymentProductId,
            parameters
        )
            .flatMap { response ->
                mapRetrofitResponseToNetworkResponse(response)
            }
    }

    private companion object {

        fun getPaymentProductService(connectSdkConfiguration: ConnectSDKConfiguration): PaymentProductService =
            Retrofit.Builder()
                .baseUrl(
                    connectSdkConfiguration.sessionConfiguration.getFormattedClientApiUrl() +
                            "${connectSdkConfiguration.sessionConfiguration.customerId}/"
                )
                .client(
                    OkHttpClientBuilder.okHttpClient(
                        connectSdkConfiguration
                    )
                )
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PaymentProductService::class.java)
    }
}
