package com.example.lorakotlin.network

import com.example.lorakotlin.bean.BaseEntity
import com.example.lorakotlin.bean.para.LoginByPasswordPara
import com.example.lorakotlin.bean.response.LoginData
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import rx.Observable

/**
 * 创建时间: 2017/8/23
 * 创建人: dell
 * 描述: //TODO
 * 版本: V1.0
 */
interface Api {
    @POST("app/LoginCtr/loginByAccount")
    fun loginByPassword(@Body loginPara: LoginByPasswordPara, @Header("authorization") authorization:String, @Header("userId") userId:String): Observable<BaseEntity<LoginData>>

    @POST("app/LoginCtr/SendSms")
    fun sendVertifyCode(@Body sendVertifyCodePara: SendVertifyCodePara, @Header("authorization") authorization:String, @Header("userId") userId:String): Observable<BaseEntity<*>>

    @POST("app/LoginCtr/loginBySms")
    fun loginByVertifyCode(@Body loginPara: LoginByVertifyCodePara, @Header("authorization") authorization:String, @Header("userId") userId:String): Observable<BaseEntity<LoginData>>

    @POST("app/LoginCtr/loginOut")
    fun logout(@Body mainPara: MainPara, @Header("authorization") authorization:String, @Header("userId") userId:String): Observable<BaseEntity<*>>
}

data class MainPara(var dataType: String="json")

data class LoginByVertifyCodePara(
        var idenCode: String? = null,
        var phoneNumber: String? = null
)

data class SendVertifyCodePara(var phoneNumber:String)
