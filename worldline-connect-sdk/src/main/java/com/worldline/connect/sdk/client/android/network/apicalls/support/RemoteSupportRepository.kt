/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.apicalls.support

import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.model.convertamount.ConvertedAmountResponse
import com.worldline.connect.sdk.client.android.model.paymentcontext.PaymentContext
import com.worldline.connect.sdk.client.android.model.publickey.PublicKeyResponse
import com.worldline.connect.sdk.client.android.model.thirdpartystatus.ThirdPartyStatusResponse
import com.worldline.connect.sdk.client.android.model.iindetails.IinDetailsRequest
import com.worldline.connect.sdk.client.android.model.iindetails.IinDetailsResponse
import com.worldline.connect.sdk.client.android.model.privacypolicy.PrivacyPolicyResponse
import com.worldline.connect.sdk.client.android.network.NetworkResponse
import com.worldline.connect.sdk.client.android.network.OkHttpClientBuilder
import com.worldline.connect.sdk.client.android.network.extension.mapRetrofitResponseToNetworkResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

internal class RemoteSupportRepository : SupportRepository {

    override fun getPrivacyPolicy(
        connectSDKConfiguration: ConnectSDKConfiguration,
        paymentProductId: String?,
        locale: String?
    ): Observable<NetworkResponse<PrivacyPolicyResponse>> {
        return getSupportService(
            connectSDKConfiguration
        ).getPrivacyPolicy(paymentProductId, locale)
            .flatMap { response ->
                mapRetrofitResponseToNetworkResponse(response)
            }
    }

    override fun getIINDetails(
        paymentContext: PaymentContext,
        connectSDKConfiguration: ConnectSDKConfiguration,
        bin: String
    ): Observable<NetworkResponse<IinDetailsResponse>> {
        return getSupportService(
            connectSDKConfiguration
        ).getIINDetails(
            IinDetailsRequest(
                bin,
                paymentContext
            )
        )
            .flatMap { response ->
                mapRetrofitResponseToNetworkResponse(response)
            }
    }

    override fun getPublicKey(
        connectSDKConfiguration: ConnectSDKConfiguration
    ): Observable<NetworkResponse<PublicKeyResponse>> {
            return getSupportService(connectSDKConfiguration).getPublicKey()
                .flatMap { response ->
                    mapRetrofitResponseToNetworkResponse(response)
                }
    }

    override fun getThirdPartyStatus(
        connectSDKConfiguration: ConnectSDKConfiguration,
        paymentId: String
    ): Observable<NetworkResponse<ThirdPartyStatusResponse>> {
        return getSupportService(connectSDKConfiguration).getThirdPartyStatus(paymentId)
            .flatMap { response ->
                mapRetrofitResponseToNetworkResponse(response)
            }
    }

    override fun convertAmount(
        connectSDKConfiguration: ConnectSDKConfiguration,
        source: String,
        target: String,
        amount: Long
    ): Observable<NetworkResponse<ConvertedAmountResponse>> {
        val parameters = HashMap<String, String>()
        parameters["source"] = source
        parameters["target"] = target
        parameters["amount"] = amount.toString()

        return getSupportService(connectSDKConfiguration).convertAmount(parameters)
            .flatMap { response ->
                mapRetrofitResponseToNetworkResponse(response)
            }
    }

    private companion object {

        fun getSupportService(connectSdkConfiguration: ConnectSDKConfiguration): SupportService =
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
                .create(SupportService::class.java)
    }
}
