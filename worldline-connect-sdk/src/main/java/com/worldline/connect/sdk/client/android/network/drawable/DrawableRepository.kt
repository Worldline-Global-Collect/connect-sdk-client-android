/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.drawable

import android.graphics.drawable.Drawable
import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import io.reactivex.rxjava3.core.Observable

internal interface DrawableRepository {

    fun getDrawableFromUrl(connectSDKConfiguration: ConnectSDKConfiguration, drawableUrl: String): Observable<Drawable>
}
