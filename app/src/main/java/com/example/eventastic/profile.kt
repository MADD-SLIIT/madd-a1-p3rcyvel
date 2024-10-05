package com.example.eventastic

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.eventastic.data_class.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class profile : AppCompatActivity() {

    lateinit var gname: TextView
    lateinit var gmail: TextView
    lateinit var glastname: TextView

    lateinit var bckBtn: Button
    lateinit var Edtbtn: Button
    lateinit var deltbtn: Button

    private lateinit var firebaseauth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    private lateinit var userlist: ArrayList<User>

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        gname = findViewById(R.id.profnametxt)
        gmail = findViewById(R.id.profemailtxt)
        glastname = findViewById(R.id.profLastnametxt)

        bckBtn = findViewById(R.id.bck_btn)
        Edtbtn = findViewById(R.id.edtbtn)
        deltbtn = findViewById(R.id.dltbtn)

        firebaseauth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().getReference()

        userlist = ArrayList()

        val uid = firebaseauth.currentUser?.uid.toString()
        listenforprofilechange(uid)

        supportActionBar?.setDisplayShowHomeEnabled(true)

        bckBtn.setOnClickListener {
            onBackPressed()
        }

        Edtbtn.setOnClickListener {
            directEdtprofile()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragmentManager = supportFragmentManager
                if (fragmentManager.backStackEntryCount > 0) {
                    fragmentManager.popBackStack() // Pop the top fragment from the back stack
                } else {
                    finish()  // No fragments left, finish the activity
                }
            }
        })

    }

    private fun directEdtprofile() {
        val intent = Intent(this, EditProfileActivity::class.java)
        startActivity(intent)
    }

    private fun listenforprofilechange(uid: String) {
        databaseRef.child("Users").child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.getValue(User::class.java)
                    val email = snapshot.getValue(User::class.java)
                    val lastname = snapshot.getValue(User::class.java)
                    val user = snapshot.getValue(User::class.java)

                    if (user != null) {
                        gname.text = name?.firstname
                        gmail.text = email?.email
                        glastname.text = lastname?.lastname
                    } else {
                        Toast.makeText(this@profile, "user data not found", Toast.LENGTH_SHORT)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }


    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack() // Pop the top fragment from the back stack
        } else {
            super.onBackPressed() // Default back behavior
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.homeFragment, fragment)
            .addToBackStack(null) // Add to back stack to allow back navigatioz
            .commit()
    }

}