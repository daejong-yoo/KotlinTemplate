package com.example.kotlintemplate.util

import android.util.Log
import com.google.gson.GsonBuilder

/**
 * 로그 통합관리
 */
class GLog {
    // 배포 시 isRetrofitLog, isLogPrint false 처리 필요.
    companion object {
        private const val isLogPrint = true
        const val isRequestHeaderLog = false
        const val isResponseHeaderLog = false

        private const val TAG = "ssb10300"
        private const val MAX_INDEX = 3000
        private const val LOG_DEBUG = 0
        private const val LOG_VERBOSE = 1
        private const val LOG_INFO = 2
        private const val LOG_WARN = 3
        private const val LOG_ERROR = 4
        private val gson = GsonBuilder().setPrettyPrinting().create()

        @Synchronized
        fun d(msg: String) {
            if (isLogPrint) dLong(msg, LOG_DEBUG)
        }

        @Synchronized
        fun v(msg: String) {
            if (isLogPrint) dLong(msg, LOG_VERBOSE)
        }

        @Synchronized
        fun i(msg: String) {
            if (isLogPrint) dLong(msg, LOG_INFO)
        }

        @Synchronized
        fun w(msg: String) {
            if (isLogPrint) dLong(msg, LOG_WARN)
        }

        @Synchronized
        fun e(msg: String) {
            if (isLogPrint) dLong(msg, LOG_ERROR)
        }

        fun e(msg: String?, e: Exception?) {
            if (isLogPrint) Log.e(TAG, msg, e)
        }

        fun toJson(src: Any?): String {
            //JSON 이쁘게 출력
            return if (isLogPrint) gson.toJson(src) else ""
        }

        fun getPretty(jsonString: String?): String {
            if (jsonString.isNullOrEmpty()) {
                e("jsonString is null")
                return ""
            }
            val INDENT = "    "
            val prettyJsonSb = StringBuffer()
            var indentDepth = 0
            var targetString: String?
            for (i in jsonString.indices) {
                targetString = jsonString.substring(i, i + 1)
                when (targetString) {
                    "{", "[" -> {
                        prettyJsonSb.append(targetString).append("\n")
                        indentDepth++
                        for (j in 0 until indentDepth) {
                            prettyJsonSb.append(INDENT)
                        }
                    }

                    "}", "]" -> {
                        prettyJsonSb.append("\n")
                        indentDepth--
                        for (j in 0 until indentDepth) {
                            prettyJsonSb.append(INDENT)
                        }
                        prettyJsonSb.append(targetString)
                    }

                    "," -> {
                        prettyJsonSb.append(targetString)
                        prettyJsonSb.append("\n")
                        for (j in 0 until indentDepth) {
                            prettyJsonSb.append(INDENT)
                        }
                    }

                    else -> {
                        prettyJsonSb.append(targetString)
                    }
                }
            }
            return prettyJsonSb.toString()
        }

        /**
         * 메서드명 포함한 로그 문자열로 리턴
         */
        private fun getWithMethodName(log: String): String {
            //로그 찍을때 메서드명도 찍기
            return try {
                val ste = Thread.currentThread().stackTrace[5]
                val sb = StringBuffer()
                sb.append("[")
                sb.append(ste.fileName.replace(".java", ""))
                sb.append("::")
                sb.append(ste.methodName)
                sb.append("]   ")
                sb.append(log)
                sb.toString()
            } catch (e: Throwable) {
                e.printStackTrace()
                log
            }
        }

        @Synchronized
        private fun dLong(theMsg: String, logType: Int) {
            var theMsg = theMsg
            try {
                // String to be logged is longer than the max...
                if (theMsg.length > MAX_INDEX) {
                    var theSubstring = theMsg.substring(0, MAX_INDEX)
                    val theIndex = MAX_INDEX

                    //로그 찍을때 메서드명도 찍기
                    theSubstring = getWithMethodName(theSubstring)
                    when (logType) {
                        LOG_DEBUG -> Log.d(TAG, theSubstring)
                        LOG_VERBOSE -> Log.v(TAG, theSubstring)
                        LOG_INFO -> Log.i(TAG, theSubstring)
                        LOG_WARN -> Log.w(TAG, theSubstring)
                        LOG_ERROR -> Log.e(TAG, theSubstring)
                    }

                    // Recursively log the remainder.
                    dLong(theMsg.substring(theIndex), logType)
                } else {
                    //로그 찍을때 메서드명도 찍기
                    theMsg = getWithMethodName(theMsg)
                    when (logType) {
                        LOG_DEBUG -> Log.d(TAG, theMsg)
                        LOG_VERBOSE -> Log.v(TAG, theMsg)
                        LOG_INFO -> Log.i(TAG, theMsg)
                        LOG_WARN -> Log.w(TAG, theMsg)
                        LOG_ERROR -> Log.e(TAG, theMsg)
                    }
                }
            } catch (e: Exception) {
                e("dLong exception : ${e.message}")
                if (isLogPrint) e.printStackTrace()
            }
        }
    }
}