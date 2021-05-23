package com.skill_factory.braintrain

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import kotlin.math.max
import kotlin.math.min

const val MAX_HP = 3
const val MAX_LVL = 25
const val START_LVL = 1

open class GameFragment : Fragment() {

    private var text_lvl: TextView? = null
    var counterForNextLvL = 0
    internal var lvl = START_LVL
        set(value) {
            field = min(value, MAX_LVL)
            counterForNextLvL = 0
            text_lvl?.text = "Уровень ${field}"
        }

    private var hpBarView: HpBarView? = null
    private var hp = MAX_HP
        set(value) {
            field = value
            hpBarView?.value = field
        }

    internal lateinit var fieldView: FieldView
    private val h = Handler(Looper.getMainLooper())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.game_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Finding views by id
        fieldView = view.findViewById<FieldView>(R.id.field_view)
        text_lvl = view.findViewById(R.id.lvl)
        hpBarView = view.findViewById(R.id.hp_bar)

        fieldView.doOnPreDraw {
            startLevel(lvl)
        }

        //Bind listener on wrong answer
        fieldView.onWrongAnswerListener = {
            hp--
            if (hp < 0) {
                hp = MAX_HP
                //Backward for 3 levels
                lvl = max(lvl - 2, START_LVL);
                startLevel(lvl)
            }
        }

        //Bind listener on right answer1
        fieldView.onRightAnswerListener = {
            counterForNextLvL++
            if (counterForNextLvL == fieldView.field.shaded) {
                //Next lvl
                lvl = min(lvl + 1, MAX_LVL);
                startLevel(lvl)
            }
        }
    }


    open fun startLevel(lvl: Int) {
        fieldView.field = Field(lvl)
        h.postDelayed({
            fieldView.hide()
        }, lvl * 700L)
    }
}