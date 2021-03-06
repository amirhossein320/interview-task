package ir.sanags.interviewTask.data.api

import android.content.Context
import ir.sanags.interviewTask.BuildConfig
import ir.sanags.interviewTask.util.NoConnectivityException
import ir.sanags.interviewTask.util.hasNetwork
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

class RetrofitInitializer {

    fun retrofit(context: Context) = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .client(httpClient(context))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun httpClient(context: Context) = OkHttpClient.Builder()
        .callTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor {
            if (context.hasNetwork()) it.proceed(it.request())
            else throw NoConnectivityException()
        }
        .addInterceptor(AuthInterceptor())
        .build()
}

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val baseRequest = chain.request()
        val request :Request.Builder
        try {
             request = baseRequest.newBuilder()
                .header(
                    "Authorization",
                    Credentials.basic(BuildConfig.AUTH_USERNAME, BuildConfig.AUTH_PASS)
                )
            return chain.proceed(request.build())
        } catch (e: SocketTimeoutException ) {
            throw SocketTimeoutException("زمان درخواست تمام شد. مجددا تلاش بفرمایید")
        }
    }
}

