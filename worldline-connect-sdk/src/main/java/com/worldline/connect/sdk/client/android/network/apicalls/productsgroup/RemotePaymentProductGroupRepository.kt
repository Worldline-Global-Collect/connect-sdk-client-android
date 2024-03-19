/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.apicalls.productsgroup

import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.network.NetworkResponse
import com.worldline.connect.sdk.client.android.network.OkHttpClientBuilder
import com.worldline.connect.sdk.client.android.network.extension.mapRetrofitResponseToNetworkResponse
import com.worldline.connect.sdk.client.android.model.paymentcontext.PaymentContext
import com.worldline.connect.sdk.client.android.model.paymentproductgroup.BasicPaymentProductGroups
import com.worldline.connect.sdk.client.android.model.paymentproductgroup.PaymentProductGroup
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

internal class RemotePaymentProductGroupRepository : PaymentProductGroupRepository {

    override fun getPaymentProductGroups(
        paymentContext: PaymentContext,
        connectSDKConfiguration: ConnectSDKConfiguration
    ): Observable<NetworkResponse<BasicPaymentProductGroups>> {
        return getPaymentProductGroupService(connectSDKConfiguration).getPaymentProductGroups(
            paymentContext.convertToNetworkRequestParameters()
        )
            .flatMap { response ->
                mapRetrofitResponseToNetworkResponse(response)
            }
    }

    override fun getPaymentProductGroup(
        paymentContext: PaymentContext,
        connectSDKConfiguration: ConnectSDKConfiguration,
        paymentProductGroupId: String
    ): Observable<NetworkResponse<PaymentProductGroup>> {
        return getPaymentProductGroupService(connectSDKConfiguration).getPaymentProductGroup(
            paymentProductGroupId,
            paymentContext.convertToNetworkRequestParameters()
        )
            .flatMap { response ->
                response.body()?.let { paymentProductGroup ->
                    for (field in paymentProductGroup.paymentProductFields) {
                        field.setValidationRules()
                    }
                }
                mapRetrofitResponseToNetworkResponse(response)
            }
    }

    private companion object {

        fun getPaymentProductGroupService(
            connectSdkConfiguration: ConnectSDKConfiguration
        ): PaymentProductGroupService =
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
                .create(PaymentProductGroupService::class.java)
    }
}
