/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.paymentproduct.field.displayhints;

import java.io.Serializable;
import java.util.List;

/**
 * POJO which holds the ValueMap data and its paymentProductFieldDisplayElements.
 * If the {@link FormElement} is a list, ValueMap is used to display a value and its displayElements.
 */
public class ValueMap implements Serializable {

	private static final long serialVersionUID = -8334806247597370688L;


	private String value;
	private List<PaymentProductFieldDisplayElement> displayElements;


	public String getValue() {
		return value;
	}

	public List<PaymentProductFieldDisplayElement> getDisplayElements() {
		return displayElements;
	}

}
