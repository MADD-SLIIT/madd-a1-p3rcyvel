package com.example.eventastic

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.eventastic.data_class.User
import com.example.eventastic.databinding.ActivityCreateAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class create_account : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding
    private lateinit var firebaseauth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseauth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference

        binding.createbtn.setOnClickListener {
            val firstname = binding.etName.text.toString()
            val lastname = binding.etLastName.text.toString()
            val email = binding.etEmail.text.toString()
            val pw = binding.etPswd.text.toString()

            if (firstname.isNotEmpty() && lastname.isNotEmpty() && email.isNotEmpty() && pw.isNotEmpty()) {
                signupUser(firstname, lastname, email, pw)
            } else {
                Toast.makeText(this, "please fill all the details", Toast.LENGTH_SHORT).show()
            }
        }

        binding.navtoSignIN.setOnClickListener {
            navtoLogin()
        }

    }

    private fun navtoLogin() {
        val intent = Intent(this, Login_screen::class.java)
        startActivity(intent)
    }

    private fun signupUser(firstname: String, lastname: String, email: String, pw: String) {
        firebaseauth.createUserWithEmailAndPassword(email, pw)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val uid = firebaseauth.currentUser?.uid
                    if (uid != null){
                        addusertoDatabase(firstname, lastname,uid,email,pw)
                    }
                    Toast.makeText(this, "sign up success", Toast.LENGTH_SHORT).show()
                    directToHome()
                } else {
                    Toast.makeText(this, "sign up failed ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun directToHome() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }

    private fun addusertoDatabase(
        firstname: String,
        lastname: String,
        uid: String,
        email: String,
        password: String
    ) {
        val user = User(firstname, lastname, uid, email, password)
        databaseRef.child("Users").child(uid).setValue(user)
    }
}