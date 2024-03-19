/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.accountonfile.displayhints;

import java.io.Serializable;


/**
 * POJO that represents an AccountOnFileDisplay object.
 */
public class AccountOnFileDisplay implements Serializable {

	private static final long serialVersionUID = -7793293988073972532L;

	private String attributeKey;
	private String mask;

	public AccountOnFileDisplay(String attributeKey, String mask) {
		this.attributeKey = attributeKey;
		this.mask = mask;
	}

	public String getKey() {
		return attributeKey;
	}

	public String getMask() {
		return mask;
	}

}
