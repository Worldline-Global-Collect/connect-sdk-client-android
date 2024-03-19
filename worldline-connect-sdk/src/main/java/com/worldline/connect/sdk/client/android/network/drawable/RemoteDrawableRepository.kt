/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.drawable

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.network.OkHttpClientBuilder
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

internal class RemoteDrawableRepository : DrawableRepository {

    override fun getDrawableFromUrl(
        connectSDKConfiguration: ConnectSDKConfiguration,
        drawableUrl: String
    ): Observable<Drawable> {
        return getDrawableService(connectSDKConfiguration).getDrawableFromUrl(drawableUrl)
            .flatMap { response ->
                mapResponseToDrawable(connectSDKConfiguration.applicationContext, response)
            }
    }

    private fun mapResponseToDrawable(
        context: Context,
        response: ResponseBody
    ): ObservableSource<out Drawable> {
        val bitmap = BitmapFactory.decodeStream(response.byteStream())
        return Observable.just(BitmapDrawable(context.resources, bitmap))
    }

    private companion object {

        fun getDrawableService(connectSdkConfiguration: ConnectSDKConfiguration): DrawableService =
            Retrofit.Builder()
                .baseUrl(connectSdkConfiguration.sessionConfiguration.getFormattedAssetUrl())
                .client(
                    OkHttpClientBuilder.okHttpClient(
                        connectSdkConfiguration
                    )
                )
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(DrawableService::class.java)
    }
}
