package com.dicedefense.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicedefense.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {
    private lateinit var binding : ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val score = intent.extras?.get("score")
        binding.textView.text = score.toString()
    }

    // 뒤로가기 키 방지
    override fun onBackPressed() {
        //super.onBackPressed()
    }
}