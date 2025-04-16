/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.constants;

public class Constants {

	/** SDK version **/
	public final static String SDK_IDENTIFIER = "AndroidClientSDK/v7.0.4";

	/** SDK creator **/
	public final static String SDK_CREATOR = "Worldline Global Collect";

	/** Cards Group ID **/
	public final static String PAYMENTPRODUCTGROUPID_CARDS = "cards";

	/** Apple Pay product ID **/
	public final static String PAYMENTPRODUCTID_APPLEPAY = "302";
	/** Google Pay product ID**/
	public final static String PAYMENTPRODUCTID_GOOGLEPAY = "320";

	public final static int GOOGLE_API_VERSION = 2;
	public final static String 	GOOGLE_PAY_TOKEN_FIELD_ID = "encryptedPaymentData";

	/** Boleto Bancario product ID **/
	public final static String PAYMENTPRODUCTID_BOLETOBANCARIO = "1503";
	/** Fiscal number field ID **/
	public final static String FISCAL_NUMBER_FIELD_ID = "fiscalNumber";

	/** BanContact product ID **/
	public final static String PAYMENTPRODUCTID_BanContact = "3012";

	/** Link placeholder for label texts **/
	public final static String LINK_PLACEHOLDER = "{link}";
}
