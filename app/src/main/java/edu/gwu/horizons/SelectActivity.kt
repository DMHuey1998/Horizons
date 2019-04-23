package edu.gwu.horizons

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class SelectActivity : AppCompatActivity() {

    private lateinit var myalbums: Button
    private lateinit var recommend: Button
    private lateinit var logout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        myalbums = findViewById(R.id.myalbums)
        recommend = findViewById(R.id.recommend)
        logout = findViewById(R.id.logout)

        myalbums.setOnClickListener {

        }

        logout.setOnClickListener {//this is for when you want to log out of the app
            val logoutIntent = Intent(this, MainActivity::class.java)
            startActivity(logoutIntent)
        }

    }
}

//private fun generateFakeAlbums with JSON Data type for each album here
//I'll make a data type Album for that
//note: return cd, vinyl, etc.