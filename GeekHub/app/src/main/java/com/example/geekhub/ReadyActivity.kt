package com.example.geekhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.geekhub.databinding.ActivityReadyBinding

class ReadyActivity : AppCompatActivity() {
    lateinit var binding: ActivityReadyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deliveryStartButton.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }


    }
}