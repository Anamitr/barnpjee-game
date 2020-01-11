package com.example.burlapgameclient

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import api.exception.MinefieldConst.MINEFIELD_HEIGHT
import api.exception.MinefieldConst.MINEFIELD_WIDTH
import api.model.CheckFieldResponse
import api.model.Minefield
import api.service.MinesweeperService
import com.example.ama_tracking_app.base.CustomApplication

import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class GameActivity : AppCompatActivity() {
    companion object {
        private val TAG = GameActivity::class.java.simpleName

        private val INTENT_MINEFIELD = "INTENT_MINEFIELD"
        private val INTENT_USERNAME = "INTENT_USERNAME"

        fun newIntent(
            context: Context,
            minefield: Minefield,
            username: String
        ): Intent {
            val intent = Intent(context, GameActivity::class.java)
            intent.putExtra(INTENT_MINEFIELD, minefield)
            intent.putExtra(INTENT_USERNAME, username)
            return intent
        }
    }

    val minesweeperService: MinesweeperService = BurlapMinesweeperService()

    lateinit var minefield: Minefield
    lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        minefield = intent.getSerializableExtra(INTENT_MINEFIELD) as Minefield
        username = intent.getStringExtra(INTENT_USERNAME)

        drawMinefieldGridLayout()

    }

    private fun drawMinefieldGridLayout() {
        GlobalScope.launch(Dispatchers.Main) {
            minefieldGridLayout.removeAllViews()
            for (i in 0 until MINEFIELD_HEIGHT)
                for (j in 0 until MINEFIELD_WIDTH) {
                    val fieldButton = Button(this@GameActivity).apply {
                        val fieldType = minefield.fieldsMatrix[i][j]
                        if (fieldType.isRevealed) {
                            if (fieldType.isBomb) {
                                text = "B"
                            } else if (fieldType.bombsAround != 0) {
                                text = fieldType.bombsAround.toString()
                            }
                            isClickable = false
                            setBackgroundColor(Color.LTGRAY)
                        } else {
                            setBackgroundColor(Color.DKGRAY)
                        }

                        val layoutParams = GridLayout.LayoutParams()
                        layoutParams.height = 200
                        layoutParams.width = 200
                        val margin = 5
                        layoutParams.setMargins(margin, margin, margin, margin)
                        this.layoutParams = layoutParams

                        setOnClickListener(View.OnClickListener { v ->
                            buttonClick(
                                v as Button,
                                i,
                                j
                            )
                        })
                    }
                    minefieldGridLayout.addView(fieldButton)
                }

            if (minefield.isGameOver) {
                if(minefield.isWasGameWon) {
                    setGameWon()
                } else {
                    val bombButton = minefieldGridLayout.getChildAt(minefield.detonatedBombPosition)
                    bombButton.setBackgroundResource(R.mipmap.bomb)
                    setGameOver()
                }
            }
        }

    }

    private fun buttonClick(button: Button, x: Int, y: Int) {
        GlobalScope.launch {
            val checkFieldResponse = minesweeperService.checkField(minefield.id, username, x, y)
            Log.v(TAG, "checkField($x, $y) = $checkFieldResponse")
            when (checkFieldResponse) {
                CheckFieldResponse.NOT_YOUR_TURN -> {
                    Log.v(TAG, "Not your turn!")
                    EventBus.getDefault().post(CustomApplication.ToastEvent("Not your turn!"))
//                    Toast.makeText(this@GameActivity, "Not your turn!", Toast.LENGTH_SHORT).show()
                }
                CheckFieldResponse.BOMB -> {
                    Log.v(TAG, "Detonated a bomb!")
                    EventBus.getDefault().post(
                        CustomApplication.ToastEvent(
                            "Detonated a bomb!",
                            Toast.LENGTH_LONG
                        )
                    )
                    handleBomDetonation(x, y)
//                    button.setBackgroundResource(R.mipmap.bomb)
//                    this
//                    Toast.makeText(this@GameActivity, "Detonated a bomb!", Toast.LENGTH_LONG).show()
                }
                CheckFieldResponse.OK -> {
                    minefield = minesweeperService.getMinefield(minefield.id)
                    drawMinefieldGridLayout()
                }
                CheckFieldResponse.GAME_IS_OVER -> {
                    minefield = minesweeperService.getMinefield(minefield.id)
                    drawMinefieldGridLayout()
                    setGameOver()
                }
                CheckFieldResponse.GAME_WON -> {
                    minefield = minesweeperService.getMinefield(minefield.id)
                    drawMinefieldGridLayout()
                    handleGameWon()
                }

            }
        }

    }

    private fun handleBomDetonation(x: Int, y: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            val bombButton = minefieldGridLayout.getChildAt(x * MINEFIELD_WIDTH + y)
            bombButton.setBackgroundResource(R.mipmap.bomb)
            setGameOver()
        }
    }

    private fun setGameOver() {
        GlobalScope.launch(Dispatchers.Main) {
            infoTextView.text = "Game over!"
            setAllFieldUnclickabe()
        }
    }

    private fun handleGameWon() {
        GlobalScope.launch(Dispatchers.Main) {
            EventBus.getDefault().post(CustomApplication.ToastEvent("Game Won!", Toast.LENGTH_LONG))
            setGameWon()
        }
    }

    private fun setGameWon() {
        GlobalScope.launch(Dispatchers.Main) {
            infoTextView.text = "Game won!"
            setAllFieldUnclickabe()
        }
    }

    private fun setAllFieldUnclickabe() {
        val count = minefieldGridLayout.childCount
        for (i in 0 until count) {
            minefieldGridLayout[i].isClickable = false
        }
    }

    private fun getTextFromPositon(i: Int, j: Int): CharSequence? {
        val fieldType = minefield.fieldsMatrix[i][j]
        return ""
    }


}
