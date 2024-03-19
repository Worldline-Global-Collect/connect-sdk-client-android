/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.paymentproduct.validation;

import java.io.Serializable;

/**
 * POJO which holds the RegularExpression data.
 * Used for validation.
 */
public class RegularExpression implements Serializable {

	private static final long serialVersionUID = -1242536946684504857L;

	private String regularExpression;

	public String getRegularExpression(){
		return regularExpression;
	}
}
