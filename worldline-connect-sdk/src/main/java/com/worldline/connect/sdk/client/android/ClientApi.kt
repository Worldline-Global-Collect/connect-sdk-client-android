/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android

import android.graphics.drawable.Drawable
import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.configuration.PaymentConfiguration
import com.worldline.connect.sdk.client.android.network.drawable.GetDrawableFromUrl
import com.worldline.connect.sdk.client.android.model.directoryentry.PaymentProductDirectoryResponse
import com.worldline.connect.sdk.client.android.model.publickey.PublicKeyResponse
import com.worldline.connect.sdk.client.android.model.thirdpartystatus.ThirdPartyStatus
import com.worldline.connect.sdk.client.android.model.iindetails.IinDetailsResponse
import com.worldline.connect.sdk.client.android.model.paymentitem.BasicPaymentItems
import com.worldline.connect.sdk.client.android.model.paymentproduct.BasicPaymentProducts
import com.worldline.connect.sdk.client.android.model.paymentproductgroup.BasicPaymentProductGroups
import com.worldline.connect.sdk.client.android.model.paymentproduct.PaymentProduct
import com.worldline.connect.sdk.client.android.model.paymentproductgroup.PaymentProductGroup
import com.worldline.connect.sdk.client.android.model.privacypolicy.PrivacyPolicyResponse
import com.worldline.connect.sdk.client.android.network.ApiError
import com.worldline.connect.sdk.client.android.network.Failure
import com.worldline.connect.sdk.client.android.network.Success
import com.worldline.connect.sdk.client.android.network.extension.subscribeAndMapNetworkResponse
import com.worldline.connect.sdk.client.android.network.apicalls.product.GetPaymentProduct
import com.worldline.connect.sdk.client.android.network.apicalls.product.GetPaymentProductDirectory
import com.worldline.connect.sdk.client.android.network.apicalls.product.GetPaymentProducts
import com.worldline.connect.sdk.client.android.network.apicalls.product.GetPaymentItems
import com.worldline.connect.sdk.client.android.network.apicalls.productsgroup.GetPaymentProductGroup
import com.worldline.connect.sdk.client.android.network.apicalls.productsgroup.GetPaymentProductGroups
import com.worldline.connect.sdk.client.android.network.apicalls.support.ConvertAmount
import com.worldline.connect.sdk.client.android.network.apicalls.support.GetIINDetails
import com.worldline.connect.sdk.client.android.network.apicalls.support.GetPrivacyPolicy
import com.worldline.connect.sdk.client.android.network.apicalls.support.GetPublicKey
import com.worldline.connect.sdk.client.android.network.apicalls.support.GetThirdPartyStatus
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Use this class to perform API calls to the Connect gateway.
 */
