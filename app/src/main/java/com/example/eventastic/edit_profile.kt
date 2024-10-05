package com.example.eventastic

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eventastic.data_class.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EditProfileActivity : AppCompatActivity() {

    private lateinit var firstNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var lastNameEditText: EditText

    private lateinit var backButton: Button
    private lateinit var saveButton: Button

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private lateinit var userList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // Initialize views
        firstNameEditText = findViewById(R.id.edtnaem)
        emailEditText = findViewById(R.id.edtmail)
        lastNameEditText = findViewById(R.id.edtlastname)

        backButton = findViewById(R.id.bck_to_profile_btn)
        saveButton = findViewById(R.id.savebtn)

        // Initialize Firebase Auth and Database
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        userList = ArrayList()

        retrieveUserProfile()

        saveButton.setOnClickListener {
            updateProfile()
        }
    }

    private fun updateProfile() {
        val firstName = firstNameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()

        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val user = User(firstName, lastName, userId, email)

            // Update user profile in Firebase
            databaseReference.child(userId)
                .setValue(user)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT)
                            .show()
                        // Corrected this line to reference ProfileActivity
                        val intent = Intent(this, profile::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun retrieveUserProfile() {
        val currentUser = firebaseAuth.currentUser
        currentUser?.let {
            val uid = it.uid

            databaseReference.child(uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val firstName = snapshot.child("firstname").value.toString()
                        val email = snapshot.child("email").value.toString()
                        val lastName = snapshot.child("lastname").value.toString()

                        // Set the retrieved data to EditTexts
                        firstNameEditText.setText(firstName)
                        emailEditText.setText(email)
                        lastNameEditText.setText(lastName)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@EditProfileActivity,
                            "Error: ${error.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }
}
