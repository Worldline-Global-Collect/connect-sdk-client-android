/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.apicalls.support

import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.network.NetworkResponse
import io.reactivex.rxjava3.core.Observable

internal class ConvertAmount {

    operator fun invoke(
        connectSDKConfiguration: ConnectSDKConfiguration,
        source: String,
        target: String,
        amount: Long
    ): Observable<NetworkResponse<Long>> {
        return RemoteSupportRepository().convertAmount(connectSDKConfiguration, source, target, amount)
            .map {
                if (it is NetworkResponse.Success) {
                    NetworkResponse.Success(it.data?.convertedAmount!!)
                } else {
                    NetworkResponse.ApiError(it.apiErrorResponse)
                }
            }
    }
}

