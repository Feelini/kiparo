package com.soldatov.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class EqualizerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {

    private val viewBorderPaint: Paint = Paint()
    private val columnsBorderPaint: Paint = Paint()
    private val columnsValuePaint: Paint = Paint()
    private val viewBorderRect = Rect()
    private val columnsValueRect = Rect()

    private val columns: ArrayList<Rect> = arrayListOf(Rect(), Rect(), Rect(), Rect(), Rect())
    private val columnsValues: ArrayList<Int> = arrayListOf(0, 0, 0, 0, 0)

    private var columnsCoordinateTop = 0
    private var columnsCoordinateBottom = 0
    private var columnsWidth = 0
    private var columnsHeight = 0
    private var columnsTopBottomPadding = 0

    private var onEqualizerDataChangedListener: OnEqualizerDataChangedListener? = null

    companion object {
        private const val STROKE_WIDTH = 5F
    }

    interface OnEqualizerDataChangedListener {
        fun onEqualizerDataChanged(values: ArrayList<Int>)
    }

    fun getColumnsValues(): ArrayList<Int> {
        return columnsValues
    }

    fun setEqualizerDataChangedListener(listener: OnEqualizerDataChangedListener) {
        onEqualizerDataChangedListener = listener
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)

        var x = event.x.toInt()
        var y = event.y.toInt()
        val columnValue: Int
        var columnNumber: Int? = null

        when (event.action) {
            MotionEvent.ACTION_DOWN ->
                columnNumber = getTouchedColumn(x, y)
            MotionEvent.ACTION_MOVE -> {
                x = event.x.toInt()
                y = event.y.toInt()
                columnNumber = getTouchedColumn(x, y)
            }
            MotionEvent.ACTION_UP -> {
                x = event.x.toInt()
                y = event.y.toInt()
                columnNumber = getTouchedColumn(x, y)
            }
        }

        if (columnNumber != null) {
            columnValue = getTouchedValue(event.y.toInt())
            columnsValues[columnNumber] = columnValue
        }
        onEqualizerDataChangedListener?.onEqualizerDataChanged(columnsValues)
        invalidate()
        return false
    }

    private fun getTouchedColumn(x: Int, y: Int): Int? {
        for (i in 0..4) {
            if (columns[i].contains(x, y)) {
                return i
            }
        }
        return null
    }

    private fun getTouchedValue(y: Int): Int {
        return ((columnsCoordinateBottom - y).toDouble() / (columnsCoordinateBottom - columnsCoordinateTop) * 100).toInt()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        columnsWidth = width / 11
        columnsTopBottomPadding = ((height * 0.1) / 2).toInt()
        columnsHeight = height - (columnsTopBottomPadding * 2)
        columnsCoordinateTop = height - columnsTopBottomPadding - columnsHeight
        columnsCoordinateBottom = height - columnsTopBottomPadding
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawViewBorder(canvas)
        drawColumns(canvas)

        for (i in 0..4) {
            setColumnValue(canvas, columnNumber = i + 1, columnsValues[i])
        }
    }

    private fun drawViewBorder(canvas: Canvas?) {
        viewBorderPaint.color = Color.parseColor("#2F80ED")
        viewBorderPaint.style = Paint.Style.STROKE
        viewBorderPaint.strokeWidth = Companion.STROKE_WIDTH * 2
        setRectAttrs(viewBorderRect, 0, 0, width, height)
        canvas?.drawRect(viewBorderRect, viewBorderPaint)
    }

    private fun drawColumns(canvas: Canvas?) {
        columnsBorderPaint.color = Color.parseColor("#333333")
        columnsBorderPaint.style = Paint.Style.STROKE
        columnsBorderPaint.strokeWidth = Companion.STROKE_WIDTH

        for (i in 1..10 step 2) {
            val columnIndex = (i - 1) / 2
            setRectAttrs(
                columns[columnIndex],
                (columnsWidth * i),
                columnsCoordinateTop,
                (columnsWidth * (i + 1)),
                columnsCoordinateBottom
            )
            canvas?.drawRect(columns[columnIndex], columnsBorderPaint)
        }
    }

    private fun setColumnValue(
        canvas: Canvas?, columnNumber: Int, value: Int
    ) {
        val halfStrokeWidth = Companion.STROKE_WIDTH / 2
        val columnIndex = (columnNumber * 2) - 1
        val valueTop = (columnsCoordinateBottom * (100 - value) + value * columnsCoordinateTop) / 100

        columnsValuePaint.color = Color.parseColor("#2F80ED")
        columnsValuePaint.style = Paint.Style.FILL
        setRectAttrs(
            rect = columnsValueRect,
            left = ((columnsWidth * columnIndex) + halfStrokeWidth.toInt()),
            top = (valueTop + halfStrokeWidth).toInt(),
            right = (columnsWidth * (columnIndex + 1) - halfStrokeWidth).toInt(),
            bottom = (columnsCoordinateBottom - halfStrokeWidth).toInt()
        )
        canvas?.drawRect(columnsValueRect, columnsValuePaint)
    }

    private fun setRectAttrs(rect: Rect, left: Int, top: Int, right: Int, bottom: Int) {
        rect.left = left
        rect.top = top
        rect.right = right
        rect.bottom = bottom
    }
}