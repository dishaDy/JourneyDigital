package com.example.journeydigital.utills

import android.util.Log


class LogUtils {


    companion object {


        /**
         * Don't use this when obfuscating class names!
         */


        fun logD(tag: String, message: String) {
            //noinspection PointlessBooleanExpression,ConstantConditions
//            if (BuildConfig.DEBUG) {
                Log.d(tag, message)
//            }
        }

        fun logD(tag: String, message: String, cause: Throwable) {
            //noinspection PointlessBooleanExpression,ConstantConditions
//            if (BuildConfig.DEBUG) {
                Log.d(tag, message, cause)
//            }
        }

        fun logV(tag: String, message: String) {
            //noinspection PointlessBooleanExpression,ConstantConditions
//            if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.VERBOSE)) {
                Log.v(tag, message)
//            }
        }

        fun logV(tag: String, message: String, cause: Throwable) {
            //noinspection PointlessBooleanExpression,ConstantConditions
//            if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.VERBOSE)) {
                Log.v(tag, message, cause)
//            }
        }

        fun logI(tag: String, message: String) {
            Log.i(tag, message)
        }

        fun logI(tag: String, message: String, cause: Throwable) {
            Log.i(tag, message, cause)
        }

        fun logW(tag: String, message: String) {
            Log.w(tag, message)
        }

        fun logW(tag: String, message: String, cause: Throwable) {
            Log.w(tag, message, cause)
        }

        fun logE(tag: String, ex: Exception) {
            Log.e(tag, Log.getStackTraceString(ex))
        }

        fun logE(tag: String, message: String, cause: Throwable) {
            Log.e(tag, message, cause)
        }

        fun logD(tag: String, message: String, shouldShow: Boolean) {
            //noinspection PointlessBooleanExpression,ConstantConditions
//            if ((BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) && shouldShow) {
                Log.d(tag, message)
//            }
        }

        fun logD(tag: String, message: String, cause: Throwable, shouldShow: Boolean) {
            //noinspection PointlessBooleanExpression,ConstantConditions
//            if ((BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) && shouldShow) {
                Log.d(tag, message, cause)
//            }
        }

        fun logV(tag: String, message: String, shouldShow: Boolean) {
            //noinspection PointlessBooleanExpression,ConstantConditions
//            if ((BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) && shouldShow) {
                Log.v(tag, message)
//            }
        }

        fun logV(tag: String, message: String, cause: Throwable, shouldShow: Boolean) {
            //noinspection PointlessBooleanExpression,ConstantConditions
//            if ((BuildConfig.DEBUG || Log.isLoggable(tag, Log.VERBOSE)) && shouldShow) {
                Log.v(tag, message, cause)
//            }
        }

        fun logI(tag: String, message: String, shouldShow: Boolean) {
            if (shouldShow) {
                Log.i(tag, message)
            }
        }

        fun logI(tag: String, message: String, cause: Throwable, shouldShow: Boolean) {
            if (shouldShow) {
                Log.i(tag, message, cause)
            }
        }

        fun logW(tag: String, message: String, shouldShow: Boolean) {
            if (shouldShow) {
                Log.w(tag, message)
            }
        }

        fun logW(tag: String, message: String, cause: Throwable, shouldShow: Boolean) {
            if (shouldShow) {
                Log.w(tag, message, cause)
            }
        }

        fun logE(tag: String, message: String, shouldShow: Boolean) {
            if (shouldShow) {
                Log.e(tag, message)
            }
        }

        fun logE(tag: String, message: String, cause: Throwable, shouldShow: Boolean) {
            if (shouldShow) {
                Log.e(tag, message, cause)
            }
        }

    }
}
