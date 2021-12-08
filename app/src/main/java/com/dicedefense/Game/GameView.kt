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
    private val diceList = ArrayList<Dice>()
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
    private var price = 10
    private var time = 0
    private var round = 0
    private var remain = 250 // 현재 라운드의 남은 시간, 250 = 초기 대기 시간 5초

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
        // Dice List 초기화 (3 * 4 = 12개)
        for (i in 0..11)  {diceList.add(Dice(0, 0, mid, bot, true))}
    }

    // Game Loop
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (playerHp <= 0) { // hp 가 0 이하가 될 경우 종료
            gameActivity.gameOver()
        }
        else {
            initialDraw(canvas)
            statusChanged(canvas)
            invalidate()
        }
    }

    // Initial Draw
    private fun initialDraw(canvas: Canvas) {
        // 기본 도형 그리기
        canvas.drawRect(mid - 5, top, mid + 5, bot, paintGray)
        canvas.drawRect(mid - 60, top, mid + 60, top + 10, paintBlue)
        canvas.drawRect(mid - 60, bot - 10, mid + 60, bot, paintRed)

        // 현재 상태 그리기
        canvas.drawText("score : $score", x/16f, y/16f, paintBlack)
        canvas.drawText("round : $round", 11*x/16f, y/16f, paintBlack)
        canvas.drawText("HP : $playerHp", x/16f, 15*y/16f, paintBlack)
        canvas.drawText("gold : $gold", 11*x/16f, 15*y/16f, paintBlack)
    }

    // Dice, Enemy 객체 관리
    private fun statusChanged(canvas: Canvas) {
        // 주사위 공격 관리 (초당 1회 공격)
        if (time > 48) {
            time = 1
        }
        else {
            // time 이 4, 8, 12... 가 될 때마다 [0], [1], [2]... 공격
            if (time / 4 > 0 && time % 4 == 0) {
                val dice = diceList[(time / 4) - 1]

                if (dice.level > 0 && dice.isWait) { // 공격 대기 상태일 경우 공격 시작
                    val r = random.nextInt(50) - 25
                    dice.attackX = mid + r  // x 좌표 랜덤 설정
                    dice.attackY = bot
                    dice.isWait = false
                }
                else if (dice.attackY < 0) { // top 좌표를 넘어갈 경우
                        dice.isWait = true
                }
            }
        }

        // 라운드 관리 (대기시간 3초, 10초당 1라운드)
        if (remain <= 1) {
            remain = 650
            round += 1
        }
        else {
            if (remain <= 500
                && remain % 50 == 0
                && round > 0) { // 초당 약 1회 동작
                enemyList.add(Enemy(round * 3 + 30f, 0f, round * 10)) // 적 객체 생성
            }
        }

        // diceList 의 dice 객체들을 모두 Draw
        for (dice in diceList) {
            if (dice.level > 0 && !dice.isWait) { // 공격 대기 상태가 아닌 경우에만 그리기
                val s = dice.level * 10 // 공격 크기 설정
                when (dice.type) { // 타입 별 공격 색상 설정
                    0 -> { // normal
                        canvas.drawRect(dice.attackX - s, dice.attackY - s, dice.attackX + s, dice.attackY + s, paintBlack)
                    }
                    1 -> { // red
                        canvas.drawRect(dice.attackX - s, dice.attackY - s, dice.attackX + s, dice.attackY + s, paintRed)
                    }
                    2 -> { // blue
                        canvas.drawRect(dice.attackX - s, dice.attackY - s, dice.attackX + s, dice.attackY + s, paintBlue)
                    }
                }

                dice.attackY -= 10 // 공격 이동
            }
        }

        // enemyList 의 enemy 객체들을 모두 Draw
        for (enemy in enemyList) {
            val size = enemy.size
            canvas.drawRect(mid - size, enemy.y - size, mid + size, enemy.y + size, paintBlack)
            canvas.drawText(enemy.hp.toString(), mid, enemy.y + 20, paintWhite) // enemy 객체의 hp 출력
        }

        // enemyList 의 enemy 객체들의 상태 변경
        val iterator = enemyList.iterator()
        while (iterator.hasNext()) {
            val e = iterator.next()
            when {
                e.hp <= 0 -> { // hp가 0이 이하가 될 경우
                    iterator.remove()
                    gold += 10 // 적 처치 시 골드 획득
                    score += round * 10 // 적 처치 시 점수 증가
                }
                e.y > bot -> { // bottom 좌표를 넘어갈 경우
                    iterator.remove()
                    playerHp -= e.hp // 적 처치 실패 시 적의 남은 체력만큼 hp 감소
                }
                else -> { // 삭제 조건이 아닐 경우 객체 이동
                    e.y += 5 // y좌표 아래로 이동

                    for (dice in diceList) {
                        if (e.y - 10 > dice.attackY) { // 충돌 처리
                            dice.attackY = bot
                            dice.isWait = true
                            e.hp -= dice.level * 10 + dice.type * 5
                        }
                    }
                }
            }
        }

        time += 1
        remain -= 1
    } // statusChanged(canvas: Canvas)

    fun buyDice() {
        if (gold >= price && !isFull()) {
            gold -= price
            price += 10
            addRandom()
        }
    }

    private fun addRandom() {
        if (!isFull()) {
            while (true) {
                val r = random.nextInt(12)
                if (diceList[r].level == 0) {
                    diceList[r].level = 1
                    diceList[r].type = random.nextInt(3)
                    gameActivity.drawDice()
                    break
                }
            }
        }
    }

    private fun isFull() : Boolean { // 빈 공간이 있는지 확인
        for (dice in diceList) {
            if (dice.level == 0) {
                return false
            }
        }
        return true
    }

    fun levelUp(first : Int, second : Int) {
        if (diceList[first].level != 0
            && diceList[second].level != 0
            && diceList[first].level <= 6
            && diceList[second].level <= 6
            && diceList[first].level == diceList[second].level
            && diceList[first].type == diceList[second].type
        ) {
            diceList[second].level += 1
            diceList[second].type = random.nextInt(3)
            diceList[first].level = 0
            gameActivity.drawDice()
        }
    }

    fun getScore() : Int {
        return score // GameActivity 에서 score 사용
    }

    fun getPrice() : Int {
        return price
    }

    fun getDiceList() : ArrayList<Dice> {
        return diceList
    }
}