package com.example.tictactoe

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
//idea of how to draw the game board for tictactoe and how to utilize the buttons via code taken from the link below
//https://www.geeksforgeeks.org/how-to-build-a-tic-tac-toe-game-with-both-offline-and-online-mode-in-android/9
class GameAction : AppCompatActivity() {

    //check if game is still in progress
    private var gameInProgress = true
    //check how many spaces have been filled
    private var countSpaces = 0

    //array that contains all possible winner combinations for tictactoe (rows, columns, diagonals)
    private var allWinnerCombinations = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )

    //array that helps identify which space is filled with what, -1: empty, 0: player1, 1: player2
    private var boardPositions = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1)

    //strings that help identify players by the names they input
    private lateinit var winnerText : TextView
    private lateinit var playerOne : String
    private lateinit var playerTwo : String
    //strings that tells us the chosen game mode
    private lateinit var gameMode : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_action)
        //get player names from the AddPlayer view
        playerOne = intent.getStringExtra("PlayerOne").toString()
        playerTwo = intent.getStringExtra("PlayerTwo").toString()
        //get game mode from the ChooseMode view
        gameMode = intent.getStringExtra("gameMode").toString()
        //setting variables from game restart and main menu buttons
        val restartGame = findViewById<Button>(R.id.restart)
        val mainMenu = findViewById<Button>(R.id.mainMenu)
        winnerText = findViewById(R.id.winnerText)
        //setting custom greeting
        val customMessage = findViewById<TextView>(R.id.customMessage)
        customMessage.text = getString(R.string.customMessage, playerOne, playerTwo)
        //adding on click listeners to buttons for game restart and exit to main menu
        restartGame.setOnClickListener {
            restartGame()
        }
        mainMenu.setOnClickListener {
            mainMenu()
        }
    }

    //function that triggers when a player clicks on the tictactoe board
    fun clickGameBoard(view : View) {
        val button = view as Button
        var cellID = 0
        when (button.id) {
            R.id.button -> cellID = 0
            R.id.button2 -> cellID = 1
            R.id.button3 -> cellID = 2
            R.id.button4 -> cellID = 3
            R.id.button5 -> cellID = 4
            R.id.button6 -> cellID = 5
            R.id.button7 -> cellID = 6
            R.id.button8 -> cellID = 7
            R.id.button9 -> cellID = 8
        }
        updateGameBoard(button, cellID)
    }

    //function that helps computer make its move
    private fun computerMove(){
        val currentCell = (0..8).random()
        //check if a random cell is empty or not, if it's not, restart function
        if(boardPositions[currentCell] != -1){
            computerMove()
        }
        //assign button variable corresponding with clicked button ID
        val buttonSelected : Button = when (currentCell) {
            0 -> findViewById(R.id.button)
            1 -> findViewById(R.id.button2)
            2 -> findViewById(R.id.button3)
            3 -> findViewById(R.id.button4)
            4 -> findViewById(R.id.button5)
            5 -> findViewById(R.id.button6)
            6 -> findViewById(R.id.button7)
            7 -> findViewById(R.id.button8)
            8 -> findViewById(R.id.button9)
            else -> {
                findViewById(R.id.button)
            }
        }
        //make a move
        if(currentPlayer == 0 && gameInProgress && boardPositions[currentCell] == -1){
            buttonSelected.text = "O"
            buttonSelected.setTextColor(Color.parseColor("#D22BB804"))
            //disable button
            buttonSelected.isEnabled = false
            //add +1 space filled
            countSpaces++
            boardPositions[currentCell] = 0
            //check if there's a winner
            val isWinner = checkWinner()
            if(isWinner == 0){
                winnerText.text = getString(R.string.winPartMessage, playerTwo)
            }
            currentPlayer = 1
        }
    }

    private var currentPlayer = 1

    //update gameboard visually after a move is made
    private fun updateGameBoard(buttonSelected : Button, currentCell : Int) {
        if(currentPlayer == 1 && gameInProgress && boardPositions[currentCell] == -1) {
            buttonSelected.text = "X"
            buttonSelected.setTextColor(Color.parseColor("#EC0BBB"))
            buttonSelected.isEnabled = false
            countSpaces++
            boardPositions[currentCell] = 1
            val isWinner = checkWinner()
            //check if there's a winner
            //check if it's pvc, if yes, switch player and use computer move function
            //else just switch the player
            if(isWinner == 1){
                winnerText.text = getString(R.string.winPartMessage, playerOne)
            }else if(gameMode == "pvc" && countSpaces < 8){
                currentPlayer = 0
                computerMove()
            }else{
                currentPlayer = 0
            }
        }
        else {
            buttonSelected.text = "O"
            buttonSelected.setTextColor(Color.parseColor("#D22BB804"))
            buttonSelected.isEnabled = false
            countSpaces++
            boardPositions[currentCell] = 0
            val isWinner = checkWinner()
            if(isWinner == 0){
                winnerText.text = getString(R.string.winPartMessage, playerTwo)
            }
            currentPlayer = 1
        }
        if (gameInProgress && countSpaces == 9) {
            winnerText.visibility = View.VISIBLE
            winnerText.text = getString(R.string.drawMessage)
            gameInProgress = false
        }
    }

    //function to check winner using pre-defined array with all winning combinations
    private fun checkWinner() : Int{
        for(allWinnerCombinations in allWinnerCombinations) {
            if (boardPositions[allWinnerCombinations[0]] == boardPositions[allWinnerCombinations[1]] &&
                boardPositions[allWinnerCombinations[1]] == boardPositions[allWinnerCombinations[2]])
                if (boardPositions[allWinnerCombinations[0]] == 1) {
                    disableEmptyButtons()
                    winnerText.visibility = View.VISIBLE
                    gameInProgress = false
                    return 1
                } else if (boardPositions[allWinnerCombinations[0]] == 0) {
                    disableEmptyButtons()
                    winnerText.visibility = View.VISIBLE
                    gameInProgress = false
                    return 0
                }
        }
        return -1
    }

    //function to disable empty buttons in case there's a winner so no errors occur
    private fun disableEmptyButtons(){
        for (i in 0..8) {
            val thisButton: Button = when (i) {
                0 -> findViewById(R.id.button)
                1 -> findViewById(R.id.button2)
                2 -> findViewById(R.id.button3)
                3 -> findViewById(R.id.button4)
                4 -> findViewById(R.id.button5)
                5 -> findViewById(R.id.button6)
                6 -> findViewById(R.id.button7)
                7 -> findViewById(R.id.button8)
                8 -> findViewById(R.id.button9)
                else -> {
                    findViewById(R.id.button)
                }
            }
            thisButton.isEnabled = false
        }
    }

    //function to restart the game (resets all variables to the way they started out as)
    private fun restartGame() {
        currentPlayer = 1
        gameInProgress = true
        countSpaces = 0

        for (i in boardPositions.indices) {
            boardPositions[i] = -1
        }
        winnerText.visibility = View.INVISIBLE
        for (i in 0..8) {
            val thisButton: Button = when (i) {
                0 -> findViewById(R.id.button)
                1 -> findViewById(R.id.button2)
                2 -> findViewById(R.id.button3)
                3 -> findViewById(R.id.button4)
                4 -> findViewById(R.id.button5)
                5 -> findViewById(R.id.button6)
                6 -> findViewById(R.id.button7)
                7 -> findViewById(R.id.button8)
                8 -> findViewById(R.id.button9)
                else -> {
                    findViewById(R.id.button)
                }
            }
            thisButton.isEnabled = true
            thisButton.text = ""
        }
    }


    //function to go to main menu
    private fun mainMenu(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}