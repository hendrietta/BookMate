package org.wit.bookmate.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class BookMemStore : BookStore, AnkoLogger {

    val books = ArrayList<BookModel>()

    override fun findAll(): List<BookModel> {
        return books
    }

    override fun create(book: BookModel) {
        book.id = getId()
        books.add(book)
        logAll()
    }

    override fun update(book: BookModel) {
        var foundBook: BookModel? = books.find { p -> p.id == book.id }
        if (foundBook != null) {
            foundBook.title = book.title
            foundBook.description = book.description
            foundBook.image = book.image
            logAll();
        }
    }


    override fun delete(book: BookModel) {
        books.remove(book)
    }


    fun logAll() {
        books.forEach { info("${it}") }
    }
}