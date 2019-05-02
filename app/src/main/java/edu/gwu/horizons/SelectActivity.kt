package edu.gwu.horizons

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SelectActivity : AppCompatActivity() {

    private lateinit var myalbums: Button
    private lateinit var logout: Button
    private lateinit var search: Button
    private lateinit var test: Button

    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        myalbums = findViewById(R.id.myalbums)
        logout = findViewById(R.id.logout)
        search = findViewById(R.id.searchContent)
        test = findViewById(R.id.test)

        firebaseDatabase = FirebaseDatabase.getInstance()

        val reference = firebaseDatabase.getReference("albums")

        myalbums.setOnClickListener {
            val albumIntent = Intent(this, MyAlbumActivity::class.java)
            startActivity(albumIntent)
        }

        search.setOnClickListener {
            val returnIntent = Intent(this, ReturnActivity::class.java)
            startActivity(returnIntent)
        }

        logout.setOnClickListener {//this is for when you want to log out of the app
            val logoutIntent = Intent(this, MainActivity::class.java)
            startActivity(logoutIntent)
        }

        test.setOnClickListener {
            val email: String = FirebaseAuth.getInstance().currentUser!!.email!!

            val album = Album (
                title = "Circa Survive",
                style = "Emo",
                user = email
            )

            reference.push().setValue(album)
        }

    }
}