package com.example.travelapp.data.util

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.travelapp.R

@BindingAdapter("displayImage")
fun ImageView.setDisplayImage(countryId: String) {
    val image = when (countryId) {
        "D" -> R.drawable.ic_germany
        "I" -> R.drawable.ic_italy
        "NL" -> R.drawable.ic_netherlands
        "B" -> R.drawable.ic_belgium
        "F" -> R.drawable.ic_france
        "A" -> R.drawable.ic_austria
        "CH" -> R.drawable.ic_switzerland
        else -> android.R.drawable.progress_indeterminate_horizontal
    }
    val drawable = ContextCompat.getDrawable(this.context, image)
    this.setImageDrawable(drawable)
}