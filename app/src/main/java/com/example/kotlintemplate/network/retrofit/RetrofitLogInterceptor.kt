package com.example.kotlintemplate.network.retrofit

import com.example.kotlintemplate.util.GLog
import okhttp3.*
import okhttp3.internal.http.HttpHeaders
import okio.Buffer
import java.io.EOFException
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.charset.UnsupportedCharsetException
import java.util.concurrent.TimeUnit

class RetrofitLogInterceptor constructor() : Interceptor {
    @Volatile
    var level = Level.NONE

    /**
     * Change the level at which this interceptor logs.
     */
    fun setLevel(level: Level): RetrofitLogInterceptor {
        this.level = level

        return this
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val level = level
        val request = chain.request()
        if (level == Level.NONE) {
            return chain.proceed(request)
        }
        val logBody = level == Level.BODY
        val logHeaders = logBody || level == Level.HEADERS
        val requestBody = request.body()
        val hasRequestBody = requestBody != null
        val connection = chain.connection()
        val protocol = if (connection != null) connection.protocol() else Protocol.HTTP_1_1
        var requestStartMessage = "Request --> " + request.method() + ' ' + request.url() + ' ' + protocol
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody!!.contentLength() + "-byte body)"
        }
        GLog.d(requestStartMessage)
        if (logHeaders) {
            if (hasRequestBody) {
                if (GLog.isRequestHeaderLog) {
                    // Request body headers are only present when installed as a network interceptor. Force
                    // them to be included (when available) so there values are known.
                    if (requestBody!!.contentType() != null) {
                        GLog.d("Content-Type: " + requestBody.contentType())
                    }
                    if (requestBody.contentLength() != -1L) {
                        GLog.d("Content-Length: " + requestBody.contentLength())
                    }
                }
            }
            if (GLog.isRequestHeaderLog) {
                val headers = request.headers()
                var i = 0
                val count = headers.size()
                while (i < count) {
                    val name = headers.name(i)
                    i++
                }
            }
            if (!logBody || !hasRequestBody) {
                GLog.d("Request -->  END " + request.method())
            } else if (bodyEncoded(request.headers())) {
                GLog.d("Request -->  END " + request.method() + " (encoded body omitted)")
            } else {
                val buffer = Buffer()
                requestBody!!.writeTo(buffer)
                var charset = UTF8
                val contentType = requestBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }

//                logger.log("");
                if (isPlaintext(buffer)) {
                    GLog.d("Request Body --> " + buffer.readString(charset))
                    GLog.d(
                        "Request --> END " + request.method()
                                + " (" + requestBody.contentLength() + "-byte body)"
                    )
                } else {
                    GLog.d(
                        "Request --> END " + request.method() + " (binary "
                                + requestBody.contentLength() + "-byte body omitted)"
                    )
                }
            }
        }
        val startNs = System.nanoTime()
        val response = chain.proceed(request)
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        val responseBody = response.body()
        val contentLength = responseBody!!.contentLength()
        val bodySize = if (contentLength != -1L) "$contentLength-byte" else "unknown-length"
        GLog.d(
            "Response <-- " + response.code() + ' ' + response.message() + ' '
                    + response.request().url() + " (" + tookMs + "ms" + (if (!logHeaders) (", "
                    + bodySize + " body") else "") + ')'
        )
        if (logHeaders) {
            if (GLog.isResponseHeaderLog) {
                val headers = response.headers()
                var i = 0
                val count = headers.size()
                while (i < count) {
                    GLog.d(headers.name(i) + ": " + headers.value(i))
                    i++
                }
            }
            if (!logBody || !HttpHeaders.hasBody(response)) {
                GLog.d("<-- END HTTP")
            } else if (bodyEncoded(response.headers())) {
                GLog.d("<-- END HTTP (encoded body omitted)")
            } else {
                val source = responseBody.source()
                source.request(Long.MAX_VALUE) // Buffer the entire body.
                val buffer = source.buffer()
                var charset = UTF8
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    charset = try {
                        contentType.charset(UTF8)
                    } catch (e: UnsupportedCharsetException) {
                        GLog.d("")
                        GLog.d("Couldn't decode the response body; charset is likely malformed.")
                        GLog.d("<-- END HTTP")
                        return response
                    }
                }
                if (!isPlaintext(buffer)) {
                    GLog.d("")
                    GLog.d("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)")
                    return response
                }
                var isResponseBodyShow = true

                // 응답값 보고 싶지 않은 API 추가
                if (response.request().url().toString().contains("messagelist") ||
                    response.request().url().toString().contains("retrieveowncouponhomeinfo")
                ) {
                    isResponseBodyShow = false
                }
                if (contentLength != 0L) {
                    if (isResponseBodyShow) {
                        GLog.d("========================== BODY START==========================")
                        GLog.d(GLog.getPretty(buffer.clone().readString(charset)))
                        GLog.d("========================== BODY END ==========================")
                    }
                }
                GLog.d("Response <-- END HTTP (" + buffer.size() + "-byte body)")
            }
        }
        return response
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"]
        return contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
    }

    enum class Level {
        /**
         * No logs.
         */
        NONE,

        /**
         * Logs request and response lines.
         *
         *
         * Example:
         * <pre>`--> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
        `</pre> *
         */
        BASIC,

        /**
         * Logs request and response lines and their respective headers.
         *
         *
         * Example:
         * <pre>`--> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
        `</pre> *
         */
        HEADERS,

        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         *
         *
         * Example:
         * <pre>`--> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
        `</pre> *
         */
        BODY
    }

    companion object {
        private val UTF8 = StandardCharsets.UTF_8

        /**
         * Returns true if the body in question probably contains human readable text. Uses a small sample
         * of code points to detect unicode control characters commonly used in binary file signatures.
         */
        fun isPlaintext(buffer: Buffer): Boolean {
            return try {
                val prefix = Buffer()
                val byteCount = if (buffer.size() < 64) buffer.size() else 64
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0..15) {
                    if (prefix.exhausted()) {
                        break
                    }
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false
                    }
                }
                true
            } catch (e: EOFException) {
                false // Truncated UTF-8 sequence.
            }
        }
    }
}