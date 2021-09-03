package org.wit.bookmate.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.bookmate.helpers.exists
import org.wit.bookmate.helpers.read
import org.wit.bookmate.helpers.write
import java.util.*

val JSON_FILE = "books.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<BookModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class BookJSONStore : BookStore, AnkoLogger {

    val context: Context
    var books = mutableListOf<BookModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<BookModel> {
        return books
    }

     override fun create(book: BookModel) {
        book.id = generateRandomId()
         books.add(book)
        serialize()
    }


     override fun update(book: BookModel) {
        val booksList = findAll() as ArrayList<BookModel>
        var foundBook: BookModel? = booksList.find { p -> p.id == book.id }
        if (foundBook != null) {
            foundBook.title = book.title
            foundBook.description = book.description
            foundBook.author= book.author
            foundBook.image =book.image
        }
        serialize()
    }


     override fun delete(book: BookModel) {
        books.remove(book)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(books, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        books = Gson().fromJson(jsonString, listType)
    }
}