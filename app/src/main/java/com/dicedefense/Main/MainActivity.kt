package com.dicedefense.Main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicedefense.Game.GameActivity
import com.dicedefense.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGameStart.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }

        binding.btnRank.setOnClickListener {
            startActivity(Intent(this, ScoreActivity::class.java))
        }

        binding.btnTip.setOnClickListener {
            startActivity(Intent(this, TipActivity::class.java))
        }

        binding.btnExit.setOnClickListener {
            finish()
        }
    }

    // 뒤로가기 키 방지
    override fun onBackPressed() {
        //super.onBackPressed()
    }
}