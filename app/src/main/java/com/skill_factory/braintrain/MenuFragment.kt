package com.skill_factory.braintrain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class MenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.menu_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menu1 = view.findViewById<View>(R.id.menu1)
        val menu2 = view.findViewById<View>(R.id.menu2)
        menu1.setOnClickListener {
          requireFragmentManager().beginTransaction().replace(R.id.root, GameFragment()).commit()
        }
        menu2.setOnClickListener {
            requireFragmentManager().beginTransaction().replace(R.id.root, TwistingFragment()).commit()
        }
    }
}