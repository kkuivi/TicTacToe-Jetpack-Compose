package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.ui.theme.TicTacToeTheme
import java.util.Collections

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val boardState = remember {
                mutableStateOf(listOf("", "", "", "", "", "", "", "", ""))
            }
            val isCurrentPlayerX = remember {
                mutableStateOf(true)
            }
            val gameEnded = remember {
                mutableStateOf(false)
            }
            val winner = remember {
                mutableStateOf("")
            }

            TicTacToeTheme {
                Board(
                    boardState.value,
                    onBoardStateChanged = { index, value ->
                        val boardStateCopy = boardState.value.toMutableList()

                        if (boardStateCopy[index] != "") {
                            return@Board
                        }

                        boardStateCopy[index] = value
                        boardState.value = boardStateCopy.toList()

                        val winningPlayer = isWinner(boardStateCopy)
                        if (winningPlayer != "") {
                            winner.value = winningPlayer
                            gameEnded.value = true
                            return@Board
                        }

                        if (isDraw(boardStateCopy)) {
                            winner.value = ""
                            gameEnded.value = true
                        }

                        isCurrentPlayerX.value = !isCurrentPlayerX.value

                    },
                    isCurrentPlayerX.value,
                    gameEnded.value,
                    winner.value,
                    onReset = {
                        boardState.value =
                            listOf("", "", "", "", "", "", "", "", "")
                        isCurrentPlayerX.value = true
                        gameEnded.value = false
                        winner.value = ""
                    })
            }
        }
    }
}

fun isDraw(boardState: List<String>): Boolean {
    return "" !in boardState
}

fun isWinner(boardState: List<String>): String {
    val winningState: List<List<Int>> = listOf(
        listOf(0, 1, 2),
        listOf(3, 4, 5),
        listOf(6, 7, 8),
        listOf(0, 3, 6),
        listOf(1, 4, 7),
        listOf(2, 5, 8),
        listOf(0, 4, 8),
        listOf(2, 4, 6)
    )

    for (boxes in winningState) {
        if (boardState[boxes[0]] == boardState[boxes[1]] &&
            boardState[boxes[1]] == boardState[boxes[2]] &&
            boardState[boxes[0]] != ""
        ) {
            return boardState[boxes[0]]
        }
    }

    return ""
}

@Composable
fun TicTacToeBox(
    boardState: List<String>, index: Int, isCurrentPlayerX:
    Boolean, onBoardStateChanged:
        (Int, String)
    -> Unit
) {
    Button(modifier = Modifier, shape = RectangleShape, onClick = {
        var res = if (isCurrentPlayerX) "X" else "O"
        onBoardStateChanged(index, res)
    }) {
        Text(text = boardState[index])
    }
}

@Composable
fun Board(
    boardState: List<String>,
    onBoardStateChanged: (Int, String) -> Unit,
    isCurrentPlayerX: Boolean,
    gameEnded: Boolean,
    winner: String,
    onReset: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        if (gameEnded) {
            val text = if (winner != "") "Winner: $winner" else "Draw"
            Text(
                text = text, modifier = Modifier.padding(20.dp),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color.Red
            )
        }

        Row(
            horizontalArrangement = Arrangement
                .spacedBy(
                    10
                        .dp
                )
        ) {
            TicTacToeBox(
                boardState,
                index = 0,
                isCurrentPlayerX,
                onBoardStateChanged
            )
            TicTacToeBox(
                boardState,
                index = 1,
                isCurrentPlayerX,
                onBoardStateChanged
            )
            TicTacToeBox(
                boardState,
                index = 2,
                isCurrentPlayerX,
                onBoardStateChanged
            )
        }

        Row(
            horizontalArrangement = Arrangement
                .spacedBy(
                    10
                        .dp
                )
        ) {
            TicTacToeBox(
                boardState,
                index = 3,
                isCurrentPlayerX,
                onBoardStateChanged
            )
            TicTacToeBox(
                boardState,
                index = 4,
                isCurrentPlayerX,
                onBoardStateChanged
            )
            TicTacToeBox(
                boardState,
                index = 5,
                isCurrentPlayerX,
                onBoardStateChanged
            )
        }

        Row(
            horizontalArrangement = Arrangement
                .spacedBy(
                    10
                        .dp
                )
        ) {
            TicTacToeBox(
                boardState,
                index = 6,
                isCurrentPlayerX,
                onBoardStateChanged
            )
            TicTacToeBox(
                boardState,
                index = 7,
                isCurrentPlayerX,
                onBoardStateChanged
            )
            TicTacToeBox(
                boardState,
                index = 8,
                isCurrentPlayerX,
                onBoardStateChanged
            )
        }

        val player = if (isCurrentPlayerX) "X" else "O"
        Text(
            text = "Current Player: $player",
            modifier = Modifier.padding(20.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        )

        Button(
            modifier = Modifier.padding(20.dp),
            shape = RectangleShape,
            onClick = {
                onReset()
            }) {
            Text(text = "Reset", fontSize = 20.sp)
        }
    }
}