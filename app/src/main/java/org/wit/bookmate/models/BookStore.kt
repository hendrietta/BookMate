package org.wit.bookmate.models

interface BookStore {
    fun findAll(): List<BookModel>
    fun create(book: BookModel)
    fun update(book: BookModel)
    fun delete(book: BookModel)

}