package com.vigeo.fnur.main.viewModel

import com.google.gson.annotations.SerializedName
import kotlin.collections.ArrayList

data class MainVO(

    @SerializedName("faqList")
    val faqList: ArrayList<faqListObject>,

    @SerializedName("message")
    val message: String,

    @SerializedName("error")
    val error: String,

    @SerializedName("userChack")
    val userChack: String,

    @SerializedName("userMileageList")
    val userMileageList: ArrayList<mileageListObject>,

    @SerializedName("totalMileageCnt")
    val totalMileageCnt: String,
)

data class mileageListObject(
    @SerializedName("userNm")
    val userNm: String,

    @SerializedName("cmpnyNm")
    val cmpnyNm: String,

    @SerializedName("mileageCnt")
    val mileageCnt: String,

    @SerializedName("prodWeight")
    val prodWeight: String,

    @SerializedName("collectAddr")
    val collectAddr: String,

    @SerializedName("collectGbNm")
    val collectGbNm: String,

    @SerializedName("collectDate")
    val collectDate: String,
)

data class faqListObject(
    @SerializedName("title")
    val title: String,

    @SerializedName("content")
    val content: String,
)
