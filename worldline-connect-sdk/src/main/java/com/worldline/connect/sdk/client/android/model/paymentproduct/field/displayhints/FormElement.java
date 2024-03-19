/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.paymentproduct.field.displayhints;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * POJO that represents a FormElement object.
 * The FormElement is used for determining its list type (text, list, currency, date or boolean).
 * In case of a list, it also has values inside the valueMapping.
 */
public class FormElement implements Serializable {

	private static final long serialVersionUID = 7081218270681792356L;


	public enum ListType {
		@SerializedName("text")
		TEXT,

		@SerializedName("list")
		LIST,

		@SerializedName("currency")
		CURRENCY,

		@SerializedName("date")
		DATE,

		@SerializedName("boolean")
		BOOLEAN,
		;
	}

	private ListType type;
	private List<ValueMap> valueMapping = new ArrayList<>();


	public ListType getType(){
		return type;
	}

	public List<ValueMap> getValueMapping(){
		return valueMapping;
	}
}
