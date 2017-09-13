package com.example.lorakotlin.ui.presenter

import android.text.TextUtils
import com.example.lorakotlin.bean.BaseEntity
import com.example.lorakotlin.bean.para.LoginByPasswordPara
import com.example.lorakotlin.bean.response.LoginData
import com.example.lorakotlin.network.Api
import com.example.lorakotlin.network.LoginByVertifyCodePara
import com.example.lorakotlin.network.RetrofitUtil
import com.example.lorakotlin.network.SendVertifyCodePara
import com.example.lorakotlin.ui.contract.LoginContract
import com.example.lorakotlin.utils.ToastUtils
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

/**
 * 创建时间: 2017/8/23
 * 创建人: dell
 * 描述: //TODO
 * 版本: V1.0
 */
class LoginPresenter(val mView: LoginContract.View) {
    private var mCompositeSubscription: CompositeSubscription = CompositeSubscription()

    fun sendVertifyCode(phoneNumber: String, listener: LoginContract.SendVertifyCodeListener) {
        val api = RetrofitUtil.getDefaultRetrofit().create(Api::class.java)
        val subscription = api.sendVertifyCode(SendVertifyCodePara(phoneNumber), "aaa", "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<BaseEntity<*>>() {
                    override fun onNext(t: BaseEntity<*>?) {
                        if (t?.isSuccess ?: false) {
                            listener?.sendSuccess()
                        } else {
                            t?.let { listener?.sendFailed(t.message ?: "empty") } ?:
                                    listener?.sendFailed("数据返回为空")
                        }
                    }

                    override fun onError(e: Throwable?) {
                        listener?.sendFailed(e?.message ?: "empty")
                    }

                    override fun onCompleted() {

                    }
                })
        mCompositeSubscription.add(subscription)
    }

    fun sendVertifyCode(phoneNumber: String): Observable<BaseEntity<*>>{
        val api = RetrofitUtil.getDefaultRetrofit().create(Api::class.java)
        val observable = api.sendVertifyCode(SendVertifyCodePara(phoneNumber), "aaa", "1")
        return observable
    }

    fun loginByPassword(account: String, password: String, listener: LoginContract.LoginListener) {
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showShort("亲，账号不能为空哦！")
            return loginByPassword@ Unit
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort("亲，账号不能为空哦！")
            return
        }
        val subscription = RetrofitUtil
                .getDefaultRetrofit()
                .create(Api::class.java)
                .loginByPassword(LoginByPasswordPara(password, account), "aa", "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<BaseEntity<LoginData>>() {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable?) {
                        listener?.loginFailed(e?.message ?: "empty")
                    }


                    override fun onNext(t: BaseEntity<LoginData>?) {
                        if (t != null && t.isSuccess) {
//                            t.data?.id = 0
//                            Realm.getDefaultInstance().executeTransaction { it -> it.insertOrUpdate(t.data) }
                            listener?.loginSuccess()
                        } else {
                            t?.let { listener?.loginFailed(t.message ?: "empty") } ?:
                                    listener?.loginFailed("数据返回为空")
                        }
                    }

                })
        mCompositeSubscription.add(subscription)
    }

    fun loginByVertifyCode(account: String, vertifyCode: String, listener: LoginContract.LoginListener) {
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showShort("亲，账号不能为空哦！")
            return
        }

        val subscription = RetrofitUtil
                .getDefaultRetrofit()
                .create(Api::class.java)
                .loginByVertifyCode(LoginByVertifyCodePara(vertifyCode, account), "aa", "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<BaseEntity<LoginData>>() {
                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        listener?.loginFailed(e?.message ?: "empty")
                    }

                    override fun onNext(t: BaseEntity<LoginData>?) {
                        if (t != null && t.isSuccess) {
//                            t.data?.id = 0
//                            Realm.getDefaultInstance().executeTransaction { it -> it.insertOrUpdate(t.data) }
                            listener?.loginSuccess()
                        } else {
                            t?.let { listener?.loginFailed(t.message ?: "empty") } ?:
                                    listener?.loginFailed("数据返回为空")
                        }
                    }

                })
        mCompositeSubscription.add(subscription)
    }

    fun unSubscribe() {
        if (!mCompositeSubscription.isUnsubscribed) {
            mCompositeSubscription.clear()
        }
    }

}