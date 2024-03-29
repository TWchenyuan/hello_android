package com.thoughtworks.androidtrain.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import com.thoughtworks.androidtrain.R


class LongTextFragment(
    private val text: String,
    private val gravity: Int = Gravity.TOP
) : Fragment() {
    private lateinit var textView: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_long_text, container, false)
        textView = view.findViewById(R.id.text_view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView.text = this.text
        textView.gravity = this.gravity
    }
}
