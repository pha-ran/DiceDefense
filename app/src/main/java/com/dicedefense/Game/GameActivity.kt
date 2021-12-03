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
    private var selected = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adaptor = FieldAdaptor(binding.gameView.getDiceList())
        setRecyclerView()

        showSelected(false)
        showPrice()

        adaptor.setOnItemClickListener(object : FieldAdaptor.OnItemClickListener{
            override fun onItemClick(position: Int) {
                selectDice(position)
            }
        })
        
        binding.button.setOnClickListener {
            binding.gameView.buyDice() // 구매 버튼 클릭
            showPrice()
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

    private fun selectDice(position: Int) {
        println("$position 선택됨")

        when {
            selected == -1 -> {
                selected = position
                showSelected(true)
            }
            selected != position -> {
                binding.gameView.levelUp(selected, position)
                selected = -1
                showSelected(false)
            }
            else -> {
                selected = -1
                showSelected(false)
            }
        }
    }

    private fun showSelected(b : Boolean) {
        when(b) {
            true -> {
                binding.tvSelected.text = "현재 선택된 주사위\n${(selected/4) + 1} 번째 줄 ${selected % 4 + 1} 번째"
            }
            else -> {
                binding.tvSelected.text = "현재 선택된 주사위\n없음"
            }
        }
    }

    private fun showPrice() {
        binding.button.text = "주사위 구매\n(가격 : ${binding.gameView.getPrice()} Gold)"
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