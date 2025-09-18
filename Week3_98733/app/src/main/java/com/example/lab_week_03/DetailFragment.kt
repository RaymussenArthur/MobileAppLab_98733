package com.example.lab_week_03

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coffeeTitle: TextView = view.findViewById(R.id.coffee_title)
        val coffeeDesc: TextView = view.findViewById(R.id.coffee_desc)
        val backButton: Button = view.findViewById(R.id.btn_back)

        val coffeeId = arguments?.getInt(ListFragment.COFFEE_ID, 0) ?: 0

        when (coffeeId) {
            R.id.affogato -> {
                coffeeTitle.text = getString(R.string.affogato_title)
                coffeeDesc.text = getString(R.string.affogato_desc)
            }
            R.id.americano -> {
                coffeeTitle.text = getString(R.string.americano_title)
                coffeeDesc.text = getString(R.string.americano_desc)
            }
            R.id.latte -> {
                coffeeTitle.text = getString(R.string.latte_title)
                coffeeDesc.text = getString(R.string.latte_desc)
            }
            R.id.cappuccino -> {
                coffeeTitle.text = getString(R.string.cappuccino_title)
                coffeeDesc.text = getString(R.string.cappuccino_desc)
            }
            R.id.mocha -> {
                coffeeTitle.text = getString(R.string.mocha_title)
                coffeeDesc.text = getString(R.string.mocha_desc)
            }
            else -> {
                coffeeTitle.text = getString(R.string.coffee_title)
                coffeeDesc.text = getString(R.string.coffee_detail)
            }
        }

        backButton.setOnClickListener {
            view.findNavController().navigateUp()
        }
    }
}
