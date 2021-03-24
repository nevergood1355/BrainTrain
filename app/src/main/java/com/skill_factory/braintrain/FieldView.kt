package com.skill_factory.braintrain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.graphics.drawable.toBitmap
import java.lang.Math.pow
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.random.Random

internal class FieldView : View {
    private val d1 = AppCompatResources.getDrawable(context, R.drawable.sq)!!
    private val d2 = AppCompatResources.getDrawable(context, R.drawable.sq_painted)!!
    private lateinit var painter: Array<Bitmap>
    private val brush = Paint()

    init {
        setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP && !isVisible) {
                val i = ((event.x - margin / 2) / (sqSize + margin)).toInt()
                val j = ((event.y) / (sqSize + margin)).toInt()
                if(model[i][j] == PAINTED) {
                    temp[i][j] = PAINTED
                    invalidate()
                }
                performClick()
            }
            return@setOnTouchListener true
        }
    }

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    private var sqSize = 0
    private var margin = 0F
    var field = Field(3)
        set(value) {
            field = value
            isVisible = true
            requestLayout()
        }


    private val model
        get() = field.model

    private val temp
        get() = field.temp

    private val size
        get() = model.size


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val w = measuredWidth
        margin = w / (2 * size * size).toFloat()
        sqSize = (w / size - margin).toInt()
        painter = arrayOf(d1, d2).map {
            it.toBitmap(sqSize, sqSize, Bitmap.Config.ARGB_8888)
        }.toTypedArray()

    }

    var isVisible = true

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
                val bitmap = painter[current[i][j]]
                canvas.drawBitmap(bitmap, margin / 2 + i * (sqSize + margin), j * (sqSize + margin), brush)
            }
        }

    }
}