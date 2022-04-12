package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class AddPlayer : AppCompatActivity() {

    lateinit var gameMode : String
    var playerTwoName : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_player)
        val confirmBtn = findViewById<Button>(R.id.pvpBtn)
        playerTwoName = intent.getStringExtra("PlayerTwoName").toString()
        gameMode = intent.getStringExtra("gameMode").toString()
        if(gameMode == "pvc"){
            var enterTwo: EditText = findViewById(R.id.enter2)
            playerTwoName = "Computer"
            enterTwo.setText(playerTwoName)
            enterTwo.setEnabled(false)
        }

        confirmBtn.setOnClickListener {
            submitAndStart()
        }
    }

    private fun submitAndStart(){
        var enterOne: EditText = findViewById(R.id.enter1)
        var enterTwo: EditText = findViewById(R.id.enter2)
        val playerOneName = enterOne.text.toString()
        playerTwoName = enterTwo.text.toString()
        val intent = Intent(this, GameAction::class.java)
        intent.putExtra("PlayerOne", playerOneName)
        intent.putExtra("PlayerTwo", playerTwoName)
        intent.putExtra("gameMode", gameMode)
        startActivity(intent)
    }
}