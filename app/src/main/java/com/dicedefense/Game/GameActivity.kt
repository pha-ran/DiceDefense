package com.dicedefense.Game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicedefense.Main.ScoreActivity
import com.dicedefense.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // 뒤로가기 키 방지
    override fun onBackPressed() {
        //super.onBackPressed()
    }

    fun gameOver() {
        // ScoreActivity 실행 후 GameActivity 종료
        val intent = Intent(this@GameActivity, ScoreActivity::class.java)
        intent.putExtra("score", binding.gameView.getScore())
        startActivity(intent)
        finish()
    }
}