class ClientApi(
    private val connectSDKConfiguration: ConnectSDKConfiguration,
    private val paymentConfiguration: PaymentConfiguration
) {

    private var compositeDisposable = CompositeDisposable()

    /**
     * Gets the [BasicPaymentProducts] from the gateway.
     *
     * @param onSuccess calls this parameter when [BasicPaymentProducts] is successfully fetched
     * @param onApiError calls this parameter when an api error is returned by the server
     * @param onFailure calls this parameter when an unexpected error thrown
     */
    fun getPaymentProducts(
        onSuccess: Success<BasicPaymentProducts>,
        onApiError: ApiError,
        onFailure: Failure
    ) {
        val disposable = GetPaymentProducts().invoke(
            paymentConfiguration.paymentContext,
            connectSDKConfiguration
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeAndMapNetworkResponse(
                { (onSuccess::success)(it) },
                { (onApiError::apiError)(it) },
                { (onFailure::failure)(it) }
            )

        compositeDisposable.add(disposable)
    }

    /**
     * Gets the [PaymentProduct] from the gateway.
     *
     * @param paymentProductId the paymentProductId of the product which needs to be retrieved from the server
     * @param onSuccess calls this parameter when a [PaymentProduct] is successfully fetched
     * @param onApiError calls this parameter when an api error is returned by the server
     * @param onFailure calls this parameter when an unexpected error thrown
     */
    fun getPaymentProduct(
        paymentProductId: String,
        onSuccess: Success<PaymentProduct>,
        onApiError: ApiError,
        onFailure: Failure
    ) {
        val disposable = GetPaymentProduct().invoke(
            paymentConfiguration.paymentContext,
            connectSDKConfiguration,
            paymentProductId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeAndMapNetworkResponse(
                { (onSuccess::success)(it) },
                { (onApiError::apiError)(it) },
                { (onFailure::failure)(it) }
            )

        compositeDisposable.add(disposable)
    }

    /**
     * Gets the [BasicPaymentProductGroups] from the gateway.
     *
     * @param onSuccess calls this parameter when [BasicPaymentProductGroups] is successfully fetched
     * @param onApiError calls this parameter when an api error is returned by the server
     * @param onFailure calls this parameter when an unexpected error thrown
     */
    fun getPaymentProductGroups(
        onSuccess: Success<BasicPaymentProductGroups>,
        onApiError: ApiError,
        onFailure: Failure
    ) {
        val disposable = GetPaymentProductGroups().invoke(
            paymentConfiguration.paymentContext,
            connectSDKConfiguration
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeAndMapNetworkResponse(
                { (onSuccess::success)(it) },
                { (onApiError::apiError)(it) },
                { (onFailure::failure)(it) }
            )

        compositeDisposable.add(disposable)
    }

    /**
     * Gets the [PaymentProductGroup] from the gateway.
     *
     * @param paymentProductGroupId the paymentProductGroupId of the product which needs to be retrieved from the server
     * @param onSuccess calls this parameter when a [PaymentProductGroup] is successfully fetched
     * @param onApiError calls this parameter when an api error is returned by the server
     * @param onFailure calls this parameter when an unexpected error thrown
     */
    fun getPaymentProductGroup(
        paymentProductGroupId: String,
        onSuccess: Success<PaymentProductGroup>,
        onApiError: ApiError,
        onFailure: Failure
    ) {
        val disposable = GetPaymentProductGroup().invoke(
            paymentConfiguration.paymentContext,
            connectSDKConfiguration,
            paymentProductGroupId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeAndMapNetworkResponse(
                { (onSuccess::success)(it) },
                { (onApiError::apiError)(it) },
                { (onFailure::failure)(it) }
            )

        compositeDisposable.add(disposable)
    }

    /**
     * Gets the [BasicPaymentItems] from the gateway.
     *
     * @param onSuccess calls this parameter when [BasicPaymentItems] is successfully fetched
     * @param onApiError calls this parameter when an api error is returned by the server
     * @param onFailure calls this parameter when an unexpected error thrown
     */
    fun getPaymentItems(
        onSuccess: Success<BasicPaymentItems>,
        onApiError: ApiError,
        onFailure: Failure
    ) {
        val disposable = GetPaymentItems()
            .invoke(
            paymentConfiguration.paymentContext,
            connectSDKConfiguration,
            paymentConfiguration.groupPaymentProducts
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeAndMapNetworkResponse(
                { (onSuccess::success)(it) },
                { (onApiError::apiError)(it) },
                { (onFailure::failure)(it) }
            )

        compositeDisposable.add(disposable)
    }

    /**
     * Gets payment product directories as a [PaymentProductDirectoryResponse] from the gateway.
     * Mainly used for IDEAL payments.
     *
     * @param onSuccess calls this parameter when payment product directories
     *        are successfully fetched as a [PaymentProductDirectoryResponse]
     * @param onApiError calls this parameter when an api error is returned by the server
     * @param onFailure calls this parameter when an unexpected error thrown
     */
    fun getPaymentProductDirectory(
        paymentProductId: String,
        onSuccess: Success<PaymentProductDirectoryResponse>,
        onApiError: ApiError,
        onFailure: Failure
    ) {
        val disposable = GetPaymentProductDirectory().invoke(
            paymentConfiguration.paymentContext,
            connectSDKConfiguration,
            paymentProductId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeAndMapNetworkResponse(
                { (onSuccess::success)(it) },
                { (onApiError::apiError)(it) },
                { (onFailure::failure)(it) }
            )

        compositeDisposable.add(disposable)
    }

    /**
     * Gets the [PrivacyPolicyResponse] from the gateway.
     *
     * @param paymentProductId the paymentProductId of the product for which you wish to retrieve the privacy policy.
     *  When this value is set to null you will receive all privacy policies for your payment products
     * @param locale the locale in which the privacy policy should be returned.
     *  When this value is set to null it will use the default locale
     * @param onSuccess calls this parameter when [PrivacyPolicyResponse] is successfully fetched
     * @param onApiError calls this parameter when an api error is returned by the server
     * @param onFailure calls this parameter when an unexpected error thrown
     */
    fun getPrivacyPolicy(
        paymentProductId: String?,
        locale: String?,
        onSuccess: Success<PrivacyPolicyResponse>,
        onApiError: ApiError,
        onFailure: Failure
    ) {
        val disposable = GetPrivacyPolicy().invoke(
            connectSDKConfiguration,
            paymentProductId,
            locale
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeAndMapNetworkResponse(
                { (onSuccess::success)(it) },
                { (onApiError::apiError)(it) },
                { (onFailure::failure)(it) }
            )

        compositeDisposable.add(disposable)
    }

    /**
     * Gets the IinDetails as a [IinDetailsResponse] for a given Bank Identification Number.
     *
     * @param bin Bank Identification Number, first six digits of a bank card number or payment cards number
     * @param onSuccess calls this parameter when the details of an IIN
     *        are successfully fetched as a [IinDetailsResponse]
     * @param onApiError calls this parameter when an api error is returned by the server
     * @param onFailure calls this parameter when an unexpected error thrown
     */
    fun getIINDetails(
        bin: String,
        onSuccess: Success<IinDetailsResponse>,
        onApiError: ApiError,
        onFailure: Failure
    ) {
        val disposable = GetIINDetails().invoke(
            paymentConfiguration.paymentContext,
            connectSDKConfiguration,
            bin
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeAndMapNetworkResponse(
                { (onSuccess::success)(it) },
                { (onApiError::apiError)(it) },
                { (onFailure::failure)(it) }
            )

        compositeDisposable.add(disposable)
    }

    /**
     * Gets the public key as a [PublicKeyResponse] from the gateway.
     * Use this method only in combination with an encryption method.
     *
     * @param onSuccess calls this parameter when the public key is successfully fetched as a [PublicKeyResponse]
     * @param onApiError calls this parameter when an api error is returned by the server
     * @param onFailure calls this parameter when an unexpected error thrown
     */
    fun getPublicKey(
        onSuccess: Success<PublicKeyResponse>,
        onApiError: ApiError,
        onFailure: Failure
    ) {
        val disposable = GetPublicKey().invoke(
            connectSDKConfiguration
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeAndMapNetworkResponse(
                { (onSuccess::success)(it) },
                { (onApiError::apiError)(it) },
                { (onFailure::failure)(it) }
            )

        compositeDisposable.add(disposable)
    }

    /**
     * Gets the [ThirdPartyStatus] from the gateway.
     * Supported payment products for this call are Bancontact and WeChat Pay.
     *
     * @param paymentId the payment id for this payment
     * @param onSuccess calls this parameter when the [ThirdPartyStatus] is successfully fetched
     * @param onApiError calls this parameter when an api error is returned by the server
     * @param onFailure calls this parameter when an unexpected error thrown
     */
    fun getThirdPartyStatus(
        paymentId: String,
        onSuccess: Success<ThirdPartyStatus>,
        onApiError: ApiError,
        onFailure: Failure
    ) {
        val disposable = GetThirdPartyStatus().invoke(
            connectSDKConfiguration,
            paymentId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeAndMapNetworkResponse(
                { (onSuccess::success)(it) },
                { (onApiError::apiError)(it) },
                { (onFailure::failure)(it) }
            )

        compositeDisposable.add(disposable)
    }

    /**
     * Converts a given amount in cents from the given source currency to the given target currency
     *
     * @param source the currency which the amount currently is
     * @param target the currency to which the amount should be converted
     * @param amount the amount in cents to be converted
     * @param onSuccess calls this parameter when the amount is successfully converted
     * @param onApiError calls this parameter when an api error is returned by the server
     * @param onFailure calls this parameter when an unexpected error thrown
     */
    @Suppress("LongParameterList")
    fun convertAmount(
        source: String,
        target: String,
        amount: Long,
        onSuccess: Success<Long>,
        onApiError: ApiError,
        onFailure: Failure
    ) {
        val disposable = ConvertAmount().invoke(
            connectSDKConfiguration,
            source,
            target,
            amount
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeAndMapNetworkResponse(
                { (onSuccess::success)(it) },
                { (onApiError::apiError)(it) },
                { (onFailure::failure)(it) }
            )

        compositeDisposable.add(disposable)
    }

    /**
     * Gets a drawable from the gateway.
     *
     * @param drawableUrl the path to the image, the base URL should be omitted
     * @param onSuccess calls this parameter when the drawable is successfully fetched
     * @param onFailure calls this parameter when an unexpected error thrown
     */
    fun getDrawableFromUrl(
        drawableUrl: String,
        onSuccess: Success<Drawable>,
        onFailure: Failure
    ) {
        val disposable = GetDrawableFromUrl().invoke(connectSDKConfiguration, drawableUrl)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                (onSuccess::success)(it)
            }, {
                (onFailure::failure)(it)
            })

        compositeDisposable.add(disposable)
    }

    internal fun disposeApiClient() {
        compositeDisposable.dispose()
    }
}

object ClientApiNotInitializedException :
    Exception("Initialise the ConnectSDK first before you use the ClientApi class.")
