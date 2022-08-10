package com.vigeo.fnur.main.viewModel

import com.google.gson.annotations.SerializedName
import kotlin.collections.ArrayList

data class MainVO(

    @SerializedName("medicineListCnt")
    val medicineListCnt: Int,

    @SerializedName("medicineList")
    val medicineList: ArrayList<medicineListObject>,

    @SerializedName("message")
    val message: String,

    @SerializedName("error")
    val error: String,
)

data class medicineListObject(
    @SerializedName("medNo")
    val medNo: String,

    @SerializedName("medNm")
    val medNm: String,

    @SerializedName("medSymptom")
    val medSymptom: String,

    @SerializedName("medCountry")
    val medCountry: String,

    @SerializedName("useYn")
    val useYn: String,

    @SerializedName("medImg")
    val medImg: String,

    @SerializedName("regDate")
    val regDate: String,
)

data class faqListObject(
    @SerializedName("title")
    val title: String,

    @SerializedName("content")
    val content: String,
)
