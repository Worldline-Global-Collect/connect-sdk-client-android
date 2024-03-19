/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.thirdpartystatus;

/**
 * POJO that holds the ThirdPartyStatus call response.
 */
public class ThirdPartyStatusResponse {

    private ThirdPartyStatus thirdPartyStatus;

    public ThirdPartyStatusResponse(ThirdPartyStatus thirdPartyStatus) {
        this.thirdPartyStatus = thirdPartyStatus;
    }

    public ThirdPartyStatus getThirdPartyStatus() {
        return thirdPartyStatus;
    }
    public void setThirdPartyStatus(ThirdPartyStatus thirdPartyStatus) {
        this.thirdPartyStatus = thirdPartyStatus;
    }
}
