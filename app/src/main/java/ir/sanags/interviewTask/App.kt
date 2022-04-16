package ir.sanags.interviewTask

import android.app.Application
import ir.sanags.interviewTask.di.Container

class App : Application() {


    val injection = Container(this)
}