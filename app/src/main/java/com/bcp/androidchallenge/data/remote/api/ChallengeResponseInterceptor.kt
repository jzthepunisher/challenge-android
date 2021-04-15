package com.bcp.androidchallenge.data.remote.api

import android.content.Context
import okhttp3.*
import java.io.IOException

class CurrencyResponseInterceptor(private val context: Context)  : Interceptor {

    companion object {
        private val JSON_MEDIA_TYPE = MediaType.parse("application/json")
        private const val MOCK = "mock"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val header = request.header(MOCK)

        if (header != null) {
            val filename = request.url().pathSegments().last()
            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .message("")
                .code(200)
                .body(ResponseBody.create(JSON_MEDIA_TYPE, context.readFileFromAssets("mocks/$filename.json")))
                .build()
        }

        val isHandled = true
        if(isHandled){
            throw DoNothing("true",isHandled)
        }

        return chain.proceed(request.newBuilder().removeHeader(MOCK).build())

    }
}

fun Context.readFileFromAssets(filePath: String): String {
    return resources.assets.open(filePath).bufferedReader().use {
        it.readText()
    }
}

class DoNothing(text: String = "false", isHandled :Boolean = false): IOException(text)