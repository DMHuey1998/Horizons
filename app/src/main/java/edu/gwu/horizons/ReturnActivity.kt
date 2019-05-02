package edu.gwu.horizons

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

//returns albums based on search parameters, with that parameter being either album or artist
//in it, you want to find put a button to add that album that will add it to firebase. A floating action button, if you will.
class ReturnActivity : AppCompatActivity() {

    private val discogsManager: DiscogsManager = DiscogsManager()   //this is the manager for the API call and what not
    private val albumsList: MutableList<Album> = mutableListOf()    //list of albums that will be returned

    private lateinit var searchArtist: EditText
    private lateinit var search: Button

    private lateinit var recyclerView: RecyclerView

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            val inputtedSearch: String = searchArtist.text.toString().trim()
            val enableButton: Boolean = inputtedSearch.isNotEmpty()

            search.isEnabled = enableButton
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_return)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        searchArtist = findViewById(R.id.searchArtist)
        search = findViewById(R.id.search)

        searchArtist.addTextChangedListener(textWatcher)

        search.setOnClickListener {

            val inputtedSearch: String = searchArtist.text.toString().trim()

            discogsManager.searchAlbums(
                query = inputtedSearch,   //takes the searchContent variable from searchActivity
                successCallback = { albums ->

                    albumsList.clear()
                    albumsList.addAll(albums)

                    runOnUiThread {
                        recyclerView.adapter =
                            AlbumsAdapter(albums)
                    }

                },
                errorCallback = {
                    Toast.makeText(this@ReturnActivity, "Error retrieving Albums!", Toast.LENGTH_LONG).show()
                }
            )
        }

        if (savedInstanceState != null) {
            // The screen has rotated, so we should retrieve the previous Tweets
            val previousAlbums: List<Album> = savedInstanceState.getSerializable("TWEETS") as List<Album>
            albumsList.addAll(previousAlbums)

            recyclerView.visibility = View.INVISIBLE
            recyclerView.adapter = AlbumsAdapter(albumsList)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("ALBUMS", ArrayList(albumsList))
    }

}