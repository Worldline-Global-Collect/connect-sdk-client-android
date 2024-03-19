/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android;

import com.worldline.connect.sdk.client.android.masking.MaskTest;
import com.worldline.connect.sdk.client.android.model.iindetails.IinDetailsResponseTest;
import com.worldline.connect.sdk.client.android.util.MetadataUtilTest;
import com.worldline.connect.sdk.client.android.model.validation.ValidationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * Android InstrumentationTestRunner used for running all the tests in the SDK
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({IinDetailsResponseTest.class, MaskTest.class, ValidationTest.class, MetadataUtilTest.class})
public class SDKTest {

}
