package com.vigeo.fnur.main.service

import com.vigeo.fnur.main.viewModel.MainVO
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface MainService {

    //medicineAppList 불러오기
    @FormUrlEncoded
    @POST("pjt/medicine/medicineAppList.do")
    fun medicineAppList(
        @Field("vigeoToken") vigeoToken : String = "O304UIUw3P78ZZPC5qBkmQ==",
        @Field("medSymptom") medSymptom : String = "",
        @Field("medCountry") medCountry : String = ""
    ): Call<MainVO>

    //medicineAppSelect 불러오기
    @FormUrlEncoded
    @POST("pjt/medicine/medicineAppSelect.do")
    fun medicineAppSelect(
        @Field("vigeoToken") vigeoToken: String = "O304UIUw3P78ZZPC5qBkmQ==",
        @Field("medNo") medNo: String = ""
    ): Call<MainVO>

    //medicineAppSelect 불러오기
    @FormUrlEncoded
    @POST("pjt/medicine/countryAppList.do")
    fun countryAppList(
        @Field("vigeoToken") vigeoToken: String = "O304UIUw3P78ZZPC5qBkmQ=="
    ): Call<MainVO>







}