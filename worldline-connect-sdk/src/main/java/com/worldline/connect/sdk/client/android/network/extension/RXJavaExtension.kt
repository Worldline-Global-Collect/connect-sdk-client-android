/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.extension

import com.google.gson.Gson
import com.worldline.connect.sdk.client.android.network.ApiError
import com.worldline.connect.sdk.client.android.network.ApiErrorResponse
import com.worldline.connect.sdk.client.android.network.Failure
import com.worldline.connect.sdk.client.android.network.NetworkResponse
import com.worldline.connect.sdk.client.android.network.Success
import com.worldline.connect.sdk.client.android.network.UnknownNetworkResponseException
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.disposables.Disposable
import retrofit2.Response

internal fun <T> Observable<NetworkResponse<T>>.subscribeAndMapNetworkResponse(
    onSuccess: Success<T>,
    onApiError: ApiError,
    onFailure: Failure
): Disposable =
    subscribe(
        { networkResponse ->
            when (networkResponse) {
                is NetworkResponse.ApiError -> networkResponse.apiErrorResponse?.let {
                    (onApiError::apiError)(
                        it
                    )
                } ?: (onFailure::failure)(UnknownNetworkResponseException)
                is NetworkResponse.Success -> networkResponse.data?.let {
                    (onSuccess::success)(
                        it
                    )
                } ?: (onFailure::failure)(UnknownNetworkResponseException)
            }
        }, {
            (onFailure::failure)(it)
        }
    )

internal fun <T> mapRetrofitResponseToNetworkResponse(response: Response<T>): ObservableSource<out NetworkResponse<T>> {
    return if (response.isSuccessful) {
        Observable.just(NetworkResponse.Success(response.body()!!))
    } else {
        val errorResponse: ApiErrorResponse = Gson().fromJson(
            response.errorBody()?.charStream(),
            ApiErrorResponse::class.java
        )
        Observable.just(NetworkResponse.ApiError(errorResponse))
    }
}
