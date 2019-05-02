package edu.gwu.horizons

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyAlbumActivity: AppCompatActivity() {    //the activity that lists the albums you have saved under your account

    private lateinit var recyclerView: RecyclerView
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var show: Button

    //this will take the albums you have saved and use the albumadapter in the recyclerview that we also used for search
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        firebaseDatabase = FirebaseDatabase.getInstance()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        show = findViewById(R.id.show)

        show.setOnClickListener {
            //read in the firebase data, then it's gonna return it but for now we'll just show the fake data because presentation is soon

        }

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