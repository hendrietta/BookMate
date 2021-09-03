package org.wit.bookmate.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.wit.bookmate.R
import org.wit.bookmate.helpers.readImageFromPath
import org.wit.bookmate.models.BookModel

interface BookListener {
    fun onBookClick(book: BookModel)
}

class BookAdapter constructor(private var books: List<BookModel>,
                               private val listener: BookListener) : RecyclerView.Adapter<BookAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_book, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val book= books[holder.adapterPosition]
        holder.bind(book, listener)
    }

    override fun getItemCount(): Int = books.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(book: BookModel,  listener : BookListener) {

        }
    }
}