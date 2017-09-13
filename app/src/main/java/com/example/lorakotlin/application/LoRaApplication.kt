package com.example.lorakotlin.application

import android.app.Application
import android.content.Context
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.lorakotlin.utils.Utils
import com.squareup.leakcanary.LeakCanary
import java.util.*

/**
 * 创建时间: 2017/8/23
 * 创建人: dell
 * 描述: //TODO
 * 版本: V1.0
 */
class LoRaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
//        SDKInitializer.initialize(this)
//        SDKInitializer.setCoordType(CoordType.BD09LL)
        Utils.init(this)
        mContext = this
        mHandler = Handler()
//        Realm.init(this)
//        var configuration: RealmConfiguration = RealmConfiguration.Builder().build()
//        Realm.setDefaultConfiguration(configuration)
    }

    companion object {
        lateinit var mContext: Context
        lateinit var mHandler: Handler
        private val activityList: ArrayList<AppCompatActivity> = ArrayList()

        fun add2List(activity: AppCompatActivity) {
            activityList.add(activity)
        }

        fun remove2List(activity: AppCompatActivity) {
            activityList?.contains(activity)?.let {
                activityList.remove(activity)
            }
        }

        fun clearLit(){
            activityList.forEach { it?.finish() }
            activityList.clear()
        }
    }

}