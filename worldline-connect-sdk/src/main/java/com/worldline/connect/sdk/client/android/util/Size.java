/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.util;

/**
 * POJO for getting scaled images.
 *
 * @deprecated In a future release, this class will be removed since its no longer used.
 */
@Deprecated
public class Size {

	private Integer width;
	private Integer height;

	public Size(Integer width, Integer height){
		this.width = width;
		this.height = height;
	}

	public Integer getWidth(){
		return width;
	}

	public Integer getHeight(){
		return height;
	}
}
