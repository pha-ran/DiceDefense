package com.dicedefense.Main

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dicedefense.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {
    private lateinit var binding : ActivityScoreBinding
    private lateinit var sp : SharedPreferences
    private var first = 0
    private var second = 0
    private var third = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sp = applicationContext.getSharedPreferences("score", MODE_PRIVATE)
        getRank()

        val score = intent.extras?.get("score")
        if (score != null) {
            val s = score.toString().toInt()
            showToast("획득한 점수 : $s")
            setRank(s)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    // 뒤로가기 키 방지
    override fun onBackPressed() {
        //super.onBackPressed()
    }

    private fun getRank() {
        // 점수 불러오기
        val f = sp.getInt("first", -1)
        val s = sp.getInt("second", -1)
        val t = sp.getInt("third", -1)

        if (f != -1) {
            first = f
        }
        if (s != -1) {
            second = s
        }
        if (t != -1) {
            third = t
        }
        binding.tvRank1.text = first.toString()
        binding.tvRank2.text = second.toString()
        binding.tvRank3.text = third.toString()
    }

    private fun setRank(s : Int) {
        when {
            s > first -> { // 1위 달성시 전 1위와 2위가 한 등수 내려감
                sp.edit().putInt("first", s).apply()
                sp.edit().putInt("second", first).apply()
                sp.edit().putInt("third", second).apply()
                showToast("1위 달성! 점수가 갱신되었습니다.")
            }
            s > second -> { // 2위 달성시 전 2위가 한 등수 내려감
                sp.edit().putInt("second", s).apply()
                sp.edit().putInt("third", second).apply()
                showToast("2위 달성! 점수가 갱신되었습니다.")
            }
            s > third -> {
                sp.edit().putInt("third", s).apply()
                showToast("3위 달성! 점수가 갱신되었습니다.")
            }
        }
        getRank()
    }

    private fun showToast(s : String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }
}