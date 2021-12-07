package com.dicedefense.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicedefense.R
import com.dicedefense.databinding.ActivityTipBinding

class TipActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTipBinding
    private var page : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivTip.setOnClickListener {
            when (page) {
                0 -> {
                    binding.ivTip.setImageResource(R.drawable.tip_2)
                    page = 1
                }
                1 -> {
                    binding.ivTip.setImageResource(R.drawable.tip_3)
                    page = 2
                }
                2 -> {
                    binding.ivTip.setImageResource(R.drawable.tip_1)
                    page = 0
                }
            }
        }
    }
}