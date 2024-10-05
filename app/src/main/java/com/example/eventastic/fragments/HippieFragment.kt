package com.example.eventastic.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.eventastic.R
import com.example.eventastic.booking

class HippieFragment : Fragment() {

    private lateinit var bookBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_hippie, container, false)

        // Initialize the button
        bookBtn = view.findViewById(R.id.book_now_button)

        // Set click listener for the "Book Now" button
        bookBtn.setOnClickListener {
            // Navigate to booking activity
            val intent = Intent(requireContext(), booking::class.java)
            startActivity(intent)
        }

        return view
    }
}
