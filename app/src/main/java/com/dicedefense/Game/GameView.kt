package com.dicedefense.Game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class GameView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    protected var x : Int = 0
    protected var y : Int = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.x = w
        this.y = h
        println("$x, $y")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val p = Paint()

        p.color = Color.GRAY
        canvas.drawRect(x/2f - 5, 0f, x/2f + 5, y.toFloat(), p)

        p.color = Color.RED
        canvas.drawRect(x/2f - 60, y - 10f, x/2f + 60, y.toFloat(), p)

        p.color = Color.BLACK
        canvas.drawRect(x/2f - 50, y - 120f, x/2f + 50, y - 20f, p)
    }
}