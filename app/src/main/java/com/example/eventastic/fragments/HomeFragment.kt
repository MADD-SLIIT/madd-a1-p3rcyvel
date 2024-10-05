package com.example.eventastic.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

import com.example.eventastic.R
import com.example.eventastic.profile

class HomeFragment : Fragment() {

    private lateinit var hippiebtn : Button
    private lateinit var usrbtn : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        hippiebtn = view.findViewById(R.id.text_below_card_1)
        usrbtn = view.findViewById(R.id.usernametxtbtn)


        usrbtn.setOnClickListener {
            val intent = Intent(activity,profile::class.java)
            startActivity(intent)
        }

        hippiebtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_hippieFragment)

        }

        return view

    }


}