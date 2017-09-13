package com.example.lorakotlin.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * author : cxb
 * time   : 2017/8/24  14:53
 * desc   : 全应用碎片的基类，封装了一些常用的方法
 * version: 1.0
 * update : 2017/5/16  14:53
 */
open class BaseFragment : Fragment(){

    protected var TAG = this::class.simpleName?:"chex"
    protected var mIsFirst = true
    //获取宿主Activity
    protected lateinit var holdingActivity: BaseActivity

    override fun onAttach(context: Context?) {//Modified 2016-06-01</span>
        super.onAttach(context)
        Log.d(TAG, "onAttach()")
        this.holdingActivity = context as BaseActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mIsFirst =false
        Log.d(TAG, "onCreate()")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView()")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated()")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated()")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView()")
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        mIsFirst = true
        Log.d(TAG, "onDestroy()")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        Log.d(TAG, "onSaveInstanceState: outState = " + outState!!)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewStateRestored: savedInstanceState = " + savedInstanceState!!)
        super.onViewStateRestored(savedInstanceState)
    }
}