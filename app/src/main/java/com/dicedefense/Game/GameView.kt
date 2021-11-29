package com.dicedefense.Game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class GameView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var gameActivity = (context as GameActivity)
    private var x = 0
    private var y = 0
    private var top = 0f
    private var mid = 0f
    private var bot = 0f
    private val paintBlack = Paint()
    private val paintWhite = Paint()
    private val paintGray = Paint()
    private val paintBlue = Paint()
    private val paintRed = Paint()

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

        initialDraw(canvas)
        enemyDraw(canvas)

        invalidate()
    }

    private fun initialDraw(canvas: Canvas) {
        canvas.drawRect(mid - 5, top, mid + 5, bot, paintGray)
        canvas.drawRect(mid - 60, top, mid + 60, top + 10, paintBlue)
        canvas.drawRect(mid - 60, bot - 10, mid + 60, bot, paintRed)

        canvas.drawText(gameActivity.getScore(), x/16f, y/16f, paintBlack) // score 출력
        canvas.drawText(gameActivity.getHp(), x/16f, 15*y/16f, paintBlack) // player 의 hp 출력
    }

    private fun enemyDraw(canvas: Canvas) {
        val enemyList = gameActivity.getEnemyList()

        // enemyList 의 enemy 객체들을 모두 Draw
        for (enemy in enemyList) {
            val size = enemy.size
            canvas.drawRect(mid - size, enemy.y - size, mid + size, enemy.y + size, paintBlack)
            canvas.drawText(enemy.hp.toString(), mid, enemy.y + 20, paintWhite) // enemy 객체의 hp 출력
        }
    }

    fun getBot() : Float {
        return bot // GameActivity 에서 bot 사용
    }
}