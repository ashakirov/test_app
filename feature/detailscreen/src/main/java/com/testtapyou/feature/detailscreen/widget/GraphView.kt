package com.testtapyou.feature.detailscreen.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import com.testtapyou.data.network.model.Point
import kotlin.math.abs
import kotlin.math.min
import androidx.core.graphics.toColorInt
import kotlin.math.max

class GraphView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val points = mutableListOf<Point>()
    private val path = Path()

    private var scaleDetector: ScaleGestureDetector
    private var activePointerId = MotionEvent.INVALID_POINTER_ID

    private var scaleFactor = MIN_SCALE
    private var lastTouchX = 0f
    private var lastTouchY = 0f

    private var offsetX = -1f
    private var offsetY = -1f

    private var isInited = false

    init {
        scaleDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                scaleFactor = max(MIN_SCALE, scaleFactor * detector.scaleFactor)
                updateLineWidths()

                invalidate()
                return true
            }
        })
    }

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        style = Paint.Style.STROKE
    }

    private val axisPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
    }

    private val pointPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    private fun updateLineWidths() {
        linePaint.strokeWidth = STROKE_WIDTH / scaleFactor
        axisPaint.strokeWidth = STROKE_WIDTH / scaleFactor
    }

    fun setPoints(points: List<Point>) {
        this.points.clear()
        //меняю знак Y т.к. Y ось view и графика противоположные
        val reversedY = points.map { Point(it.x, -it.y) }
        this.points.addAll(reversedY)
        this.points.sortBy { it.x }
        fillCurvePath()

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(BG_COLOR)

        if (width <= 0 || height <= 0 || points.isEmpty()) {
            return
        }

        if (!isInited) {
            isInited = true

            initScaleAndOffsets()
        }

        canvas.scale(scaleFactor, scaleFactor, offsetX, offsetY)
        canvas.translate(offsetX, offsetY)

        //оси
        canvas.drawLine(-abs(offsetX), 0f, width * scaleFactor, 0f, axisPaint)
        canvas.drawLine(0f, -abs(offsetY), 0f, height * scaleFactor, axisPaint)

        //точки
        points.forEach { point ->
            canvas.drawCircle(point.x, point.y, POINT_SIZE / scaleFactor, pointPaint)
        }

        //график
        canvas.drawPath(path, linePaint)
    }

    private fun initScaleAndOffsets() {
        val minX = points.first().x
        val maxX = points.last().x

        val minY = points.minBy { it.y }.y
        val maxY = points.maxBy { it.y }.y

        scaleFactor = if (points.size == 1) {
            SINGLE_POINT_SCALE
        } else {
            min(width / (maxX - minX), height / (maxY - minY))
        }
        updateLineWidths()

        //центрирование графика при запуске
        offsetX = (width - (minX + maxX) * scaleFactor) / 2f
        offsetY = (height - (minY + maxY) * scaleFactor) / 2f
    }

    private fun fillCurvePath() {
        path.reset()
        path.moveTo(points[0].x, points[0].y)

        for (i in 1 until points.size) {
            val p0 = points[i - 1]
            val p1 = points[i]

            val cX = (p0.x + p1.x) / 2
            path.cubicTo(
                cX, p0.y,
                cX, p1.y,
                p1.x, p1.y
            )
        }
    }

    //https://developer.android.com/develop/ui/views/touch-and-input/gestures/scale
    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                event.getActionIndex().also { pointerIndex ->
                    lastTouchX = event.getX(pointerIndex)
                    lastTouchY = event.getY(pointerIndex)
                }
                activePointerId = event.getPointerId(0)
            }

            MotionEvent.ACTION_MOVE -> {
                val (x: Float, y: Float) =
                    event.findPointerIndex(activePointerId).let { pointerIndex ->
                        event.getX(pointerIndex) to
                                event.getY(pointerIndex)
                    }

                offsetX += x - lastTouchX
                offsetY += y - lastTouchY

                invalidate()

                lastTouchX = x
                lastTouchY = y
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                activePointerId = MotionEvent.INVALID_POINTER_ID
            }

            MotionEvent.ACTION_POINTER_UP -> {
                event.actionIndex.also { pointerIndex ->
                    event.getPointerId(pointerIndex)
                        .takeIf { it == activePointerId }
                        ?.run {
                            val newPointerIndex = if (pointerIndex == 0) 1 else 0
                            lastTouchX = event.getX(newPointerIndex)
                            lastTouchY = event.getY(newPointerIndex)
                            activePointerId = event.getPointerId(newPointerIndex)
                        }
                }
            }
        }
        return true
    }

    companion object {
        const val SINGLE_POINT_SCALE = 3f
        const val STROKE_WIDTH = 6f
        const val POINT_SIZE = STROKE_WIDTH * 2
        const val MIN_SCALE = 1f

        val BG_COLOR = "#EEEEEE".toColorInt()
    }
}