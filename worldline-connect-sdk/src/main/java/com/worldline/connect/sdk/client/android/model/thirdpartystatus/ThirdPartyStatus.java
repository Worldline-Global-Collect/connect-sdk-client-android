/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.thirdpartystatus;

/**
 * An enumeration of the possible statuses that a third party payment may be in.
 */
public enum ThirdPartyStatus {
    WAITING,
    INITIALIZED,
    AUTHORIZED,
    COMPLETED,

    ;
}
