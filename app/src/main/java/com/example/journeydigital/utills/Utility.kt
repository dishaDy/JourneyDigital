package com.example.journeydigital.utills

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

object Utility {
    fun showOkDialog(
        context: Context,
        message: String,
        onClickListener: DialogInterface.OnClickListener,
        okText: String
    ) {
        AlertDialog.Builder(context).setMessage(message).setCancelable(false)
            .setPositiveButton(
                okText
            ) { p0, p1 -> onClickListener.onClick(p0, p1) }
            .show()
    }
}