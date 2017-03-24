package com.shpt.core.api

import android.content.Context
import android.util.Log
import com.shpt.core.app.BUS
import com.shpt.core.config.Config
import com.shpt.core.prefs.Prefs
import com.shpt.core.rest.Rest
import com.shpt.core.serviceevent.RetryServiceEvent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by poovarasanv on 6/2/17.
 * @author poovarasanv
 * @project SHPT
 * @on 6/2/17 at 9:07 AM
 */
fun Context.getAdapter(): Rest {

    if (Prefs.with(this).contains(Config.COOKIE)) {

        Log.i("cookies", Prefs.with(this).read(Config.COOKIE))


        val client = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(CookieInterceptor(Prefs.with(this).read(Config.COOKIE)))
                .addInterceptor(CacheInterceptor())
                .addInterceptor(RetryInterceptor())
                .build()


        val retrofit = Retrofit.Builder()
                .baseUrl(Config.BASE)
                .client(client)
                .build()

        return retrofit.create(Rest::class.java)

    } else {

        val client = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(CacheInterceptor())
                .addInterceptor(RetryInterceptor())
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(Config.BASE)
                .client(client)
                .build()

        return retrofit.create(Rest::class.java)

    }
}

class CookieInterceptor(cookie: String) : Interceptor {

    var cookies: String = cookie
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        builder.addHeader("Cookie", "PHPSESSID=${cookies}; display=list")
        builder.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36 OPR/39.0.2256.71")
        return chain.proceed(builder.build())
    }

}

class RetryInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain!!.request()

        // try the request
        var response = chain.proceed(request)

        var tryCount = 0
        while (!response.isSuccessful && tryCount < 3) {
            Log.d("intercept", "Request is not successful - " + tryCount)
            tryCount++

            BUS.post(RetryServiceEvent(true, "Retrying to server"))
            response = chain.proceed(request)
        }
        return response
    }
}

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val original = chain!!.request()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Cache-Control", "no-store")

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}


val Context.rest: Rest
    get() = getAdapter()