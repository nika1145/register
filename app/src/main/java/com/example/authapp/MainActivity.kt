package com.example.authapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var textView2: EditText
    private lateinit var textView3: EditText
    private lateinit var textView: EditText
    private lateinit var buttonlogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        registerListeners()
    }
    private fun init(){
        textView = findViewById(R.id.textView)
        textView2 = findViewById(R.id.textView2)
        textView3 = findViewById(R.id.textView3)
        buttonlogin = findViewById(R.id.buttonlogin)
    }
    private fun registerListeners(){
        buttonlogin.setOnClickListener {
            val email = textView.text.toString()
            val password = textView2.text.toString()
            val password2 = textView3.text.toString()
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this,"Email incorrect!", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
            if(password.length < 9) {
                Toast.makeText(this,"Password should have at least 9 characters!", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
            if(!password.contains("[0-9]".toRegex()) || !password.contains("[a-z]".toRegex()) ||
                password.contains("[A-Z]".toRegex())) {
                Toast.makeText(this,"Password should contain letters and numbers!", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
            if(password != password2) {
                Toast.makeText(this,"Passwords do not match!", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java));
                        Toast.makeText(this,"User Registered Successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this,"Internal Error!", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }
}