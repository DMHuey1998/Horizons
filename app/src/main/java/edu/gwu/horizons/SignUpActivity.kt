package edu.gwu.horizons

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity: AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var confirm: EditText
    private lateinit var submit: Button
    private lateinit var back: Button

    private lateinit var firebaseAuth: FirebaseAuth
    //private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            val inputtedUsername: String = username.text.toString().trim()
            val inputtedPassword: String = password.text.toString().trim()
            val inputtedConfirm: String = confirm.text.toString().trim()
            val enableButton: Boolean = inputtedUsername.isNotEmpty() && inputtedPassword.isNotEmpty() && inputtedConfirm.isNotEmpty()

            submit.isEnabled = enableButton
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        firebaseAuth = FirebaseAuth.getInstance()

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        confirm = findViewById(R.id.confirm)
        submit = findViewById(R.id.submit)
        back = findViewById(R.id.back)

        username.addTextChangedListener(textWatcher)
        password.addTextChangedListener(textWatcher)
        confirm.addTextChangedListener(textWatcher)

        //Crashlytics.getInstance().crash()

        submit.setOnClickListener {

            val inputtedUsername: String = username.text.toString().trim()
            val inputtedPassword: String = password.text.toString().trim()
            val inputtedConfirm: String = confirm.text.toString().trim()

            if (inputtedPassword == inputtedConfirm) {
                firebaseAuth.createUserWithEmailAndPassword(
                    inputtedUsername,
                    inputtedPassword
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val currentUser: FirebaseUser? = firebaseAuth.currentUser
                        Toast.makeText(
                            this,
                            "Registered as: ${currentUser!!.email}",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val exception = task.exception
                        Toast.makeText(
                            this,
                            "Failed to register: $exception",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else if (inputtedPassword != inputtedConfirm) {   //this will display if passwords do not match
                Toast.makeText(
                    this,
                    "Passwords must match!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        back.setOnClickListener {
            val loginIntent = Intent(this, MainActivity::class.java)
            startActivity(loginIntent)
        }
    }
}