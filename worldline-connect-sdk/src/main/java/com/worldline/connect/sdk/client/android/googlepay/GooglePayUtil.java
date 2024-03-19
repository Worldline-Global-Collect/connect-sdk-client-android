/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.googlepay;

import android.util.Log;

import com.worldline.connect.sdk.client.android.ConnectSDK;
import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration;
import com.worldline.connect.sdk.client.android.constants.Constants;
import com.worldline.connect.sdk.client.android.configuration.PaymentConfiguration;
import com.worldline.connect.sdk.client.android.model.paymentproduct.specificdata.PaymentProduct320SpecificData;

import com.worldline.connect.sdk.client.android.model.paymentproduct.BasicPaymentProduct;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Util class containing Google Pay related methods.
 *
 * @deprecated In a future release, this class will become internal to the SDK.
 */
@Deprecated
public final class GooglePayUtil {

    private static final String TAG = GooglePayUtil.class.getName();

    private static final int GOOGLE_PAY_TIMEOUT_IN_MILLIS = 10000;

    private GooglePayUtil() {}

    /**
     * Check whether or not Google Pay is allowed by creating an IsReadyToPayRequest containing minimal information and sending it to Google through the Google Pay PaymentsClient.
     * Note that {@link ConnectSDK#initialize(ConnectSDKConfiguration, PaymentConfiguration)} must be called before this method can be used.
     *
     * @param googlePay the Google Pay payment product object as obtained through the getPaymentProduct request
     *
     * @return a Boolean indicating whether Google Pay is allowed or not
     */
    public static boolean isGooglePayAllowed(BasicPaymentProduct googlePay) {

        // Validate inputs
        notNull(googlePay);

        // This should never occur as it is controlled by the sdk
        if (!Constants.PAYMENTPRODUCTID_GOOGLEPAY.equals(googlePay.getId())) {
            throw new IllegalArgumentException("This method cannot be called with a product other than Google Pay");
        }

        // Retrieve the networks that, in the current context, can be used for Google Pay
        List<String> networks = getNetworks(googlePay);

        PaymentsClient client = Wallet.getPaymentsClient(
                ConnectSDK.INSTANCE.getConnectSdkConfiguration().getApplicationContext(),
                new Wallet.WalletOptions.Builder()
                        .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                        .build());

        // Create a simple request containing just enough info to check if Google Pay is available
        IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(createGooglePayRequest(networks).toString());
        Task<Boolean> task = client.isReadyToPay(request);

        // Wait for a response
        try {
            Tasks.await(task, GOOGLE_PAY_TIMEOUT_IN_MILLIS, TimeUnit.MILLISECONDS);

            // Handle result
            if (task.isSuccessful()) {
                return task.getResult();
            }

            return false;
        } catch (TimeoutException e) {
            Log.e(TAG, "Timeout while making isReadyToPay call: " + task.getException());
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Exception occurred while making isReadyToPay call: " + task.getException());
            return false;
        }
    }

    /**
     * Assemble the minimal Google Pay payment request that can be used to verify whether the current user is Ready to Pay with Google Pay.
     *
     * @param networks needed for reading metadata
     */
    private static JSONObject createGooglePayRequest(List<String> networks) {

        JSONObject paymentRequest = new JSONObject();
        // Assemble payment request
        try {
            // Insert API version
            paymentRequest.put("apiVersion", Constants.GOOGLE_API_VERSION);
            paymentRequest.put("apiVersionMinor", 0);

            JSONArray allowedPaymentMethods = new JSONArray();
            JSONObject allowedPaymentMethodsContent = getAllowedPaymentMethodsJson(networks);

            allowedPaymentMethods.put(allowedPaymentMethodsContent);
            paymentRequest.put("allowedPaymentMethods", allowedPaymentMethods);

        } catch (JSONException e) {
            Log.e(TAG, "Exception occurred while creating JSON object: " + e);
        }

        return paymentRequest;
    }

    private static JSONObject getAllowedPaymentMethodsJson(List<String> networks) {

        JSONArray cardPaymentMethod = new JSONArray();
        JSONArray allowedNetworks = new JSONArray();
        JSONArray allowedAuthMethods = new JSONArray()
                .put("PAN_ONLY")
                .put("CRYPTOGRAM_3DS");

        // Convert networks and authMethods to JSON objects
        for( String s : networks) {
            allowedNetworks.put(s);
        }

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("type", "CARD");
            parameters.put(
                    "parameters",
                    new JSONObject()
                            .put("allowedAuthMethods", allowedAuthMethods)
                            .put("allowedCardNetworks", allowedNetworks)
            );
            cardPaymentMethod.put(parameters);

        } catch (JSONException e) {
            Log.e(TAG, "Exception occurred while creating JSON object: " + e);
        }

        return parameters;
    }

    private static List<String> getNetworks(BasicPaymentProduct paymentProduct) {

        PaymentProduct320SpecificData paymentProductSpecificData = paymentProduct.getPaymentProduct320SpecificData();

        List<String> networks = paymentProductSpecificData != null ? paymentProductSpecificData.getNetworks() : null;

        if (networks != null && !networks.isEmpty()) {
            return networks;
        } else {
            throw new IllegalStateException("No networks found");
        }
    }
}
