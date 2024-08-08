package com.jones.customview.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class CircleDrawing(context: Context, attributes: AttributeSet): View(context, attributes) {
    private val points = mutableListOf<Point>()
    private val paint = Paint()

    init {
        paint.color = Color.BLUE
        paint.strokeWidth = 1f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(this.width/2f, this.height/2f, 100f, paint)

        points.forEach { point ->
            canvas.drawCircle(
                point.x, point.y, 10f, paint
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("debugging", "${event?.x} ${event?.y}")
        event?.let {
            val x0 = it.x - this.width/2
            val y0 = it.y - this.height/2
            val z = x0*x0 + y0*y0 - 100*100
            if (z <= 0) {
                 points.clear()
            } else {
                points.add(Point(it.x, it.y))
            }
        }
        performClick()
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        invalidate()
        return super.performClick()
    }

    class Point(
        val x: Float,
        val y: Float
    )

    /*
    Equation of a circle located at origin is x^2 + y^2 = r^2
    if not located at origin (x-x1)^2 + (y-y1)^2 = r^2

    z = (x-w2)^2 + (y-h2)^2 - r^2
     */

}