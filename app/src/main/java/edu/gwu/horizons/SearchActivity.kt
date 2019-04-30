package edu.gwu.horizons

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText

class SearchActivity : AppCompatActivity() {

    private lateinit var searchContent: EditText
    private lateinit var search: Button

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            val inputtedSearch: String = searchContent.text.toString().trim()
            val enableButton: Boolean = inputtedSearch.isNotEmpty()

            search.isEnabled = enableButton
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchContent = findViewById(R.id.searchContent)
        search = findViewById(R.id.search)


        search.setOnClickListener {
            val inputtedSearch = searchContent.text.toString()  //parameters for album/artist search
            val returnIntent = Intent(this, ReturnActivity::class.java)
            startActivity(returnIntent)

        }

    }
}