package com.example.kotlintemplate.network.retrofit

import android.annotation.SuppressLint
import com.example.kotlintemplate.common.Constant
import com.example.kotlintemplate.util.GLog
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@SuppressLint("StaticFieldLeak")
class RetrofitManager {
    private val RETROFIT_TIME_OUT: Long = 10

    companion object {
        private var instance: RetrofitManager? = null

        fun getInstance(): RetrofitManager {
            if (instance == null) {
                instance = RetrofitManager()
            }
            return instance as RetrofitManager
        }

    }

    fun createApiService(): RetrofitApiService {
        return buildRetrofit().create(RetrofitApiService::class.java)
    }

    private fun buildRetrofit(): Retrofit {
        val builder = OkHttpClient.Builder()
            .connectTimeout(RETROFIT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(RETROFIT_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(RETROFIT_TIME_OUT, TimeUnit.SECONDS)

        // Log Interceptor 추가.
        val logging = RetrofitLogInterceptor()
        logging.setLevel(RetrofitLogInterceptor.Level.BODY)
        builder.addInterceptor(logging).build()

        // 특정 상황 헤더 추가 필요 시 하기 항목 주석 해제 후 사용
//        builder.addInterceptor { chain ->
//            val origin = chain.request()
//            val requestBuilder = origin.newBuilder()
//
//            chain.proceed(requestBuilder.build())
//        }

        @SuppressLint("CustomX509TrustManager")
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        })

        try {
            val sc = SSLContext.getInstance("TLS")
            sc.init(null, trustAllCerts, null)
            builder.sslSocketFactory(sc.socketFactory)
            builder.hostnameVerifier(HostnameVerifier { hostname, session ->
                true
            })
        } catch (e: java.lang.Exception) {
            GLog.e("RetrofitHostName verifier exception : ${e.message}")
        }

        val gson = GsonBuilder().setLenient().create()

        val retrofitBuilder = Retrofit.Builder()
        retrofitBuilder.baseUrl(Constant.BASE_URL)
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create(gson))
        retrofitBuilder.client(builder.build())

        return retrofitBuilder.build()
    }
}