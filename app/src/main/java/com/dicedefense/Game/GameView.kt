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
    val paint = Paint()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.x = w
        this.y = h
        mid = x/2f
        bot = y.toFloat()
        println("$x, $y")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        initialDraw(canvas)
        scoreDraw(canvas)
        enemyDraw(canvas)

        invalidate()
    }

    fun initialDraw(canvas: Canvas) {
        paint.color = Color.GRAY
        canvas.drawRect(mid - 5, top, mid + 5, bot, paint)

        paint.color = Color.BLUE
        canvas.drawRect(mid - 60, top, mid + 60, top + 10, paint)

        paint.color = Color.RED
        canvas.drawRect(mid - 60, bot - 10, mid + 60, bot, paint)
    }

    fun scoreDraw(canvas: Canvas) {
        paint.color = Color.BLACK
        paint.textSize = 50f
        canvas.drawText(gameActivity.getScore(), x/16f, y/16f, paint)
    }

    fun enemyDraw(canvas: Canvas) {
        paint.color = Color.BLACK

        val enemyList = gameActivity.getEnemyList()
        val index = enemyList.size - 1

        // enemyList의 enemy 객체들을 모두 Draw
        for (i in 0..index) {
            canvas.drawRect(mid - 50, enemyList[i].y - 50f, mid + 50, enemyList[i].y + 50f, paint)
        }

        // enemy 객체가 bottom 좌표를 넘어갈 경우 삭제
        gameActivity.deleteEnemy(bot)
    }
}