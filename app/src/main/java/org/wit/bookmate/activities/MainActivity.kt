package org.wit.bookmate.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import org.jetbrains.anko.info
import org.wit.bookmate.R
import org.wit.bookmate.helpers.readImage
import org.wit.bookmate.helpers.readImageFromPath
import org.wit.bookmate.helpers.showImagePicker
import org.wit.bookmate.main.MainApp
import org.wit.bookmate.models.BookModel

class MainActivity {
    var edit = false
    val IMAGE_REQUEST = 1
    var book = BookModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        app = application as MainApp

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)


        if (intent.hasExtra("book_edit")) {
            edit = true
            book = intent.extras?.getParcelable<BookModel>("book_edit")!!
            bookTitle.setText(book.title)
            description.setText(book.description)
            runtime.setText(book.runtime)
            trailer.setText(book.trailer)
            bookImage.setImageBitmap(readImageFromPath(this, book.image))
            if (book.image != null) {
                chooseImage.setText(R.string.change_book_image)
            }
            btnAdd.setText(R.string.save_book)
        }


        btnAdd.setOnClickListener() {
            book.title = bookTitle.text.toString()
            book.description = description.text.toString()
            book.runtime = runtime.text.toString()
            book.trailer = trailer.text.toString()
            if (book.title.isEmpty()) {
                toast(R.string.enter_book_title)
            }
            else if (book.description.isEmpty()) {
                toast(R.string.enter_book_description)
            }
            else if (book.runtime.isEmpty()) {
                toast(R.string.enter_book_runtime)
            }
            else if (book.trailer.isEmpty()) {
                toast(R.string.enter_book_trailer)
            }
            else {
                if (edit) {
                    app.books.update(book.copy())
                } else {
                    app.books.create(book.copy())
                }
            }
            info("add Button Pressed: $bookTitle")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }


        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_book, menu)
        if (edit && menu != null) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                app.books.delete(book)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    book.image = data.getData().toString()
                    bookImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_book_image)
                }
            }
        }
    }
}
