package com.jones.customview.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class PathDrawing(context: Context, attributes: AttributeSet) : View(context, attributes) {

    private val paths = mutableListOf<Pair<Path, Paint>>()
    private val paint = Paint()
    private var currentColor = Paint()

    init {
        paint.color = Color.BLUE
        currentColor.color = paint.color
        paint.strokeWidth = 16f
        paint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paths.forEach {
            canvas.drawPath(it.first, it.second)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                paths.add(Pair(Path(), Paint(currentColor)))
                paths.last().first.moveTo(event.x, event.y)
                Log.d("debugging", "${event.x} ${event.y}")
            }
            MotionEvent.ACTION_MOVE -> {
                paths.last().first.lineTo(event.x, event.y)
                Log.d("debugging", "${event.x} ${event.y}")
            }
            MotionEvent.ACTION_UP -> {
                paths.last().first.lineTo(event.x, event.y)
            }
            else -> return false
        }
        postInvalidate()
        return true
    }

    fun setColor(color: Int) {
        currentColor = Paint().apply {
            this.color = color
            this.strokeWidth = paint.strokeWidth
            this.style = paint.style
        }
    }

    fun undo() {
        if (paths.size == 0) return
        paths.removeLast()
        invalidate()
    }

    fun reset() {
        paths.clear()
        invalidate()
    }
}
