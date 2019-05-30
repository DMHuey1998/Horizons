package edu.gwu.horizons

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    private lateinit var signup: Button
    private lateinit var remember: CheckBox

    private lateinit var firebaseAuth: FirebaseAuth

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            val inputtedUsername: String = username.text.toString().trim()
            val inputtedPassword: String = password.text.toString().trim()
            val enableButton: Boolean = inputtedUsername.isNotEmpty() && inputtedPassword.isNotEmpty()

            login.isEnabled = enableButton
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = getSharedPreferences("horizons", Context.MODE_PRIVATE) //sharedPreferences so far

        firebaseAuth = FirebaseAuth.getInstance()

        signup = findViewById(R.id.signup)

        Log.d("MainActivity", "onCreate called")

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)
        remember = findViewById(R.id.remember)

        username.addTextChangedListener(textWatcher)
        password.addTextChangedListener(textWatcher)

        //Restore the sharedPreferences here
        val savedUsername = prefs.getString("USERNAME", "")
        val savedPassword = prefs.getString("PASSWORD", "")

        username.setText(savedUsername)
        password.setText(savedPassword)

        signup.setOnClickListener {

            val signUpIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signUpIntent)

        }

        login.setOnClickListener {
            val inputtedUsername: String = username.text.toString().trim()
            val inputtedPassword: String = password.text.toString().trim()

            firebaseAuth.signInWithEmailAndPassword(
                inputtedUsername,
                inputtedPassword
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {	//checks if the email/password combo is right or wrong

                    val currentUser: FirebaseUser? = firebaseAuth.currentUser
                    Toast.makeText(
                        this,
                        "Logged in as: ${currentUser!!.email}",
                        Toast.LENGTH_LONG
                    ).show()

                    remember.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            prefs.edit().putString("USERNAME", inputtedUsername).apply()
                            prefs.edit().putString("PASSWORD", inputtedPassword).apply()
                        }
                    }

                    val selectIntent = Intent(this, SelectActivity::class.java)
                    startActivity(selectIntent)

                } else {
                    val exception = task.exception
                    Toast.makeText(
                        this,
                        "Failed to login: $exception",
                        Toast.LENGTH_LONG
                    ).show()

                    val reason: String = if (exception is FirebaseAuthInvalidCredentialsException) {
                        "invalid_credentials"
                    } else {
                        "generic_failure"
                    }

                    val bundle = Bundle()
                    bundle.putString("error_reason", reason)

                }
            }


        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart called")


    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy called")

        remember = findViewById(R.id.remember)
        username = findViewById(R.id.username)

        val prefs = getSharedPreferences("horizons", Context.MODE_PRIVATE) //sharedPreferences so far
        val inputtedUsername = username.text.toString().trim()
        val inputtedPassword = password.text.toString().trim()

        remember.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                prefs.edit().putString("USERNAME", inputtedUsername).apply()
                prefs.edit().putString("PASSWORD", inputtedPassword).apply()
            }
        }

    }
}