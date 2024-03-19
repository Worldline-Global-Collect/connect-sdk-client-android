/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.apicalls.support

import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.model.thirdpartystatus.ThirdPartyStatus
import com.worldline.connect.sdk.client.android.network.NetworkResponse
import io.reactivex.rxjava3.core.Observable

internal class GetThirdPartyStatus {

    operator fun invoke(
        connectSDKConfiguration: ConnectSDKConfiguration,
        paymentId: String
    ): Observable<NetworkResponse<ThirdPartyStatus>> {
        return RemoteSupportRepository().getThirdPartyStatus(connectSDKConfiguration, paymentId)
            .map {
                if (it is NetworkResponse.Success) {
                    NetworkResponse.Success(it.data?.thirdPartyStatus!!)
                } else {
                    NetworkResponse.ApiError(it.apiErrorResponse)
                }
            }
    }
}
