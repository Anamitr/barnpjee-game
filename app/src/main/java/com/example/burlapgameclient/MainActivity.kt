package com.example.burlapgameclient

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import api.service.MinesweeperService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


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
        usernameEditText.setText("Hektor") // Hektor Achilles Parystokles Arystoteles
        gameIdEditText.setText("1")
    }

    fun startGameActivity(v: View) {
        GlobalScope.launch {
            val minefieldId = gameIdEditText.text.toString()
            val username = usernameEditText.text.toString()

            minesweeperService.registerForMinefield(minefieldId, username)
            val minefield = minesweeperService.getMinefield(minefieldId)

            val intent = GameActivity.newIntent(context = this@MainActivity, minefield = minefield, username = username)
            this@MainActivity.startActivity(intent)

        }
    }

    fun testBurlap() {
        GlobalScope.launch {
            BurlapTest.runSomeTests()
        }
    }

}
