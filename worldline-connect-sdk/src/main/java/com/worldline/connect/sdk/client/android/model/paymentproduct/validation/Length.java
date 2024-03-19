/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.paymentproduct.validation;

import java.io.Serializable;

/**
 * POJO which holds the Length data.
 * Used for validation.
 */
public class Length implements Serializable {

	private static final long serialVersionUID = -8127911803708372125L;

	private Integer minLength;
	private Integer maxLength;

	public Integer getMinLength(){
		return minLength;
	}

	public Integer getMaxLength(){
		return maxLength;
	}
}
