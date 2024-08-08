package com.jones.customview.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.jones.customview.R

class CustomView(context: Context, attributes: AttributeSet): LinearLayout(context, attributes) {
    init {
        inflate(context, R.layout.custom_view, this)
        val attrs = context.obtainStyledAttributes(attributes, R.styleable.CustomView)
        val imageView = findViewById<ImageView>(R.id.ivImage)
        val textView = findViewById<TextView>(R.id.tvTextView)

        imageView.setImageResource(
            attrs.getResourceId(R.styleable.CustomView_image, R.drawable.ic_android)
        )

        textView.text = attrs.getString(R.styleable.CustomView_text)
        attrs.recycle()
    }
}