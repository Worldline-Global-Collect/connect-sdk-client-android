/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.formatter;

/**
 * Result after a mask is applied on a field.
 */
public class FormatResult {

	private String formattedResult;
	private Integer cursorIndex;

	public FormatResult(String formattedResult, Integer cursorIndex) {
		this.formattedResult = formattedResult;
		this.cursorIndex = cursorIndex;
	}

	public Integer getCursorIndex() {
		return cursorIndex;
	}

	public String getFormattedResult() {
		return formattedResult;
	}
}
