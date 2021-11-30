package com.dicedefense.Game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.Random
import kotlin.collections.ArrayList

class GameView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    // Val
    private val gameActivity = (context as GameActivity)
    private val paintBlack = Paint()
    private val paintWhite = Paint()
    private val paintGray = Paint()
    private val paintBlue = Paint()
    private val paintRed = Paint()
    private val random = Random()
    private val enemyList = ArrayList<Enemy>()

    // Var
    private var x = 0
    private var y = 0
    private var top = 0f
    private var mid = 0f
    private var bot = 0f
    private var playerHp = 100
    private var score = 0
    private var gold = 50
    private var time = 0

    // Initialize
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.x = w
        this.y = h
        mid = x/2f
        bot = y.toFloat()
        paintBlack.color = Color.BLACK
        paintBlack.textSize = 50f
        paintWhite.color = Color.WHITE
        paintWhite.textSize = 50f
        paintWhite.textAlign = Paint.Align.CENTER
        paintGray.color = Color.GRAY
        paintBlue.color = Color.BLUE
        paintRed.color = Color.RED
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (playerHp <= 0) { // hp 가 0 이하가 될 경우 종료
            gameActivity.gameOver()
        }
        else {
            initialDraw(canvas)

            time += 1
            if (time >= 50) { // 초당 1회 동작
                enemyList.add(Enemy(50f, 0f, 100)) // 적 객체 생성
                time = 0
            }

            enemyDraw(canvas)
            
            invalidate()
        }
    }

    private fun initialDraw(canvas: Canvas) {
        // 기본 도형 그리기
        canvas.drawRect(mid - 5, top, mid + 5, bot, paintGray)
        canvas.drawRect(mid - 60, top, mid + 60, top + 10, paintBlue)
        canvas.drawRect(mid - 60, bot - 10, mid + 60, bot, paintRed)

        // 현재 상태 그리기
        canvas.drawText("score : $score", x/16f, y/16f, paintBlack)
        canvas.drawText("HP : $playerHp", x/16f, 15*y/16f, paintBlack)
        canvas.drawText("gold : $gold", 10*x/16f, 15*y/16f, paintBlack)
    }

    @Synchronized private fun enemyDraw(canvas: Canvas) {
        // enemyList 의 enemy 객체들을 모두 Draw
        for (enemy in enemyList) {
            val size = enemy.size
            canvas.drawRect(mid - size, enemy.y - size, mid + size, enemy.y + size, paintBlack)
            canvas.drawText(enemy.hp.toString(), mid, enemy.y + 20, paintWhite) // enemy 객체의 hp 출력
        }

        val iterator = enemyList.iterator()
        while (iterator.hasNext()) {
            val e = iterator.next()
            if (e.hp <= 0) { // hp가 0이 이하가 될 경우
                iterator.remove()
                gold += 10 // 적 처치 시 골드 획득
                score += 10 // 적 처치 시 점수 증가
            }
            else if (e.y > bot) { // bottom 좌표를 초과할 경우
                iterator.remove()
                playerHp -= 10 // 적 처치 실패 시 hp 감소
            }
            else { // 삭제 조건이 아닐 경우 객체 이동
                e.y += 5 // y좌표 아래로 이동

                // hp 감소시 삭제 test
                if (random.nextInt(150)== 50) {
                    e.hp -= 100
                }
            }
        }
    }

    fun getScore() : Int {
        return score // GameActivity 에서 score 사용
    }
}