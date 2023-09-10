package com.dezdeqness.tmdb.data.service

import android.content.Context
import com.dezdeqness.tmdb.core.utils.NetworkHelper
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class FirstPageCacheInterceptor(
    private val networkHelper: NetworkHelper,
    private val context: Context,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkHelper.isNetworkAvailable(context) && isFirstPageUrl(chain.request().url.toUrl().query)) {
            val request =
                chain.request().newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            return chain.proceed(request).newBuilder().build()
        }

        val original = chain.request()
        val request = original.newBuilder().method(original.method, original.body).build()
        return chain.proceed(request)
    }

    private fun isFirstPageUrl(url: String) = url.contains(FIRST_PAGE_QUERY)

    companion object {
        private const val FIRST_PAGE_QUERY = "page=1"
    }

}