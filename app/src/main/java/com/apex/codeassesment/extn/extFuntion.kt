package com.sample.extn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.apex.codeassesment.R
import com.apex.codeassesment.ui.details.DetailsActivity
import com.bumptech.glide.Glide



fun ImageView.loadImage(url: String) = Glide.with(this)
    .load(url)
    .placeholder(R.drawable.placeholder_image).into(this)


fun Context.showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()


fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun <T> Context.navigateToScreen(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

