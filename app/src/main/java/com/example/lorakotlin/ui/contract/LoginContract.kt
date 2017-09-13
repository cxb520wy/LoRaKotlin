package com.example.lorakotlin.ui.contract

/**
 * 创建时间: 2017/8/23
 * 创建人: dell
 * 描述: //TODO
 * 版本: V1.0
 */
interface LoginContract {
    interface View{
        fun showLoginDilog()
        fun hideLoginDialog()
    }

    interface LoginListener{
        fun loginSuccess()
        fun loginFailed(message :String)
    }

    interface SendVertifyCodeListener{
        fun sendSuccess()
        fun sendFailed(message: String)
    }
}