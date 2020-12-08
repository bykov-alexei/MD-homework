package com.example.colortiles

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

/**
 * TODO: document your custom view class.
 */
class TilesView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var tiles = Array(4) {Array(4) {0} }
    val darkColor = Color.GRAY
    val brightColor = Color.YELLOW

    var tileWidth: Int? = null
    var tileHeight: Int? = null

    var padding = 5

    init {
        val colors = listOf(darkColor, brightColor);
        for (i in 0..3) {
            for (j in 0..3) {
                tiles[i][j] = colors.shuffled()[0];
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = canvas.width
        val height = canvas.height
        tileWidth = width / 4
        tileHeight = height / 4

        val p = Paint().apply {
            color = Color.BLACK
            style = Paint.Style.FILL
        }
        canvas.drawPaint(p)

        val tile1 = Paint().apply {
            style = Paint.Style.FILL
            color = darkColor
            strokeWidth = 2F
        }
        val tile2 = Paint().apply {
            style = Paint.Style.FILL
            color = brightColor
            strokeWidth = 2F
        }
        for (i in 0..3) {
            for (j in 0..3) {
                val style = if (tiles[i][j] == brightColor) tile2 else tile1
                val r = Rect(tileWidth!! * i + 2, tileHeight!! * j + 2, tileWidth!! * (i + 1) - 2, tileHeight!! * (j + 1) - 2)
                Log.d("Rect", "${r.left} ${r.top} $tileWidth $tileHeight")
                canvas.drawRect(r, style)
            }
        }
    }

    private fun checkWin(): Boolean {
        for (i in 0..3) {
            for (j in 0..3) {
                if (tiles[i][j] != tiles[0][0]) {
                    return false
                }
            }
        }
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.x
        val y = event.y
        if (event.action == MotionEvent.ACTION_DOWN) {
            val i = (x / tileWidth!!).toInt()
            val j = (y / tileHeight!!).toInt()
            tiles[i][j] = if (tiles[i][j] == darkColor) brightColor else darkColor
        }
        if (checkWin()) {
            Toast.makeText(context, "You've won!!!", Toast.LENGTH_SHORT).show()
        }
        invalidate();
        return true;
    }
}