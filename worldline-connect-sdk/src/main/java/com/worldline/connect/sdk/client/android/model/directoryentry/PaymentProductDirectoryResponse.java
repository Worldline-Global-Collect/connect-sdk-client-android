/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.directoryentry;

import java.util.ArrayList;

/**
 * POJO that contains the response for PaymentProductDirectory lookup.
 */
public class PaymentProductDirectoryResponse {

	private ArrayList<DirectoryEntry> entries;


	public PaymentProductDirectoryResponse(ArrayList<DirectoryEntry> entries) {
		this.entries = entries;
	}

	public ArrayList<DirectoryEntry> getEntries() {
		return entries;
	}

}
