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

        if (prefs.getBoolean("REMEMBER", false)) {
            username.addTextChangedListener(textWatcher)
            password.addTextChangedListener(textWatcher)
        } else {
            username.setText(prefs.getString("SAVED_USERNAME", ""))
            password.setText(prefs.getString("SAVED_PASSWORD", ""))
        }

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
                if (task.isSuccessful) {

                    val currentUser: FirebaseUser? = firebaseAuth.currentUser
                    Toast.makeText(
                        this,
                        "Logged in as: ${currentUser!!.email}",
                        Toast.LENGTH_LONG
                    ).show()

                    //this thing is what is giving me grief right now, sad face. Worry about this stuff on the final build.
                    /*remember.setOnCheckedChangeListener { _, isChecked ->   //sharedpreferences for the remember checkbox
                        if (isChecked) {
                            prefs.edit().putString("SAVED_USERNAME", inputtedUsername).apply()
                            prefs.edit().putString("SAVED_PASSWORD", inputtedPassword).apply()
                            prefs.edit().putBoolean("REMEMBER", true).apply()
                        }

                    }*/
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
    }
}