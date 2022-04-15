package ir.sanags.interviewTask.data.api

import android.content.Context
import ir.sanags.interviewTask.BuildConfig
import ir.sanags.interviewTask.util.NoConnectivityException
import ir.sanags.interviewTask.util.hasNetwork
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInitializer {

    fun retrofit(context: Context) = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .client(httpClient(context))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun httpClient(context: Context) = OkHttpClient.Builder()
        .callTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor())
        .addInterceptor {
            if (context.hasNetwork()) it.proceed(it.request())
            else throw NoConnectivityException()
        }
        .build()
}

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val baseRequest = chain.request()
        val request = baseRequest.newBuilder()
            .header(
                "Authorization",
                Credentials.basic(BuildConfig.AUTH_USERNAME, BuildConfig.AUTH_PASS)
            )
        return chain.proceed(request.build())
    }
}

