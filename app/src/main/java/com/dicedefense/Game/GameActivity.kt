package com.dicedefense.Game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.dicedefense.Main.ScoreActivity
import com.dicedefense.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGameBinding
    private lateinit var adaptor : FieldAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adaptor = FieldAdaptor(binding.gameView.getDiceList())
        setRecyclerView()

        binding.button.setOnClickListener {
            binding.gameView.buyDice() // 구매 버튼 클릭
        }
    }

    // 뒤로가기 키 방지
    override fun onBackPressed() {
        //super.onBackPressed()
    }

    private fun setRecyclerView() {
        val layoutManager = GridLayoutManager(this, 4)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adaptor
    }

    fun drawDice() {
        adaptor.notifyDataSetChanged()
    }

    fun gameOver() {
        // ScoreActivity 실행 후 GameActivity 종료
        val intent = Intent(this@GameActivity, ScoreActivity::class.java)
        intent.putExtra("score", binding.gameView.getScore())
        startActivity(intent)
        finish()
    }
}