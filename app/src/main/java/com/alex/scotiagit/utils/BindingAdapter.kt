package com.alex.scotiagit.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.alex.scotiagit.R
import com.squareup.picasso.Picasso


@BindingAdapter("android:binding_url")
fun setImageUrl(imageView: ImageView, url: String?) {
    if (url?.isNotEmpty() == true) {
        imageView.visibility = View.VISIBLE
        Picasso.get().load(url).placeholder(R.drawable.ic_baseline_photo)
            .into(imageView)
    } else {
        imageView.visibility = View.INVISIBLE
    }
}

@BindingAdapter("android:updated_at")
fun setUpdatedText(textView: TextView, date: String) {
    textView.text = textView.resources.getString(R.string.main_screen_item_updated_at, date)
}

@BindingAdapter("android:forks")
fun setFolksText(textView: TextView, intText: Int) {
    textView.text = textView.resources.getString(R.string.main_screen_item_forks, intText)
}

@BindingAdapter("android:stargazers_count")
fun setStargazersText(textView: TextView, intText: Int) {
    textView.text = textView.resources.getString(R.string.main_screen_item_stargazers, intText)
}