package edu.gwu.horizons

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

//returns albums based on search parameters, with that parameter being either album or artist
//in it, you want to find put a button to add that album that will add it to firebase. A floating action button, if you will.
class ReturnActivity : AppCompatActivity() {

    private val discogsManager: DiscogsManager = DiscogsManager()   //this is the manager for the API call and what not
    private val albumsList: MutableList<Album> = mutableListOf()    //list of albums that will be returned

    private lateinit var searchArtist: EditText
    private lateinit var search: Button
    private lateinit var add: FloatingActionButton
    private lateinit var firebaseDatabase: FirebaseDatabase

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
        add = findViewById(R.id.add)


        firebaseDatabase = FirebaseDatabase.getInstance()

        searchArtist.addTextChangedListener(textWatcher)

        val reference = firebaseDatabase.getReference("albums")

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

        add.setOnClickListener {    //for now, shows an alert dialog and adds a sample of albums
            val arrayAdapter = ArrayAdapter<Album>(this, android.R.layout.select_dialog_singlechoice)
            arrayAdapter.addAll(albumsList) //setup for the radio adapter of albums

            val email: String = FirebaseAuth.getInstance().currentUser!!.email!!
            val albums = generateTestAlbums(email)

            AlertDialog.Builder(this)

                .setTitle("Choose Album")
                .setAdapter(arrayAdapter) { _, _ -> //this is the radio menu for all the choices you'll see

                }
                .setNegativeButton("CANCEL") { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton("OKAY") { _, _ ->    //will add the album that you select, for now will add the preset albums
                    for (i in albums) {
                        reference.push().setValue(i)
                    }
                }
                .show()
        }

        title = getString(R.string.search)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("ALBUMS", ArrayList(albumsList))
    }

    private fun generateTestAlbums(email: String): List<Album> {
        return listOf(
            Album(
                title = "Circa Survive - Juturna",
                style = "Emo",
                user = email
            ),
            Album(
                title = "Periphery - Periphery II: This Time It's Personal",
                style = "Progressive Metal",
                user = email
            ),
            Album(
                title = "The Contortionist - Language",
                style = "Progressive Metal",
                user = email
            ),
            Album(
                title = "We Butter the Bread with Butter - Goldkinder",
                style = "Deathcore",
                user = email
            ),
            Album(
                title = "Woe, is Me - Number[s]",
                style = "Metalcore",
                user = email
            )
        )
    }

}