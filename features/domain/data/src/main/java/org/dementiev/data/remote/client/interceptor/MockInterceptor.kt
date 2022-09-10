package org.dementiev.data.remote.client.interceptor

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.asResponseBody
import okhttp3.mockwebserver.MockResponse
import org.dementiev.data.remote.ResourcesReader

internal class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()
        val body =
            MockResponse().setBodyFromFile(
                when {
                    uri.endsWith("api/v1/sample") -> "assets/news.json"
                    else -> ""
                }

            ).getBody()
                ?.asResponseBody("application/json".toMediaTypeOrNull())

        return chain.proceed(chain.request())
            .newBuilder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .body(
                body
            )
            .addHeader("content-type", "application/json")
            .build()
    }
}

fun MockResponse.setBodyFromFile(filName: String): MockResponse {
    val text = ResourcesReader.readText(filName)
    setBody(text)
    return this
}