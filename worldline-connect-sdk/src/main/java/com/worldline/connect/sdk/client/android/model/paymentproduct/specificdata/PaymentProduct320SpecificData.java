/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.paymentproduct.specificdata;

import java.io.Serializable;
import java.util.List;

/**
 * POJO which holds the payment product 320 specific properties.
 */
public class PaymentProduct320SpecificData implements Serializable {

	private static final long serialVersionUID = 8538500042642795722L;

	private String gateway;
	private List<String> networks;


	public String getGateway() {
		return gateway;
	}

	public List<String> getNetworks() {
		return networks;
	}
}
