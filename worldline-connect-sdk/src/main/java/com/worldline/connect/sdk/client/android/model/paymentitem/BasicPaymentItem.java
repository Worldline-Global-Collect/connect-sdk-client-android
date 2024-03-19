/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.paymentitem;

import com.worldline.connect.sdk.client.android.model.accountonfile.AccountOnFile;

import java.io.Serializable;
import java.util.List;

public interface BasicPaymentItem extends Serializable {

    String getId();

    DisplayHintsPaymentItem getDisplayHints();

    List<AccountOnFile> getAccountsOnFile();

    String getAcquirerCountry();

}
