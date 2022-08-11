package com.vigeo.fnur.main.viewModel

import com.google.gson.annotations.SerializedName
import kotlin.collections.ArrayList

data class MainVO(

    @SerializedName("medicineListCnt")
    val medicineListCnt: Int,

    @SerializedName("medicineList")
    val medicineList: ArrayList<medicineListObject>,

    @SerializedName("error")
    val error: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("medicineSelect")
    val medicineSelect: medicineListObject,

    @SerializedName("countryAppList")
    val countryAppList: ArrayList<commonCodeObject>,

)

data class commonCodeObject(
    @SerializedName("comNo")
    val comNo: String,

    @SerializedName("comCd")
    val comCd: String,

    @SerializedName("comNm")
    val comNm: String,

    @SerializedName("comPtCd")
    val comPtCd: String,

    @SerializedName("comPtNm")
    val comPtNm: String,

    @SerializedName("comSort")
    val comSort: String,
)

data class medicineListObject(
    @SerializedName("medNo")
    val medNo: String,

    @SerializedName("medClassification")
    val medClassification: String,

    @SerializedName("medNm")
    val medNm: String,

    @SerializedName("medSymptom")
    val medSymptom: String,

    @SerializedName("medCountry")
    val medCountry: String,

    @SerializedName("medLicenseNum")
    val medLicenseNum: String,

    @SerializedName("medEffects")
    val medEffects: String,

    @SerializedName("medUsage")
    val medUsage: String,

    @SerializedName("medContraindications")
    val medContraindications: String,

    @SerializedName("medPrecautions")
    val medPrecautions: String,

    @SerializedName("medExpriationMaxDate")
    val medExpriationMaxDate: String,

    @SerializedName("medManufacturer")
    val medManufacturer: String,

    @SerializedName("medPackagingUnit")
    val medPackagingUnit: String,

    @SerializedName("medPackagingUnitCount")
    val medPackagingUnitCount: String,

    @SerializedName("useYn")
    val useYn: String,

    @SerializedName("medImg")
    val medImg: String,

    @SerializedName("regDate")
    val regDate: String,
)

