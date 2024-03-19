/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network

import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.util.MetadataUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

internal object OkHttpClientBuilder {

    private fun getHeaderInterceptor(
        clientSessionId: String,
        connectSDKConfiguration: ConnectSDKConfiguration
    ) = Interceptor { chain ->
        val request: Request = chain.request().newBuilder()
            .header("Authorization", "GCS v1Client:$clientSessionId")
            .header(
                "X-GCS-ClientMetaInfo",
                MetadataUtil.getBase64EncodedMetadata(
                    connectSDKConfiguration
                )
            )
            .build()

        chain.proceed(request)
    }

    fun okHttpClient(connectSDKConfiguration: ConnectSDKConfiguration) =
        if (connectSDKConfiguration.enableNetworkLogs) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(
                    getHeaderInterceptor(
                        connectSDKConfiguration.sessionConfiguration.clientSessionId,
                        connectSDKConfiguration
                    )
                )
                .build()
        } else {
            OkHttpClient
                .Builder()
                .addInterceptor(
                    getHeaderInterceptor(
                        connectSDKConfiguration.sessionConfiguration.clientSessionId,
                        connectSDKConfiguration
                    )
                )
                .build()
        }
}
