package org.wit.bookmate.main


import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.bookmate.models.BookJSONStore
import org.wit.bookmate.models.BookStore


class MainApp : Application(), AnkoLogger {


    lateinit var books: BookStore



    override fun onCreate() {
        super.onCreate()
        books = BookJSONStore(applicationContext)
        info("App started")
    }
}
