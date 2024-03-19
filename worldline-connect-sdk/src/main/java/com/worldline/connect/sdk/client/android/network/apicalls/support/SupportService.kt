/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.network.apicalls.support

import com.worldline.connect.sdk.client.android.model.convertamount.ConvertedAmountResponse
import com.worldline.connect.sdk.client.android.model.publickey.PublicKeyResponse
import com.worldline.connect.sdk.client.android.model.thirdpartystatus.ThirdPartyStatusResponse
import com.worldline.connect.sdk.client.android.model.iindetails.IinDetailsRequest
import com.worldline.connect.sdk.client.android.model.iindetails.IinDetailsResponse
import com.worldline.connect.sdk.client.android.model.privacypolicy.PrivacyPolicyResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

internal interface SupportService {

    @GET("services/privacypolicy")
    fun getPrivacyPolicy(
        @Query("paymentProductId") paymentProductId: String?,
        @Query("locale") locale: String?
    ): Observable<Response<PrivacyPolicyResponse>>

    @Headers("Content-Type: application/json")
    @POST("services/getIINdetails")
    fun getIINDetails(@Body iinDetailsRequest: IinDetailsRequest): Observable<Response<IinDetailsResponse>>

    @GET("crypto/publickey")
    fun getPublicKey(): Observable<Response<PublicKeyResponse>>

    @GET("payments/{paymentId}/thirdpartystatus")
    fun getThirdPartyStatus(@Path("paymentId") paymentId: String): Observable<Response<ThirdPartyStatusResponse>>

    @GET("services/convert/amount")
    fun convertAmount(@QueryMap parameters: Map<String, String>): Observable<Response<ConvertedAmountResponse>>
}
