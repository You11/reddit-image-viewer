//package ru.you11.redditimageviewer.other
//
//import android.content.Context
//import com.bumptech.glide.Glide
//import com.bumptech.glide.GlideBuilder
//import com.bumptech.glide.Registry
//import com.bumptech.glide.annotation.GlideModule
//import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
//import com.bumptech.glide.module.AppGlideModule
//import okhttp3.*
//import java.util.concurrent.TimeUnit
//import com.bumptech.glide.load.model.GlideUrl
//import java.io.InputStream
//
//
//@GlideModule
//class OnlyImageAppGlideModule: AppGlideModule() {
//
//    override fun applyOptions(context: Context, builder: GlideBuilder) {
//        super.applyOptions(context, builder)
//    }
//
//    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
//
//        val client = OkHttpClient.Builder()
//            .readTimeout(Consts.timeoutLength, TimeUnit.SECONDS)
//            .connectTimeout(Consts.timeoutLength, TimeUnit.SECONDS)
//            .addInterceptor { chain ->
//                val response = chain.proceed(chain.request())
//                if (response.isRedirect) response
//
//                val responseBody = response.body()
//
//                if (responseBody != null) {
//                    val isImage = responseBody.contentType()?.type()?.equals("image")
//                    if (isImage != null && isImage) {
//                        response
//                    } else return415Response(chain)
//                } else return415Response(chain)
//            }
//            .build()
//
//        val factory = OkHttpUrlLoader.Factory(client)
//
//        registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
//    }
//
//    private fun return415Response(chain: Interceptor.Chain): Response {
//        return Response.Builder()
//            .code(415)
//            .protocol(Protocol.HTTP_1_1)
//            .message("Media type not supported")
//            .body(ResponseBody.create(MediaType.parse("text/html"), ""))
//            .request(chain.request())
//            .build()
//    }
//}