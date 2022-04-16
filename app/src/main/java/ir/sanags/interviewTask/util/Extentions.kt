package ir.sanags.interviewTask.util

import android.text.TextUtils
import android.view.View
import android.widget.EditText
import androidx.core.view.isVisible


fun View.gone() {
    isVisible = false
}

fun View.visible() {
    isVisible = true
}

fun EditText.clear() {
    setText("")
}

fun EditText.isEmpty() = TextUtils.isEmpty(text)
