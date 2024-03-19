/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.configuration

import java.util.Locale

data class SessionConfiguration(
    val clientSessionId: String,
    val customerId: String,
    val clientApiUrl: String,
    val assetUrl: String,
) {

    internal fun getFormattedAssetUrl(): String {
        return if (assetUrl.endsWith("/")) {
            assetUrl
        } else {
            "$assetUrl/"
        }
    }

    internal fun getFormattedClientApiUrl(): String {
        var formattedClientApiUrl = clientApiUrl

        // The URL must always end with a slash
        if (!formattedClientApiUrl.endsWith("/")) {
            formattedClientApiUrl = "$formattedClientApiUrl/"
        }

        // Check if the URL is correct
        if (formattedClientApiUrl.lowercase(Locale.ROOT).endsWith(API_PATH)) {
            return formattedClientApiUrl
        }

        // Add the version if it is missing
        if (formattedClientApiUrl.lowercase(Locale.ROOT).endsWith(API_BASE)) {
            return formattedClientApiUrl + API_VERSION
        }

        // Add the complete API path to the provided URL
        return formattedClientApiUrl + API_PATH
    }

    private companion object {

        const val API_VERSION = "v1/"
        const val API_BASE = "client/"
        const val API_PATH = API_BASE + API_VERSION
    }
}
