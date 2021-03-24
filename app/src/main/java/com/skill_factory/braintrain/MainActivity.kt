package com.skill_factory.braintrain

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.Button
import androidx.core.animation.doOnEnd
import androidx.dynamicanimation.animation.FloatValueHolder
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    private lateinit var fieldView: FieldView
    private var lvl = 3
        set(value) {
            field = min(value, maxLvl)
        }
    private val maxLvl = 25
    private val h = Handler(Looper.getMainLooper())
    private lateinit var animator: ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fieldView = findViewById<FieldView>(R.id.field_view)
        val rotationHolder: PropertyValuesHolder = PropertyValuesHolder.ofFloat("rotation", 0.0F, 360F)
        animator = ObjectAnimator.ofPropertyValuesHolder(fieldView, rotationHolder).apply {
            duration = lvl * 600L
            interpolator = DecelerateInterpolator()
            doOnEnd {
                fieldView.hide()
            }
        }
        startLevel(lvl)
        val nextLvl = findViewById<Button>(R.id.next_lvl)
        nextLvl.setOnClickListener {
            lvl++
            startLevel(lvl)
        }
    }

    fun startLevel(lvl: Int) {
        fieldView.field = Field(lvl)
        animator.start()
    }
}