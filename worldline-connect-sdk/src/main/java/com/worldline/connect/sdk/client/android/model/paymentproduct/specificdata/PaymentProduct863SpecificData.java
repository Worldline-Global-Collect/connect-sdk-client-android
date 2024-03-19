/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.paymentproduct.specificdata;

import java.io.Serializable;
import java.util.List;

/**
 * POJO which holds the payment product 863 specific properties.
 */
public class PaymentProduct863SpecificData implements Serializable {

	private static final long serialVersionUID = -3455606815519003280L;

	private List<String> integrationTypes;


	public List<String> getIntegrationTypes() {
		return integrationTypes;
	}
}
