/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.accountonfile.displayhints;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * POJO that represents an DisplayHintsAccountOnFile object.
 */
public class DisplayHintsAccountOnFile implements Serializable {

	private static final long serialVersionUID = 3446099654728722104L;


	private List<AccountOnFileDisplay> labelTemplate = new ArrayList<>();
	private String logo;

	public List<AccountOnFileDisplay> getLabelTemplate(){
		return labelTemplate;
	}

	public String getLogo() {
		return logo;
	}
}
