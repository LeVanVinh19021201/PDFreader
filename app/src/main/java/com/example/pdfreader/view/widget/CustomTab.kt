package com.example.pdfreader.view.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.pdfreader.R

class CustomTab(context: Context, attributeSet: AttributeSet? = null) :
    LinearLayout(context, attributeSet) {
    var icon: ImageView
    var text: TextView

    init {
        inflate(getContext(), R.layout.custom_tab_viewpager, this)
        icon = findViewById(R.id.img_view_tab)
        text = findViewById(R.id.tv_title_tab)
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        val color = if (selected) {
            R.color.color_primary

        } else {
            R.color.color_sub_tab
        }
        text.setTextColor(ContextCompat.getColor(context, color))
        icon.setColorFilter(
            ContextCompat.getColor(context, color),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
    }
}