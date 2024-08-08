package com.jones.customview.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class TransformView(
    context: Context,
    attributeSet: AttributeSet
): View(context, attributeSet) {
    private val shapes = mutableListOf<Shape>(
        Circle(0f, 0f, 100f),
        Circle(150f, 150f, 50f),
        Circle(-150f, 150f, 50f),
        Rect(
            Point(200f, 200f),
            Point(300f, 200f),
            Point(300f, 300f),
            Point(200f, 300f)
        )
    )
    private var isMoving = false

    private val paint = Paint().apply {
        color = Color.YELLOW
        strokeWidth = 4f
    }

    fun createRandomShape() {
        val random = Random(System.currentTimeMillis())
        val randomShape = if (random.nextBoolean()) {
            Circle(
                random.nextFloat() * width - width / 2,
                random.nextFloat() * height - height / 2,
                random.nextFloat() * 100 + 20
            )
        } else {
            val p0 = Point(random.nextFloat() * width - width / 2, random.nextFloat() * height - height / 2)
            val p1 = Point(p0.x + random.nextFloat() * 100 + 20, p0.y)
            val p2 = Point(p1.x, p1.y + random.nextFloat() * 100 + 20)
            val p3 = Point(p0.x, p2.y)
            Rect(p0, p1, p2, p3)
        }
        shapes.add(randomShape)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val originX = this.width/2f
        val originY = this.height/2f
        shapes.forEach {
            it.draw(canvas, originX, originY, paint)
        }
    }

    fun rotate(theta: Float, duration: Int) {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            val nFrame = (50*duration / 1000)
            val dt = theta / nFrame

            for(i in 1..nFrame) {
                delay(20)
                shapes.forEach { it.rotate(dt) }
                invalidate()
            }
        }
    }

    interface Shape {
        fun draw(canvas: Canvas, originX: Float, originY: Float, paint: Paint)
        fun rotate(theta: Float)
    }

    class Circle(
        var cx: Float,
        var cy: Float,
        var radius: Float
    ): Shape {
        override fun rotate(theta: Float) {
            val nx = cx * cos(theta) - cy * sin(theta)
            val ny = cx * sin(theta) + cy * cos(theta)
            cx = nx
            cy = ny

        }

        override fun draw(
            canvas: Canvas,
            originX: Float,
            originY: Float,
            paint: Paint
        ) {
            canvas.drawCircle(
                cx+originX,
                cy+originY,
                radius,
                paint
            )
        }
    }

    class Rect(
        var p0: Point,
        var p1: Point,
        var p2: Point,
        var p3: Point
    ): Shape {
        override fun draw(canvas: Canvas, originX: Float, originY: Float, paint: Paint) {
            val path = Path()
            path.moveTo(p0.x+originX, p0.y+originY)
            path.lineTo(p1.x+originX, p1.y+originY)
            path.lineTo(p2.x+originX, p2.y+originY)
            path.lineTo(p3.x+originX, p3.y+originY)
            path.close()

            canvas.drawPath(path, paint)
        }

        override fun rotate(theta: Float) {
            p0 = p0.rotate(theta)
            p1 = p1.rotate(theta)
            p2 = p2.rotate(theta)
            p3 = p3.rotate(theta)
        }
    }

    data class Point(
        var x: Float,
        var y: Float
    ) {
        fun rotate(theta: Float): Point {
            val nx = x * cos(theta) - y * sin(theta)
            val ny = x * sin(theta) + y * cos(theta)
            return Point(nx, ny)
        }
    }


    fun move(dx: Int, dy: Int) {
        if(isMoving) return
        isMoving = true

        var xdir = 1
        var ydir = 1
//        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
//            while (true) {
//                delay(20)
//                circleOrigin.x += dx*xdir
//                circleOrigin.y += dy*ydir
//                invalidate()
//                val w = this@TransformView.width - 100
//                val h = this@TransformView.height - 100
//
//                if(circleOrigin.x > w || circleOrigin.x < 100) {
//                    xdir *= -1
//                }
//
//                if(circleOrigin.y > h || circleOrigin.y < 100) {
//                    ydir *= -1
//                }
//            }
//        }
    }
//
}


// 1000 ms 50
// d ms 50*d / 1000

/*
cos(theta) -sin(theta)     x      x1
                        *     =
sin(theta)  cos(theta)     y      y1

x1 = x*cost - y*sint
y1 = x*sint + y*cost
 */

