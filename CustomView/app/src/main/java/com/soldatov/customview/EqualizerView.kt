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

const val colorBlue = "#2F80ED"
const val colorGray = "#333333"
const val STROKE_WIDTH = 5F
const val topAndBottomPadding = 0.1


class EqualizerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {

    private val viewBorderPaint: Paint = Paint().apply {
        this.color = Color.parseColor(colorBlue)
        this.style = Paint.Style.STROKE
        this.strokeWidth = STROKE_WIDTH * 2
    }

    private val columnsBorderPaint: Paint = Paint().apply {
        this.color = Color.parseColor(colorGray)
        this.style = Paint.Style.STROKE
        this.strokeWidth = STROKE_WIDTH
    }

    private val columnsValuePaint: Paint = Paint().apply {
        this.color = Color.parseColor(colorBlue)
        this.style = Paint.Style.FILL
    }

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

        val x = event.x.toInt()
        val y = event.y.toInt()
        val columnValue: Int
        var columnNumber: Int? = null

        when (event.action) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE,
            MotionEvent.ACTION_UP -> {
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
        for (i in 0 until columns.size) {
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
        columnsWidth = width / ((columns.size * 2) + 1)
        columnsTopBottomPadding = ((height * topAndBottomPadding) / 2).toInt()
        columnsHeight = height - (columnsTopBottomPadding * 2)
        columnsCoordinateTop = height - columnsTopBottomPadding - columnsHeight
        columnsCoordinateBottom = height - columnsTopBottomPadding
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawViewBorder(canvas)
        drawColumns(canvas)

        for (i in 0 until columnsValues.size) {
            setColumnValue(canvas, columnNumber = i + 1, columnsValues[i])
        }
    }

    private fun drawViewBorder(canvas: Canvas?) {
        viewBorderRect.left = 0
        viewBorderRect.top = 0
        viewBorderRect.right = width
        viewBorderRect.bottom = height
        canvas?.drawRect(viewBorderRect, viewBorderPaint)
    }

    private fun drawColumns(canvas: Canvas?) {
        for (i in 1..(columns.size * 2) step 2) {
            val columnIndex = (i - 1) / 2
            columns[columnIndex].left = columnsWidth * i
            columns[columnIndex].top = columnsCoordinateTop
            columns[columnIndex].right = columnsWidth * (i + 1)
            columns[columnIndex].bottom = columnsCoordinateBottom
            canvas?.drawRect(columns[columnIndex], columnsBorderPaint)
        }
    }

    private fun setColumnValue(canvas: Canvas?, columnNumber: Int, value: Int) {
        val halfStrokeWidth = STROKE_WIDTH / 2
        val columnIndex = (columnNumber * 2) - 1
        val valueTop =
            (columnsCoordinateBottom * (100 - value) + value * columnsCoordinateTop) / 100

        columnsValueRect.left = (columnsWidth * columnIndex) + halfStrokeWidth.toInt()
        columnsValueRect.top = (valueTop + halfStrokeWidth).toInt()
        columnsValueRect.right = (columnsWidth * (columnIndex + 1) - halfStrokeWidth).toInt()
        columnsValueRect.bottom = (columnsCoordinateBottom - halfStrokeWidth).toInt()

        canvas?.drawRect(columnsValueRect, columnsValuePaint)
    }
}