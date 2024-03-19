/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.paymentproduct.validation;

import java.io.Serializable;

/**
 * POJO which holds the Range data.
 * Used for validation.
 */
public class Range implements Serializable {

	private static final long serialVersionUID = 4659640500627126711L;

	private Integer minValue;
	private Integer maxValue;

	public Integer getMinValue(){
		return minValue;
	}

	public Integer getMaxValue(){
		return maxValue;
	}
}
