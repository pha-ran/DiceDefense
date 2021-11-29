package com.dicedefense.Game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicedefense.Main.ScoreActivity
import com.dicedefense.databinding.ActivityGameBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer

class GameActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGameBinding
    private val random = Random()
    private var hp = 100
    private var score = 0
    private var gold = 50
    private val enemyList = ArrayList<Enemy>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameLoop()
        respawnEnemy()
    }

    // 뒤로가기 키 방지
    override fun onBackPressed() {
        //super.onBackPressed()
    }

    private fun gameLoop() {
        // 0.01초 단위
        timer(period = 10) {
            deleteEnemy()
            moveEnemy()

            if (hp <= 0) {
                cancel()

                // ScoreActivity 실행 후 GameActivity 종료
                val intent = Intent(this@GameActivity, ScoreActivity::class.java)
                intent.putExtra("score", score)
                startActivity(intent)
                finish()
            }

            runOnUiThread {
                binding.textView.text = "Gold : $gold" // gold 출력
            }
        }
    } // gameLoop()

    private fun respawnEnemy() {
        // 1초 단위
        timer(period = 1000, initialDelay = 3000) {
            enemyList.add(Enemy(50f, 0f, 100)) // 적 객체 생성

            if (hp <= 0) {
                cancel()
            }
        }
    } // respawnEnemy()

    fun getHp() : String {
        return "HP : $hp" // GameView에서 hp사용
    }

    fun getScore() : String {
        return "score : $score" // GameView에서 score사용
    }

    fun getEnemyList() : ArrayList<Enemy> {
        return enemyList // GameView에서 enemyList사용
    }

    private fun moveEnemy() {
        // 모든 적 객체를 이동
        for (e in enemyList) {
            e.y += 3 // y좌표 아래로 이동

            // hp 감소시 삭제 test
            if (random.nextInt(150)== 50) {
                e.hp -= 50
            }
        }
    } // moveEnemy()

    private fun deleteEnemy() {
        // enemy 객체의 현재 hp가 0이하가 되거나 bottom 좌표를 초과할 경우 삭제
        val iterator = enemyList.iterator()
        while (iterator.hasNext()) {
            val e = iterator.next()
            if (e.hp <= 0) { // hp가 0이 이하가 될 경우
                iterator.remove()
                gold += 10 // 적 처치 시 골드 획득
                score += 10 // 적 처치 시 점수 증가
            }
            if (e.y > binding.gameView.getBot()) { // bottom 좌표를 초과할 경우
                iterator.remove()
                hp -= 10 // 적 처치 실패 시 hp 감소
            }
        }
    } // deleteEnemy()
}