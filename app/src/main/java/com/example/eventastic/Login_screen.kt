package com.example.eventastic

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.eventastic.databinding.ActivityLoginScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Login_screen : AppCompatActivity() {

    private lateinit var binding: ActivityLoginScreenBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference

        // Set onClick listener for login button
        binding.logbtn.setOnClickListener {
            val email = binding.editemail.text.toString().trim()
            val password = binding.editpassword.text.toString().trim()

            // Ensure both email and password fields are not empty
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Please fill in all the details", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigate to registration screen
        binding.navCreatebtn.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun loginUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val uid = firebaseAuth.currentUser?.uid
                    if (uid != null) {
                        fetchUserData(uid)
                    } else {
                        Toast.makeText(this, "Error retrieving user info", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Handle failed login
                    Toast.makeText(this, "Login failed. Please check your credentials", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Login failed. ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Navigate to home after successful login
    private fun loginHome() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()  // Close login activity
    }

    // Fetch user data from Firebase and welcome the user
    private fun fetchUserData(uid: String) {
        databaseRef.child("Users").child(uid).get().addOnSuccessListener {
            if (it.exists()) {
                val firstName = it.child("firstname").value
                Toast.makeText(this, "Welcome, $firstName! ", Toast.LENGTH_SHORT).show()
                loginHome()  // Redirect to home after fetching data
            } else {
                Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show()
        }
    }

    // Navigate to the registration screen (fixed the wrong activity reference)
    private fun navigateToRegister() {
        val intent = Intent(this, create_account::class.java)  // Corrected to registration activity
        startActivity(intent)
    }
}
