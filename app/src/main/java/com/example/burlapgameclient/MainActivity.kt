package com.example.burlapgameclient

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import api.service.MinesweeperService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.min


class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    val minesweeperService : MinesweeperService = BurlapMinesweeperService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTestData()

        val testButton = findViewById<Button>(R.id.test_button) as Button
        testButton.setOnClickListener(View.OnClickListener { testBurlap() })
    }

    private fun setTestData() {
        usernameEditText.setText("Achilles") // Hektor Achilles Parystokles Arystoteles
        gameIdEditText.setText("60")
    }

    fun startGameActivity(v: View) {
        GlobalScope.launch {
            val minefield = minesweeperService.getMinefield(gameIdEditText.toString())

            val intent = GameActivity.newIntent(context = this@MainActivity, minefield = minefield)
            this@MainActivity.startActivity(intent)

        }

//        val intent = ChatActivity.newIntent(
//            this,
//            usernameEditText.text.toString(),
//            chatIdEditText.text.toString()
//        )
//        startActivity(intent)
    }

    fun testBurlap() {
        GlobalScope.launch {
            BurlapTest.runSomeTests()
        }
    }

}