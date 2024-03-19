/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.convertamount;

import java.io.Serializable;

/**
 * POJO that contains the response for a converted amount.
 */
public class ConvertedAmountResponse implements Serializable {

	private static final long serialVersionUID = -4043745317792003304L;

	private Long convertedAmount;

	public ConvertedAmountResponse(Long convertedAmount) {
		this.convertedAmount = convertedAmount;
	}

	public Long getConvertedAmount() {
		return convertedAmount;
	}

}
