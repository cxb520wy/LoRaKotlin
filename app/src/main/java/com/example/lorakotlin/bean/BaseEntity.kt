package com.example.lorakotlin.bean

import com.google.gson.annotations.SerializedName

/**
 * 创建时间: 2017/8/23
 * 创建人: dell
 * 描述: //TODO
 * 版本: V1.0
 */
/**
 * 公共的网络请求接收类
 *
 *
 * Created by james on 2017/6/28.
 */
data class BaseEntity<E>(
        @SerializedName("code") var code: Int = 0,
        @SerializedName("message") var message: String? = null,
        @SerializedName("date") var date: Long = 0,
        @SerializedName("taskuuid") var taskuuid: String? = null,
        @SerializedName("isSuccess") var isSuccess: Boolean = false,
        @SerializedName("data") var data: E? = null
)