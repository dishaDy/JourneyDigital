package com.example.journeydigital.utills

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import com.example.journeydigital.R


class LoadingDialog(context: Context) : Dialog(context, R.style.ProgressDialogTheme) {
    companion object {
        private var mainDialog: Dialog? = null

        fun show(
            context: Context,
            cancelable: Boolean = false
        ) {

            show(context, cancelable, null)
        }


        fun show(
            context: Context,

            cancelable: Boolean,

            cancelListener: DialogInterface.OnCancelListener?
        ) {
            dismissDialog()
            if (!(context as Activity).isFinishing) {
                val dialog = LoadingDialog(context)
                dialog.setCancelable(cancelable)
                dialog.setCanceledOnTouchOutside(cancelable)
                dialog.setOnCancelListener(cancelListener)
                dialog.setContentView(R.layout.progressbar_layout)
                dialog.show()
                mainDialog = dialog
            }

        }

        /**
         * Dismiss dialog.
         */
        fun dismissDialog() {
            if (null != mainDialog && mainDialog!!.isShowing) {
                mainDialog!!.dismiss()
            }
            mainDialog = null
        }

    }
}
/**
 * Show.
 *
 * @param context the context
 * @param message the message
 */
/**
 * Show.
 *
 * @param context    the context
 * @param message    the message
 * @param cancelable the cancelable
 */

