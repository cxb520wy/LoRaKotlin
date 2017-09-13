package com.example.lorakotlin.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import com.example.lorakotlin.R
import com.example.lorakotlin.base.BaseActivity
import com.example.lorakotlin.bean.BaseEntity
import com.example.lorakotlin.ui.contract.LoginContract
import com.example.lorakotlin.ui.dialog.LoginDialog
import com.example.lorakotlin.ui.presenter.LoginPresenter
import com.example.lorakotlin.utils.RegexUtils
import com.example.lorakotlin.utils.ToastUtils
import com.jakewharton.rxbinding.view.RxView
import com.jakewharton.rxbinding.widget.RxTextView
import kotlinx.android.synthetic.main.activity_login.*
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit

class LoginActivity : BaseActivity(), LoginContract.View {
    private var mPresenter: LoginPresenter = LoginPresenter(this)
    private val TOTAL_TIME: Int = 60
    private var isPasswordLogin = true
    private var mLoginDialog:LoginDialog? = null
    private var mCompositeSubscription: CompositeSubscription = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
        setContentView(R.layout.activity_login)
        initEvent()
    }

    private fun initEvent() {
        RxView.clicks(iv_back)
                .subscribe { startActivity(Intent(this@LoginActivity, LoginActivity::class.java)) }


        val subscription = RxView.clicks(rl_verify_code)
                .flatMap(Func1<Void, Observable<BaseEntity<*>>> (){
                    if (!RegexUtils.isMobileExact(edt_account.text.toString().trim())) {
//                        ToastUtils.showShort("亲，请输入正确的手机号码!")
                        return@Func1 Observable.error(Throwable("亲，请输入正确的手机号码!"))
                    }
                    mPresenter.sendVertifyCode(edt_account.text.toString().trim())
                            .subscribeOn(Schedulers.io())
                })
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap (){
                    if (it.isSuccess) {
                        RxView.enabled(rl_verify_code).call(false)
                        RxTextView.text(tv_verify_code).call("$TOTAL_TIME s")
                        Observable.interval(1, TimeUnit.SECONDS)
                                .take(TOTAL_TIME - 1)
                                .subscribeOn(Schedulers.io())
                    } else {
                        Observable.error(Throwable("亲，获取验证码失败!"))
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Long>() {
                    override fun onCompleted() {
                        RxView.enabled(rl_verify_code).call(true)
                        RxTextView.text(tv_verify_code).call("获取验证码")
                    }

                    override fun onNext(t: Long?) {
                        RxTextView.text(tv_verify_code).call("${TOTAL_TIME - 1 - t!!} s")
                    }

                    override fun onError(e: Throwable?) {
                        ToastUtils.showShort(e?.message?:"empty")
                        Log.d("chex", "onError: e = " + e)
                    }

                })
        mCompositeSubscription.add(subscription)

        RxView.clicks(rl_login_button).subscribe (){
            if(isPasswordLogin){
                mPresenter.loginByPassword(edt_account.text.toString().trim(),
                        edt_password.text.toString(),object :LoginContract.LoginListener{
                    override fun loginSuccess() {
                        ToastUtils.showShort("登入成功")
                        startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                        finish()
                    }

                    override fun loginFailed(message: String) {
                        ToastUtils.showShort(message)
                    }
                })
            }else {
                mPresenter.loginByVertifyCode(edt_account.text.toString().trim(),
                        edt_password.text.toString(),object :LoginContract.LoginListener{
                    override fun loginSuccess() {
                        ToastUtils.showShort("登入成功")
                        startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                        finish()
                    }

                    override fun loginFailed(message: String) {
                        ToastUtils.showShort(message)
                    }
                })
            }
        }

        RxView.clicks(tv_switch_login).subscribe {
            if(isPasswordLogin){
                RxView.visibility(rl_verify_code).call(true)
                RxTextView.hint(edt_account).call("请输入手机号")
                RxTextView.hint(edt_password).call("请输入验证码")
                RxTextView.text(edt_account).call("")
                RxTextView.text(edt_password).call("")
                RxTextView.text(tv_switch_login).call("用账号密码登入")
                edt_password.inputType = EditorInfo.TYPE_CLASS_TEXT
                ll_root.setBackgroundResource(R.mipmap.sms_login_bg)
                showLoginDilog()
            }else {
                RxView.visibility(rl_verify_code).call(false)
                RxTextView.hint(edt_account).call("请输入账号")
                RxTextView.hint(edt_password).call("请输入密码")
                RxTextView.text(edt_account).call("")
                RxTextView.text(edt_password).call("")
                RxTextView.text(tv_switch_login).call("用短信验证码登入")
                edt_password.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                ll_root.setBackgroundResource(R.mipmap.password_login_bg)
                hideLoginDialog()
            }
            isPasswordLogin  = !isPasswordLogin
        }

    }

    override fun showLoginDilog() {
        if(mLoginDialog==null){
            mLoginDialog = LoginDialog()
            mLoginDialog?.mListener = object :LoginDialog.BackPressListener{
                override fun backPress() {

                }
            }
        }
        mLoginDialog?.show(supportFragmentManager,LoginDialog::class.simpleName)
    }

    override fun hideLoginDialog() {
        mLoginDialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.unSubscribe()
        if(mCompositeSubscription!=null && !mCompositeSubscription.isUnsubscribed){
            mCompositeSubscription.clear()
        }
    }
}
