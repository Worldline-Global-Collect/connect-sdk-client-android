/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.apicalls.support

import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.model.publickey.PublicKeyResponse
import com.worldline.connect.sdk.client.android.network.NetworkResponse
import io.reactivex.rxjava3.core.Observable

internal class GetPublicKey {

    operator fun invoke(
        connectSDKConfiguration: ConnectSDKConfiguration
    ): Observable<NetworkResponse<PublicKeyResponse>>{
        return RemoteSupportRepository().getPublicKey(connectSDKConfiguration)
    }
}
