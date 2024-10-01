package com.example.eventastic

import android.os.Bundle
import android.text.TextUtils.replace
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.eventastic.databinding.ActivityBottomnavAndFragmentsBinding

class Bottomnav_and_fragments : AppCompatActivity() {
    private lateinit var binding: ActivityBottomnavAndFragmentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBottomnavAndFragmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replace(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.home -> replace(HomeFragment())
                R.id.profile -> replace(ProfileFragment())
                R.id.explore -> replace(exploreFragment())
                R.id.maps -> replace(MapsFragment())

                else -> {
                }

            }
            true
        }

    }
    fun replace(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}