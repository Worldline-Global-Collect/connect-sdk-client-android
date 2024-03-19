/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.drawable

import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

internal interface DrawableService {

    @GET("{drawableUrl}")
    fun getDrawableFromUrl(@Path("drawableUrl",encoded = true) drawableUrl: String): Observable<ResponseBody>
}
