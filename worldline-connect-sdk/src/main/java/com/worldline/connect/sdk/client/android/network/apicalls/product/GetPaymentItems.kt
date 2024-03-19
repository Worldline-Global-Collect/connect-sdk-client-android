/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.apicalls.product

import com.worldline.connect.sdk.client.android.configuration.ConnectSDKConfiguration
import com.worldline.connect.sdk.client.android.model.paymentcontext.PaymentContext
import com.worldline.connect.sdk.client.android.model.paymentitem.BasicPaymentItem
import com.worldline.connect.sdk.client.android.model.paymentitem.BasicPaymentItems
import com.worldline.connect.sdk.client.android.model.paymentproductgroup.BasicPaymentProductGroups
import com.worldline.connect.sdk.client.android.model.paymentproduct.BasicPaymentProducts
import com.worldline.connect.sdk.client.android.network.NetworkResponse
import com.worldline.connect.sdk.client.android.network.apicalls.productsgroup.GetPaymentProductGroups
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.Observables

internal class GetPaymentItems {

    operator fun invoke(
        paymentContext: PaymentContext,
        connectSdkConfiguration: ConnectSDKConfiguration,
        groupPaymentProducts: Boolean
    ): Observable<NetworkResponse<BasicPaymentItems>> {
        return if (groupPaymentProducts) {
            Observables.combineLatest(
                GetPaymentProducts()
                    .invoke(paymentContext, connectSdkConfiguration),
                GetPaymentProductGroups().invoke(paymentContext, connectSdkConfiguration)
            )
                .map { (products, groups) ->
                    groupPaymentProducts(products, groups)
                }
        } else {
            GetPaymentProducts()
                .invoke(paymentContext, connectSdkConfiguration).map {
                mapBasicPaymentProductsToBasicPaymentItems(it)
            }
        }
    }

    private fun groupPaymentProducts(
        products: NetworkResponse<BasicPaymentProducts>,
        groups: NetworkResponse<BasicPaymentProductGroups>
    ): NetworkResponse<BasicPaymentItems> {
        return when {
            products is NetworkResponse.ApiError -> {
                NetworkResponse.ApiError(products.apiErrorResponse)
            }
            products is NetworkResponse.Success && groups is NetworkResponse.ApiError -> {
                mapBasicPaymentProductsToBasicPaymentItems(products)
            }
            products is NetworkResponse.Success && groups is NetworkResponse.Success -> {
                mergeBasicPaymentProductsToBasicPaymentItems(products, groups)
            }
            else -> {
                NetworkResponse.ApiError(null, null)
            }
        }
    }

    private fun mapBasicPaymentProductsToBasicPaymentItems(
        networkResponse: NetworkResponse<BasicPaymentProducts>
    ): NetworkResponse<BasicPaymentItems> {
        return when (networkResponse) {
            is NetworkResponse.ApiError -> {
                NetworkResponse.ApiError(networkResponse.apiErrorResponse)
            }
            is NetworkResponse.Success -> {
                NetworkResponse.Success(
                    BasicPaymentItems(
                        networkResponse.data?.paymentProductsAsItems,
                        networkResponse.data?.accountsOnFile
                    )
                )
            }
        }
    }

    private fun mergeBasicPaymentProductsToBasicPaymentItems(
        products: NetworkResponse<BasicPaymentProducts>,
        groups: NetworkResponse<BasicPaymentProductGroups>
    ): NetworkResponse<BasicPaymentItems> {
        val basicPaymentItems = products.data?.basicPaymentProducts?.filter { basicPaymentProduct ->
            groups.data?.basicPaymentProductGroups?.map { basicPaymentGroup -> basicPaymentGroup.id }
                ?.contains(basicPaymentProduct.paymentProductGroup) == false
        }?.map { basicPaymentProduct -> basicPaymentProduct as BasicPaymentItem }?.toMutableList()
        groups.data?.paymentProductGroupsAsItems?.let { basicPaymentItems?.addAll(it) }

        return NetworkResponse.Success(
            BasicPaymentItems(
                basicPaymentItems,
                groups.data?.accountsOnFile
            )
        )
    }
}


