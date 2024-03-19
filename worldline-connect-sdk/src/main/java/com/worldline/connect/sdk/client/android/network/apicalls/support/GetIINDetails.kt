/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.apicalls.support

import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.model.paymentcontext.PaymentContext
import com.worldline.connect.sdk.client.android.model.iindetails.IinDetailsResponse
import com.worldline.connect.sdk.client.android.model.iindetails.IinStatus
import com.worldline.connect.sdk.client.android.network.NetworkResponse
import io.reactivex.rxjava3.core.Observable

internal class GetIINDetails {

    operator fun invoke(
        paymentContext: PaymentContext,
        connectSDKConfiguration: ConnectSDKConfiguration,
        bin: String
    ): Observable<NetworkResponse<IinDetailsResponse>> {

        return if (bin.length < MINIMUM_BIN_LENGTH) {
            Observable.just(
                NetworkResponse.Success(
                    IinDetailsResponse(
                        IinStatus.NOT_ENOUGH_DIGITS
                    )
                )
            )
        } else {
            RemoteSupportRepository().getIINDetails(
                paymentContext, connectSDKConfiguration, bin.take(MINIMUM_BIN_LENGTH)
            ).map {
                if (it.data?.isAllowedInContext == false) {
                    it.data.status = IinStatus.EXISTING_BUT_NOT_ALLOWED
                } else {
                    it.data?.status = IinStatus.SUPPORTED
                }
                it
            }
        }
    }

    private companion object {

        const val MINIMUM_BIN_LENGTH = 6
    }
}
