/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.apicalls.support

import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.model.convertamount.ConvertedAmountResponse
import com.worldline.connect.sdk.client.android.model.paymentcontext.PaymentContext
import com.worldline.connect.sdk.client.android.model.publickey.PublicKeyResponse
import com.worldline.connect.sdk.client.android.model.thirdpartystatus.ThirdPartyStatusResponse
import com.worldline.connect.sdk.client.android.model.iindetails.IinDetailsResponse
import com.worldline.connect.sdk.client.android.model.privacypolicy.PrivacyPolicyResponse
import com.worldline.connect.sdk.client.android.network.NetworkResponse
import io.reactivex.rxjava3.core.Observable

internal interface SupportRepository {

    fun getPrivacyPolicy(
        connectSDKConfiguration: ConnectSDKConfiguration,
        paymentProductId: String?,
        locale: String?
    ): Observable<NetworkResponse<PrivacyPolicyResponse>>

    fun getIINDetails(
        paymentContext: PaymentContext,
        connectSDKConfiguration: ConnectSDKConfiguration,
        bin: String
    ): Observable<NetworkResponse<IinDetailsResponse>>

    fun getPublicKey(connectSDKConfiguration: ConnectSDKConfiguration): Observable<NetworkResponse<PublicKeyResponse>>

    fun getThirdPartyStatus(
        connectSDKConfiguration: ConnectSDKConfiguration,
        paymentId: String
    ): Observable<NetworkResponse<ThirdPartyStatusResponse>>

    fun convertAmount(
        connectSDKConfiguration: ConnectSDKConfiguration,
        source: String,
        target: String,
        amount: Long
    ): Observable<NetworkResponse<ConvertedAmountResponse>>
}
