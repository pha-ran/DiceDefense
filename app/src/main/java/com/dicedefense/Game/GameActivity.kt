package com.dicedefense.Game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicedefense.databinding.ActivityGameBinding
import kotlin.concurrent.timer

class GameActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGameBinding
    private var hp = 100
    private var score = 0
    private var gold = 50
    private val enemyList = ArrayList<Enemy>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        timer10()
        timer100()
        timer1000()
    }

    private fun timer10() {
        timer(period = 10) {
            for (i in 0..enemyList.size-1) {
                enemyList[i].y += 3
            }

            if (hp <= 0) {
                cancel()
            }
        }
    }

    private fun timer100() {
        timer(period = 100) {
            score += 1

            if (hp <= 0) {
                cancel()
            }
        }
    }

    private fun timer1000() {
        timer(period = 1000) {
            gold += 10
            enemyList.add(Enemy(50f, 0f))

            if (hp <= 0) {
                cancel()
            }

            runOnUiThread {
                binding.textView.text = "Gold : $gold"
            }
        }
    }

    fun getScore(): String {
        return "score : $score"
    }

    fun getEnemyList(): ArrayList<Enemy> {
        return enemyList
    }

    fun deleteEnemy(bot : Float) {
        val deleteList = ArrayList<Int>()

        // enemy 객체가 bottom 좌표를 넘어갈 경우 삭제 리스트에 인덱스 추가
        for (i in 0..enemyList.size-1) {
            if (enemyList[i].y > bot) {
                deleteList.add(i)
            }
        }

        // 삭제 리스트에 추가된 객체들을 제거
       for (i in 0..deleteList.size-1) {
            enemyList.removeAt(deleteList[i]-i)
        }
    }
}