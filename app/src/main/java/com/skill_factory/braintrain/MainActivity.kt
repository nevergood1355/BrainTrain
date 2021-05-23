package com.skill_factory.braintrain

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.view.doOnPreDraw
import kotlin.math.max
import kotlin.math.min


class MainActivity : AppCompatActivity() {
    val menuFragment = MenuFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.root, menuFragment).commit()
    }
}