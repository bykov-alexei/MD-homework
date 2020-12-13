package com.example.memorina

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

/**
 * TODO: document your custom view class.
 */
class Card(val i: Int, val j: Int, val value: Int) {
    var flipped = false

    private val padding = 8

    fun draw(canvas: Canvas, width: Int, height: Int) {
        val rectStyle = Paint().apply {
            style = Paint.Style.STROKE
            color = Color.BLACK
            strokeWidth = 3F
        }
        val r = Rect(width * i + padding,
            height * j + padding,
            width * (i + 1) - padding,
            height * (j + 1) - padding)

        if (flipped) {
            val fillStyle = Paint().apply {
                style = Paint.Style.FILL
                color = Color.parseColor("#D64933")
            }
            val r = Rect(width * i + padding,
                    height * j + padding,
                    width * (i + 1) - padding,
                    height * (j + 1) - padding)
            canvas.drawRect(r, fillStyle)

            val textStyle = Paint().apply {
                style = Paint.Style.FILL_AND_STROKE
                color = Color.BLACK
                textSize = 60F
            }
            canvas.drawText(
                value.toString(),
                (width * i + width / 2).toFloat(),
                (height * j + height / 2).toFloat(),
                textStyle
            )
        } else {
            val fillStyle = Paint().apply {
                style = Paint.Style.FILL
                color = Color.parseColor("#58A4B0")
            }
            val r = Rect(width * i + padding,
                height * j + padding,
                width * (i + 1) - padding,
                height * (j + 1) - padding)
            canvas.drawRect(r, fillStyle)
        }
        canvas.drawRect(r, rectStyle)
    }

    fun flip() {
        flipped = !flipped
    }

}

class TilesView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var gameRunning = true;
    private val size = 4
    private val tiles: Array<Card?> = Array(size * size){null}
    private val opened: MutableList<Card> = mutableListOf()

    private var tileWidth: Int? = null
    private var tileHeight: Int? = null

    init {
        generateField()
    }

    private fun generateField() {
        val values = List(size * size ) { i -> i / 2 + 1}.shuffled()
        for (i in 0 until size) {
            for (j in 0 until size) {
                tiles[i * size + j] = Card(i, j, values[i*size+j])
            }
        }
    }

    private fun drawBackground(canvas: Canvas) {
        val p = Paint().apply {
            color = Color.parseColor("#0C7C59")
        }
        canvas.drawPaint(p)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBackground(canvas)

        tileWidth = width / size
        tileHeight = height / size

        for (card in tiles) {
            card?.draw(canvas, tileWidth!!, tileHeight!!)
        }
    }

    private fun handleWin(): Boolean {
        for (tile in tiles) {
            if (tile != null) {
                return false
            }
        }
        gameRunning = false
        Toast.makeText(context, "You've won. Tap to start over", Toast.LENGTH_SHORT).show()
        return true
    }

    private fun handleMatching() {
        val t = Thread {
            Thread.sleep(1000)
            for (card in opened) {
                tiles[card.i * size + card.j] = null
            }
            opened.clear()
            invalidate()
        }
        t.start()
    }

    private fun delayedHide() {
        val t = Thread {
            Thread.sleep(1000)
            for (card in opened) {
                card.flip()
            }
            opened.clear()
            invalidate()
        }
        t.start()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            if (gameRunning) {
                if (opened.size < 2) {
                    val x = event.x
                    val y = event.y
                    val i = (x / tileWidth!!).toInt()
                    val j = (y / tileHeight!!).toInt()
                    val tile = tiles[i * size + j]
                    if (opened.size != 2) {
                        if (tile != null && !opened.contains(tile)) {
                            tile.flip()
                            opened.add(tile)
                        }
                    }

                    if (opened.size == 2) {
                        if (opened[0].value == opened[1].value) {
                            handleMatching()
                        } else {
                            delayedHide()
                        }
                    }
                }
            } else {
                gameRunning = true
                generateField()
            }
            invalidate();
            handleWin()
        }
        return true;
    }
}