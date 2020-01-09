package com.example.burlapgameclient

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import api.exception.MinefieldConst.MINEFIELD_HEIGHT
import api.exception.MinefieldConst.MINEFIELD_WIDTH
import api.model.Minefield

import kotlinx.android.synthetic.main.activity_game.*

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

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
                    text = "$i - $j"
                    val layoutParams = GridLayout.LayoutParams()
                    layoutParams.height = 200
                    layoutParams.width = 200
                    this.layoutParams = layoutParams
                }


                minefieldGridLayout.addView(fieldButton)
            }
    }

}
