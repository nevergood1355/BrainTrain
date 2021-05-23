package com.skill_factory.braintrain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.MeasureSpec.*
import android.view.View.OnTouchListener
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap

internal class FieldView : View {
    private val d1 = AppCompatResources.getDrawable(context, R.drawable.sq)!!
    private val d2 = AppCompatResources.getDrawable(context, R.drawable.sq_painted)!!
    private val d3 = AppCompatResources.getDrawable(context, R.drawable.sq_wrong)!!
    private lateinit var painter: Array<Bitmap>
    private lateinit var wrongBitmap: Bitmap
    private val brush = Paint()

    var onRightAnswerListener: () -> Unit = { }
    var onWrongAnswerListener: () -> Unit = { }

    private val onTouchListener = OnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_UP && !isVisible) {
            val j = ((event.x) / (sqSize)).toInt()
            val i = ((event.y) / (sqSize)).toInt()
            if (model[i][j] == PAINTED && temp[i][j] == EMPTY) {
                temp[i][j] = PAINTED
                invalidate()
                onRightAnswerListener.invoke()
            } else if (model[i][j] == EMPTY) {
                temp[i][j] = WRONG
                onWrongAnswerListener.invoke()
                invalidate()
            }
            performClick()
            return@OnTouchListener false
        } else {
            return@OnTouchListener true
        }
    }

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    private var sqSize = 0

    var field = Field(4)
        set(value) {
            field = value
            onSizeChangedImp()
            show()
        }

    private val model
        get() = field.model

    private val temp
        get() = field.temp

    private val size
        get() = model.size



    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        onSizeChangedImp()
    }

    private fun onSizeChangedImp() {
        val w = measuredWidth
        sqSize = (w / size)
        painter = arrayOf(d1, d2).map {
            it.toBitmap(sqSize, sqSize, Bitmap.Config.ARGB_8888)
        }.toTypedArray()
        wrongBitmap = d3.toBitmap(sqSize, sqSize, Bitmap.Config.ARGB_8888)
    }

    private var isVisible = true

    fun hide() {
        isVisible = false
        invalidate()
    }

    fun show() {
        isVisible = true
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until size) {
            for (j in 0 until size) {
                val current = if (isVisible) model else temp
                val bitmap = if (current[i][j] >= EMPTY) {
                    painter[current[i][j]]
                } else {
                    wrongBitmap
                }
                canvas.drawBitmap(bitmap, j * sqSize.toFloat(), i * sqSize.toFloat(), brush)
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setOnTouchListener(onTouchListener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        setOnTouchListener(null)
    }
}