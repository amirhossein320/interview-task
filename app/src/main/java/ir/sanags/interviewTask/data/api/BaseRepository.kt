package ir.sanags.interviewTask.data.api

import retrofit2.Call
import retrofit2.Response
import java.net.SocketTimeoutException

open class BaseRepository {

    open  suspend fun <T : Any> fetch(
        call: suspend () -> Response<T>,
        onSuccess: suspend (T) -> Unit,
        onFailure: suspend (message: String) -> Unit
    ) {
        try {
            call.invoke().let {
                if (it.isSuccessful) {
                    it.body()?.let { data -> onSuccess(data) }
                } else {
                    when (it.code()) {
                        500 -> onFailure("خطا در سرور")
                        else -> onFailure("خطا در درخواست")
                    }
                }
            }
        } catch (e: Exception) {
            if (e is SocketTimeoutException) {
                onFailure("زمان درخواست تمام شد. مجددا تلاش بفرمایید")
            } else {
                e.message?.let { onFailure(it) }
            }
        }
    }
}