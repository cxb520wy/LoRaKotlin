package com.example.lorakotlin.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.KeyEvent
import com.example.lorakotlin.R
import kotlinx.android.synthetic.main.dialog_login.*

/**
 * 创建时间: 2017/8/24
 * 创建人: dell
 * 描述: //TODO
 * 版本: V1.0
 */
class LoginDialog : DialogFragment() {
    var msg: String = "正在登入中..."
        get() = field
        set(value) {
            field = value
            dialog.dialog_loading_msg?.setText(field)
        }

    var mListener: BackPressListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(activity, R.style.dialog)
        dialog.setContentView(R.layout.dialog_login)
        dialog.dialog_loading_msg?.setText(msg)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(true)

        dialog.setOnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                mListener?.backPress()
                true
            } else {
                false
            }
        }
        return dialog
    }

    interface BackPressListener {
        fun backPress()
    }
}
