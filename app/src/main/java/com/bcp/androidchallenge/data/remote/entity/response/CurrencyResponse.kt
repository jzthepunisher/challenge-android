package com.bcp.androidchallenge.data.remote.response

import com.google.gson.annotations.SerializedName

data class CurrencyResponse (val data: Data)
data class Data (val currencies : List<CurrencyItemResponse>)
data class CurrencyItemResponse (
    @SerializedName("description") val description :String,
    @SerializedName("euro_equivalence") val euroEquivalence :Double,
    @SerializedName("sign") val sign :String,
    @SerializedName("sign_description") val signDescription :String
    /*@SerializedName("type") val type : String,
    @SerializedName("description") val description : String,
    @SerializedName("cardNumber") val cardNumber : String,
    @SerializedName("viewStatus") val viewStatus : Boolean*/
)