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


class EqualizerView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint: Paint = Paint()
    private val r = Rect()
    private val strokeWidth = 5F

    private val columns: ArrayList<Rect> = arrayListOf(Rect(), Rect(), Rect(), Rect(), Rect())
    private val columnsValues: ArrayList<Int> = arrayListOf(0, 0, 0, 0, 0)

    fun getColumnsValues(): ArrayList<Int>{
        return columnsValues
    }

    private var columnsTop = 0
    private var columnsBottom = 0
    private var columnsWidth = 0

    private var onEqualizerDataChangedListener: OnEqualizerDataChangedListener? = null

    interface OnEqualizerDataChangedListener {
        fun onEqualizerDataChanged(values: ArrayList<Int>)
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
            if (x >= columns[i].left && x <= columns[i].right &&
                y >= columns[i].top && y <= columns[i].bottom
            ) {
                return i
            }
        }
        return null
    }

    private fun getTouchedValue(y: Int): Int {
        return ((columnsBottom - y).toDouble() / (columnsBottom - columnsTop) * 100).toInt()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        columnsWidth = width / 11
        val columnTopBottomPadding = (height * 0.1) / 2
        val columnHeight = height - (columnTopBottomPadding * 2)
        columnsTop = (height - columnTopBottomPadding - columnHeight).toInt()
        columnsBottom = (height - columnTopBottomPadding).toInt()

        drawViewBorder(canvas)
        drawColumns(canvas)
        for (i in 0..4) {
            setColumnValue(canvas, i + 1, columnsValues[i])
        }
    }

    private fun drawViewBorder(canvas: Canvas?) {
        paint.color = Color.parseColor("#2F80ED")
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth * 2
        setRectAttrs(r, 0, 0, width, height)
        canvas?.drawRect(r, paint)
    }

    private fun drawColumns(canvas: Canvas?) {
        paint.color = Color.parseColor("#333333")
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth

        for (i in 1..10 step 2) {
            val columnIndex = (i - 1) / 2
            setRectAttrs(
                columns[columnIndex],
                (columnsWidth * i),
                columnsTop,
                (columnsWidth * (i + 1)),
                columnsBottom
            )
            canvas?.drawRect(columns[columnIndex], paint)
        }
    }

    private fun setColumnValue(
        canvas: Canvas?, columnNumber: Int, value: Int
    ) {
        val halfStrokeWidth = strokeWidth / 2
        val columnIndex = (columnNumber * 2) - 1
        val valueTop = (columnsBottom * (100 - value) + value * columnsTop) / 100

        paint.color = Color.parseColor("#2F80ED")
        paint.style = Paint.Style.FILL
        setRectAttrs(
            r = r,
            left = ((columnsWidth * columnIndex) + halfStrokeWidth.toInt()),
            top = (valueTop + halfStrokeWidth).toInt(),
            right = (columnsWidth * (columnIndex + 1) - halfStrokeWidth).toInt(),
            bottom = (columnsBottom - halfStrokeWidth).toInt()
        )
        canvas?.drawRect(r, paint)
    }

    private fun setRectAttrs(r: Rect, left: Int, top: Int, right: Int, bottom: Int) {
        r.left = left
        r.top = top
        r.right = right
        r.bottom = bottom
    }
}