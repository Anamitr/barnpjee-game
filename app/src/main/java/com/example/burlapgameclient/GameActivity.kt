package com.example.burlapgameclient

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.marginBottom
import androidx.core.view.setPadding
import api.exception.MinefieldConst.MINEFIELD_HEIGHT
import api.exception.MinefieldConst.MINEFIELD_WIDTH
import api.model.Minefield
import api.service.MinesweeperService

import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity() {
    companion object {
        private val TAG = GameActivity::class.java.simpleName

        private val INTENT_MINEFIELD = "INTENT_MINEFIELD"

        fun newIntent(context: Context, minefield: Minefield) : Intent {
            val intent = Intent(context, GameActivity::class.java)
            intent.putExtra(INTENT_MINEFIELD, minefield)
            return intent
        }
    }

    val minesweeperService : MinesweeperService = BurlapMinesweeperService()

    lateinit var minefield : Minefield

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        minefield = intent.getSerializableExtra(INTENT_MINEFIELD) as Minefield

        drawMinefieldGridLayout()


    }

    private fun drawMinefieldGridLayout() {
//        minefieldGridLayout.rowCount = MINEFIELD_HEIGHT
//        minefieldGridLayout.columnCount = MINEFIELD_WIDTH

        for(i in 0 until MINEFIELD_HEIGHT)
            for(j in 0 until MINEFIELD_WIDTH) {
//                val textView = TextView(this)
//                textView.text = "$i - $j"
                val fieldButton = Button(this).apply {
                    val fieldType = minefield.fieldsMatrix[i][j]
                    if(fieldType.isRevealed) {
                        if(fieldType.isBomb) {
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

                    setOnClickListener(View.OnClickListener { v -> buttonClick(i, j) })

                }


                minefieldGridLayout.addView(fieldButton)
            }
    }

    private fun buttonClick(i: Int, j: Int) {
        GlobalScope.launch {
            val checkFieldResponse = minesweeperService.checkField(i, j)
            Log.v(TAG, "checkField($i, $j) = $checkFieldResponse")
        }

    }

    private fun getTextFromPositon(i: Int, j: Int): CharSequence? {
        val fieldType = minefield.fieldsMatrix[i][j]
        return ""
    }



}
