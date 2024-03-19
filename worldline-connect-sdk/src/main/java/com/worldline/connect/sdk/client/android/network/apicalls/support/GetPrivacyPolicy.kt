/*
 * Copyright (c) 2024 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.apicalls.support

import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.model.privacypolicy.PrivacyPolicyResponse
import com.worldline.connect.sdk.client.android.network.NetworkResponse
import io.reactivex.rxjava3.core.Observable

internal class GetPrivacyPolicy {

    operator fun invoke(
        connectSDKConfiguration: ConnectSDKConfiguration,
        paymentProductId: String?,
        locale: String?
    ): Observable<NetworkResponse<PrivacyPolicyResponse>> {
        return RemoteSupportRepository().getPrivacyPolicy(
            connectSDKConfiguration,
            paymentProductId,
            locale
        )
    }
}
