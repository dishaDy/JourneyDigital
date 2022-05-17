package com.example.journeydigital.extensions

import android.view.View
/**
 * Visibility modifiers and check functions
 */
fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeInVisible() {
    this.visibility = View.INVISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}



