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

        val albumsList: MutableList<Album> = mutableListOf()

        firebaseDatabase = FirebaseDatabase.getInstance()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        show = findViewById(R.id.show)

        show.setOnClickListener {
            val reference = firebaseDatabase.getReference("albums")

            reference.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(
                        this@MyAlbumActivity,
                        "Failed to retrieve albums! Error: ${databaseError.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    albumsList.clear()

                    dataSnapshot.children.forEach { data ->
                        val email: String = FirebaseAuth.getInstance().currentUser!!.email!!    //gets the email for checking the parameter to get the albums
                        val album = data.getValue(Album::class.java)

                        if (album != null) {
                            albumsList.add(album)
                        }
                    }

                    recyclerView.adapter = AlbumsAdapter(albumsList)
                }
            })
        }

    }

}