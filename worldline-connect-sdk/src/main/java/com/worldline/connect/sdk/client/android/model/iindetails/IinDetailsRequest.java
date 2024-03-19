/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.iindetails;

import com.google.gson.annotations.SerializedName;
import com.worldline.connect.sdk.client.android.model.paymentcontext.PaymentContext;

import java.io.Serializable;

/**
 * POJO that contains the request for IIN lookup.
 */
public class IinDetailsRequest implements Serializable {

	private static final long serialVersionUID = 8401271765455867950L;

	@SerializedName("bin")
	private String ccPartial;

	private PaymentContext paymentContext;

	public IinDetailsRequest(String ccPartial) {
		this.ccPartial = ccPartial;
	}

	public IinDetailsRequest(String ccPartial, PaymentContext paymentContext) {
		this.ccPartial = ccPartial;
		this.paymentContext = paymentContext;
	}

	public String getCcPartial() {
		return ccPartial;
	}

	public PaymentContext getPaymentContext () {
		return paymentContext;
	}

}
