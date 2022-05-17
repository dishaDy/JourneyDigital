package com.example.journeydigital.extensions

import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.hideToolbar() {
    supportActionBar?.hide()
}

fun AppCompatActivity.showToolbar() {
    supportActionBar?.show()
}

fun AppCompatActivity.setToolbarTitle(title: String) {
    this.supportActionBar?.title = title
}

