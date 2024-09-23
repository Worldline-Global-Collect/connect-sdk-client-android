/*
 * Copyright (c) 2024 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.util

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.os.Build
import android.util.Base64
import android.util.DisplayMetrics
import android.view.WindowManager
import com.google.gson.Gson
import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.constants.Constants
import java.nio.charset.StandardCharsets

internal object MetadataUtil {
    private const val metadataPlatformIdentifier = "platformIdentifier"
    private const val metadataAppIdentifier = "appIdentifier"
    private const val metadataSDKIdentifier = "sdkIdentifier"
    private const val metadataSDKCreator = "sdkCreator"
    private const val metadataScreenSize = "screenSize"
    private const val metadataDeviceBrand = "deviceBrand"
    private const val metadataDeviceType = "deviceType"
    private const val metadataIPAddress = "ipAddress"

    private val gson = Gson()

    internal fun getBase64EncodedMetadata(configuration: ConnectSDKConfiguration) : String {
        val jsonMetadata = gson.toJson(
            configuration.applicationContext.getMetadata(
                configuration.applicationId,
                configuration.ipAddress,
                configuration.sdkIdentifier
            )
        )
        return jsonMetadata.encodeToByteArray().base64UrlEncode()
    }
    private fun Context.getMetadata(
        appIdentifier: String?,
        ipAddress: String?,
        sdkIdentifier: String
    ) : Map<String, String> {
        // appId is 'unknown' if appIdentifier is null or empty
        val appId = appIdentifier?.ifEmpty { null } ?: "unknown"
        val screenSize = getDefaultDisplayMetrics()

        val metaData = mutableMapOf<String, String>(
            metadataPlatformIdentifier to "Android/${Build.VERSION.RELEASE}",
            metadataAppIdentifier to appId,
            metadataSDKIdentifier to sdkIdentifier,
            metadataSDKCreator to Constants.SDK_CREATOR,
            metadataScreenSize to "${screenSize.heightPixels}x${screenSize.widthPixels}",
            metadataDeviceBrand to Build.MANUFACTURER,
            metadataDeviceType to Build.MODEL
        )

        // Only add IPAddress if it is not null or empty
        if (ipAddress?.isNotEmpty() == true) {
            metaData[metadataIPAddress] = ipAddress
        }
        return metaData.toMap()
    }

    private fun Context.getDefaultDisplayMetrics() : DisplayMetrics {
        val wm =  getSystemService(WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        // Uses deprecated methods for older Android versions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val bounds = wm.currentWindowMetrics.bounds
            metrics.widthPixels = bounds.width()
            metrics.heightPixels = bounds.height()
        } else {
            wm.defaultDisplay.getMetrics(metrics)
        }
        return metrics
    }

    private fun ByteArray.base64UrlEncode() : String {
        val encoded = String(Base64.encode(this, Base64.URL_SAFE), StandardCharsets.UTF_8)
        return encoded.let {
            var t = it.replace("\n", "")
            t = t.replace("=", "")
            t
        }
    }
}
