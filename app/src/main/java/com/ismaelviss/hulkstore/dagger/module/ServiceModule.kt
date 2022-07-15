package com.ismaelviss.hulkstore.dagger.module

import com.ismaelviss.hulkstore.BuildConfig
import com.ismaelviss.hulkstore.configurations.OkHttpClientConfig.Companion.TIME_OUT
import com.ismaelviss.hulkstore.repositories.login.ILoginService
import com.ismaelviss.hulkstore.repositories.store.IProductService
import com.ismaelviss.hulkstore.retrofit.IHulkStoreServices
import com.ismaelviss.hulkstore.services.login.LoginService
import com.ismaelviss.hulkstore.services.product.ProductService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ServiceModule {
    companion object {
        private const val BASE_URL = "baseUrl"
    }

    @Singleton
    @Provides
    fun provideIFzSportsServices(@Named(BASE_URL) baseUrl: String) : IHulkStoreServices {
        val builder = OkHttpClient().newBuilder()
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val requestBuilder: Request.Builder = chain.request().newBuilder()
                    requestBuilder.header("x-api-key", "PMAK-62d0695320bd612181b0c904-075ce661d683bccb0fabf435b7bf3da89b")
                    return chain.proceed(requestBuilder.build())
                }

            })

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(interceptor)
        }

        val okHttpClient = builder.build()

        val service = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        return service.create(IHulkStoreServices::class.java)
    }

    @Provides
    fun provideILoginService(services: IHulkStoreServices) : ILoginService {
        return LoginService(services)
    }

    @Provides
    fun provideIProductService(services: IHulkStoreServices) : IProductService {
        return ProductService(services)
    }
}