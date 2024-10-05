package com.example.eventastic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class booking : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking) // Make sure this layout file exists and is not blank

        // Find the "Next" button by its ID
        val nextButton = findViewById<Button>(R.id.btn_next)

        // Set a click listener on the "Next" button
        nextButton.setOnClickListener {
            // Create an intent to navigate to the PayMethod activity
            val intent = Intent(this@booking, paymethod::class.java)
            startActivity(intent) // Start the PayMethod activity
        }
    }
}
