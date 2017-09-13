package com.example.lorakotlin.network

import android.util.Log
import com.example.lorakotlin.constant.Constant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * 创建时间: 2017/8/23
 * 创建人: dell
 * 描述: //TODO
 * 版本: V1.0
 */
class RetrofitUtil {

    companion object{
        private var okHttpClient:OkHttpClient?=null
//        private lateinit var retrofit:Retrofit

        fun getDefaultOkHttpClient():OkHttpClient{
            if(okHttpClient==null){
                okHttpClient = OkHttpClient.Builder()
                        .retryOnConnectionFailure(true)
                        .addInterceptor(LoggingInterceptor())
                        .addNetworkInterceptor(LoggingNetInterceptor())
                        .build()
            }
            return okHttpClient!!
        }

        fun getDefaultRetrofit():Retrofit{
            val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .client(getDefaultOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
            return retrofit
        }

        fun getRetrofit(url: String): Retrofit {
            val retrofit = Retrofit.Builder()
                    .baseUrl(url)
                    .client(getDefaultOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
            return retrofit
        }
    }

    class LoggingInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()

            val t1 = System.nanoTime()
            Log.d("retrofit", "intercept:" + String.format("发送请求: [%s] %s%n%s",
                    request.url(), chain.connection(), request.headers()))

            val response = chain.proceed(request)

            val t2 = System.nanoTime()
            Log.d("retrofit", "intercept:" + String.format("接收响应: [%s] %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6, response.headers()))

            return response
        }
    }

    class LoggingNetInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()

            val t1 = System.nanoTime()
            Log.d("retrofit", "net_intercept: " + String.format("发送请求: [%s] %s%n%s",
                    request.url(), chain.connection(), request.headers()))

            val response = chain.proceed(request)

            val t2 = System.nanoTime()
            Log.d("retrofit", "net_intercept: " + String.format("接收响应: [%s] %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6, response.headers()))

            return response
        }
    }

}