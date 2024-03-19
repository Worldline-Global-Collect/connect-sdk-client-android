/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.paymentproduct.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * POJO which holds the FixedList data.
 * Used for validation.
 */
public class FixedList implements Serializable {

	private static final long serialVersionUID = -7191166722186646029L;

	private List<String> allowedValues = new ArrayList<>();

	public List<String> getAllowedValues(){
		return allowedValues;
	}
}
