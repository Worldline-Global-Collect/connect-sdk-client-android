/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.configuration

import android.content.Context
import com.worldline.connect.sdk.client.android.constants.Constants

class ConnectSDKConfiguration private constructor(
    val sessionConfiguration: SessionConfiguration,
    val applicationContext: Context,
    val enableNetworkLogs: Boolean,
    val applicationId: String?,
    val ipAddress: String?,
    val preLoadImages: Boolean,
    val sdkIdentifier: String
) {

    data class Builder(
        var sessionConfiguration: SessionConfiguration,
        var applicationContext: Context,
        var enableNetworkLogs: Boolean = false,
        var applicationId: String? = null,
        var ipAddress: String? = null,
        var preLoadImages: Boolean = true,
        var sdkIdentifier: String = Constants.SDK_IDENTIFIER
    ) {
        init {
            sdkIdentifier = getValidSdkIdentifier(sdkIdentifier)
        }

        constructor(sessionConfiguration: SessionConfiguration, applicationContext: Context) : this(
            sessionConfiguration,applicationContext, false, null, null, true, Constants.SDK_IDENTIFIER
        )

        fun enableNetworkLogs(enableNetworkLogs: Boolean) =
            apply { this.enableNetworkLogs = enableNetworkLogs }

        fun applicationId(applicationId: String) =
            apply { this.applicationId = applicationId }

        fun ipAddress(ipAddress: String) =
            apply { this.ipAddress = ipAddress }

        fun preLoadImages(preLoadImages: Boolean) =
            apply { this.preLoadImages = preLoadImages }

        fun build() = ConnectSDKConfiguration(
            sessionConfiguration,
            applicationContext,
            enableNetworkLogs,
            applicationId,
            ipAddress,
            preLoadImages,
            sdkIdentifier
        )

        private fun getValidSdkIdentifier(identifier: String): String {
            val identifierParts = identifier.split("/")

            if (identifierParts.size == 2
                && identifierParts.first() == "FlutterClientSDK"
                && identifierParts.last().startsWith("v")) {
                val versionParts = identifierParts.last().replace("v", "").split(".")

                if (versionParts.size == 3 && versionParts.all { it.toIntOrNull() != null }) {
                    return identifier
                }
            }

            return Constants.SDK_IDENTIFIER
        }
    }
}
