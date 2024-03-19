/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.encryption

import com.worldline.connect.sdk.client.android.util.MetadataUtil
import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.exception.EncryptDataException
import com.worldline.connect.sdk.client.android.model.paymentrequest.EncryptedPaymentRequest
import com.worldline.connect.sdk.client.android.model.paymentrequest.PaymentRequest
import com.worldline.connect.sdk.client.android.model.publickey.PublicKeyResponse
import com.worldline.connect.sdk.client.android.model.paymentproduct.field.PaymentProductField
import com.worldline.connect.sdk.client.android.network.NetworkResponse
import com.worldline.connect.sdk.client.android.network.apicalls.support.GetPublicKey
import io.reactivex.rxjava3.core.Observable
import java.util.*

internal class EncryptPaymentRequest {

    operator fun invoke(
        connectSDKConfiguration: ConnectSDKConfiguration,
        paymentRequest: PaymentRequest
    ): Observable<NetworkResponse<EncryptedPaymentRequest>> {

        return GetPublicKey().invoke(
            connectSDKConfiguration
        ).map { networkResponse ->
            when (networkResponse) {
                is NetworkResponse.ApiError -> {
                    NetworkResponse.ApiError(networkResponse.apiErrorResponse)
                }
                is NetworkResponse.Success -> {
                    encryptPublicKey(connectSDKConfiguration, paymentRequest, networkResponse.data)
                }
            }
        }
    }

    private fun encryptPublicKey(
        connectSDKConfiguration: ConnectSDKConfiguration,
        paymentRequest: PaymentRequest,
        publicKeyResponse: PublicKeyResponse?
    ): NetworkResponse<EncryptedPaymentRequest> {

        // Format all values based on their paymentproductfield.type and them to the encryptedValues
        val formattedPaymentValues: MutableMap<String, String> = HashMap()
        for (field in paymentRequest.paymentProduct.paymentProductFields) {
            val value = paymentRequest.getValue(field.id)

            // The date and expirydate are already in the correct format.
            // If the masks given by the GC gateway are correct
            if (field.type != null && value != null) {
                if (field.type == PaymentProductField.Type.NUMERICSTRING) {
                    formattedPaymentValues[field.id] = value.replace("[^\\d.]".toRegex(), "")
                } else {
                    formattedPaymentValues[field.id] = value
                }
            }
        }

        val encryptDataObject = EncryptData()
            .apply {
            paymentValues = formattedPaymentValues
            clientSessionId = connectSDKConfiguration.sessionConfiguration.clientSessionId
            nonce = UUID.randomUUID().toString()
            paymentProductId = paymentRequest.paymentProduct.id.toInt()
            tokenize = paymentRequest.tokenize

            if (paymentRequest.accountOnFile != null) accountOnFileId = paymentRequest.accountOnFile.id
        }

        val encryptPaymentRequest = Encryptor(
            publicKeyResponse
        ).encrypt(encryptDataObject)?.let { encryptedData ->
            EncryptedPaymentRequest(encryptedData,
                MetadataUtil.getBase64EncodedMetadata(connectSDKConfiguration)
            )
        } ?: throw EncryptDataException(
            "Error while encrypting fields"
        )

        return NetworkResponse.Success(encryptPaymentRequest)
    }
}
