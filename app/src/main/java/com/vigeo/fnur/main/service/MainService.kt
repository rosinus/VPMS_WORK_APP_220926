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








    //userMileage 불러오기
    @FormUrlEncoded
    @POST("appApi/userApp/userMileage.do")
    fun userMileage(
        @Field("vigeoToken") vigeoToken: String = "O304UIUw3P78ZZPC5qBkmQ==",
        @Field("userGb") userGb: String = "03",
        @Field("userNo") userNo: String = "",
        @Field("year") year: String = "",
        @Field("month") month: String = "",
    ): Call<MainVO>


    //isUserUpdate 불러오기
    @FormUrlEncoded
    @POST("appApi/userApp/isCollUserUpdate.do")
    fun isCollUserUpdate(
        @Field("vigeoToken") vigeoToken: String = "O304UIUw3P78ZZPC5qBkmQ==",
        @Field("userGb") userGb: String = "03",
        @Field("userNo") userNo: String = "",
        @Field("userNm") userNm: String = "",
        @Field("userPw") userPw: String = "",
        @Field("email") email: String = "",
        @Field("cmpnyNm") cmpnyNm: String = "",
        @Field("zipCd") zipCd: String = "",
        @Field("addr") addr: String = "",
        @Field("addrDetail") addrDetail: String = "",
        @Field("phoneNum") phoneNum: String = ""
    ): Call<MainVO>

    //isUserExist 불러오기
    @FormUrlEncoded
    @POST("appApi/userApp/isUserExist.do")
    fun isUserExist(
        @Field("vigeoToken") vigeoToken: String = "O304UIUw3P78ZZPC5qBkmQ==",
        @Field("userGb") userGb: String = "03",
        @Field("phoneNum") userId: String = "",
    ): Call<MainVO>

    //isUserDelect 불러오기
    @FormUrlEncoded
    @POST("appApi/userApp/isUserDelect.do")
    fun isUserDelect(
        @Field("vigeoToken") vigeoToken: String = "O304UIUw3P78ZZPC5qBkmQ==",
        @Field("userNo") userNo: String = "",
        @Field("userGb") userGb: String = "03"
    ): Call<MainVO>




}