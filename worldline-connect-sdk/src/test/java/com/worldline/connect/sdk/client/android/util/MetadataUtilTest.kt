/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.util

import android.util.Base64
import androidx.test.core.app.ApplicationProvider
import com.google.gson.Gson
import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.configuration.SessionConfiguration
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.nio.charset.StandardCharsets

@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner::class)
class MetadataUtilTest {

    @Test
    fun testMetadata() {
        val ipAddress = "127.0.0.1"
        val applicationId = "metaDataUtilTest"
        
        val config = ConnectSDKConfiguration.Builder(
            SessionConfiguration("clientSessionId", "customerId", "clientApiUrl", "assetUrl"),
            ApplicationProvider.getApplicationContext()
        )
            .ipAddress(ipAddress)
            .applicationId(applicationId)
            .build()

        val result = MetadataUtil.getBase64EncodedMetadata(config)

        assert(result.none { it == '=' })
        assert(result.none { it.toString() == "\n" })

        val decoded = String(Base64.decode(result, Base64.URL_SAFE), StandardCharsets.UTF_8)
        println(decoded)
        val gson = Gson()
        val parsedConfig = gson.fromJson(decoded, Map::class.java)
        println(parsedConfig)

        assert(parsedConfig["appIdentifier"] == applicationId)
        assert(parsedConfig["ipAddress"] == ipAddress)
    }
}
