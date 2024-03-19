/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.validation;


import com.worldline.connect.sdk.client.android.model.paymentrequest.PaymentRequest;

/**
 * Interface for ValidationRule.
 */
public interface ValidationRule {

	boolean validate(PaymentRequest paymentRequest, String fieldId);
}
