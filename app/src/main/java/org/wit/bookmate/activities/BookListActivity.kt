package org.wit.bookmate.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import org.wit.bookmate.R
import org.wit.bookmate.main.MainApp
import org.wit.bookmate.models.BookModel

class BookListActivity {

    lateinit var app: MainApp

    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitybooklist)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        loadBooks()

        toolbarMain.title = title
        setSupportActionBar(toolbarMain)
    }

    fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


   fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<MainActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }


    fun onBookClick(book: BookModel) {
        startActivityForResult(intentFor<MainActivity>().putExtra("book_edit", book), 0)
    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        loadBooks()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadBooks() {
        showBooks(app.books.findAll())
    }

    fun showBooks(books: List<BookModel>) {
        recyclerView.adapter = BookAdapter(books, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}
