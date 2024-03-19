/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.paymentitem;

import com.worldline.connect.sdk.client.android.model.paymentproduct.field.PaymentProductField;

import java.io.Serializable;
import java.util.List;

/**
 * POJO that represents a PaymentItem.
 */
public interface PaymentItem extends BasicPaymentItem, Serializable {

    List<PaymentProductField> getPaymentProductFields();
}
