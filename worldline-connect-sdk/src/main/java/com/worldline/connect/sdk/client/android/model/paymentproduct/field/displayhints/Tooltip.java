/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.paymentproduct.field.displayhints;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * POJO that represents a Tooltip object.
 * Tooltips are payment product specific and are used to show extra information about an input field.
 */
public class Tooltip implements Serializable {

	private static final long serialVersionUID = -317203058533669043L;

	private String label;
	@SerializedName("image")
	private String imageURL;
	private transient Drawable imageDrawable;

	public String getLabel(){
		return label;
	}

	public String getImageURL(){
		return imageURL;
	}

	public Drawable getImageDrawable() {
		return imageDrawable;
	}

	public void setImageDrawable(Drawable imageDrawable){
		this.imageDrawable = imageDrawable;
	}
}
