package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class ChooseMode : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_mode)
        val pvpBtn = findViewById<Button>(R.id.pvpBtn)
        val pvcBtn = findViewById<Button>(R.id.pvcBtn)
        //depending on chosen gamemode save different variables
        //if pvp save only the gamemode varibale
        pvpBtn.setOnClickListener {
            val pvp = "pvp"
            val intent = Intent(this, AddPlayer::class.java)
            intent.putExtra("gameMode", pvp)
            startActivity(intent)
        }
        //if pvc also save the playerTwo name as "computer" by default
        pvcBtn.setOnClickListener {
            val pvc = "pvc"
            val intent = Intent(this, AddPlayer::class.java)
            val playerTwoName = "Computer"
            intent.putExtra("gameMode", pvc)
            intent.putExtra("PlayerTwoName", playerTwoName)
            startActivity(intent)
        }
    }
}