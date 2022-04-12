package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val startBtn = findViewById<Button>(R.id.startBtn)
        //add a onClick button listener to the "Start" button
        startBtn.setOnClickListener {
            val intent = Intent(this, ChooseMode::class.java)
            startActivity(intent)
        }
    }

}