package ir.sanags.interviewTask.data.api

import ir.sanags.interviewTask.data.api.address.AddressResponse
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

open class BaseRepository {

    open suspend fun <T : Any> fetch(
        call: Response<T>,
        onSuccess: (T?) -> Unit,
        onFailure: (message: String?) -> Unit
    ) {
        try {
            call.let {
                if (it.isSuccessful) {
                    onSuccess(it.body())
                } else {
                    when (it.code()) {
                        500 -> onFailure("خطا در سرور")
                        else -> onFailure("خطا در درخواست")
                    }
                }
            }
        } catch (e: IOException) {
            if (e is SocketTimeoutException) {
                onFailure("زمان درخواست تمام شد. مجددا تلاش بفرمایید")
            } else {
                onFailure(e.message)
            }
        }
    }
}