/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.apicalls.productsgroup

import com.worldline.connect.sdk.client.android.model.paymentproductgroup.BasicPaymentProductGroups
import com.worldline.connect.sdk.client.android.model.paymentproductgroup.PaymentProductGroup
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface PaymentProductGroupService {

    @GET("productgroups")
    fun getPaymentProductGroups(
        @QueryMap parameters: Map<String, String>
    ): Observable<Response<BasicPaymentProductGroups>>

    @GET("productgroups/{paymentProductGroupId}")
    fun getPaymentProductGroup(
        @Path("paymentProductGroupId") paymentProductGroupId: String,
        @QueryMap parameters: Map<String, String>
    ): Observable<Response<PaymentProductGroup>>
}
