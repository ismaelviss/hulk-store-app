package com.ismaelviss.hulkstore.services.login

import com.google.common.truth.Truth
import com.ismaelviss.hulkstore.configurations.OkHttpClientConfig
import com.ismaelviss.hulkstore.retrofit.IHulkStoreServices
import com.ismaelviss.hulkstore.services.login.model.UserRequest
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.Assert.*
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

class LoginServiceTest {

    private lateinit var services: IHulkStoreServices
    private lateinit var server: MockWebServer
    private lateinit var loginService: LoginService

    @Before
    fun setUp() {
        server = MockWebServer()
        services = provideIHulkStoreServices(server)
        loginService = LoginService(services)
    }

    @Test
    fun test_services_login() {
        runBlocking {
            server.enqueue(enqueueMockResponse("login.json"))

            val responseBody = services.login(UserRequest("esalvati", "3s4lv4t1")).execute().body()
            val request = server.takeRequest()

            assertThat(responseBody).isNotNull()
            assertThat(responseBody?.jwt).isNotEmpty()
            assertThat(request.path).isEqualTo("/auth/login")
        }
    }

    @Test
    fun test_interface_service_login() {
        runBlocking {
            val login = LoginService(services)
            server.enqueue(enqueueMockResponse("login.json"))

            val result = login.login(UserRequest("esalvati", "3s4lv4t1"))

            assertThat(result).isNotNull()
            assertTrue(result.isSuccess)
            assertThat(result.getOrNull()).isNotNull()
            assertThat(result.getOrNull()?.jwt).isNotNull()
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    companion object {
        fun provideIHulkStoreServices(server: MockWebServer) : IHulkStoreServices {
            val builder = OkHttpClient().newBuilder()
                .readTimeout(OkHttpClientConfig.TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(OkHttpClientConfig.TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(OkHttpClientConfig.TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)


                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                builder.addInterceptor(interceptor)


            val okHttpClient = builder.build()

            val service = Retrofit.Builder()
                .baseUrl(server.url(""))
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return service.create(IHulkStoreServices::class.java)
        }

        fun enqueueMockResponse(fileName: String) : MockResponse {
            val inputStream = LoginServiceTest::class.java.classLoader?.getResourceAsStream(fileName)
            val source = inputStream?.source()?.buffer()
            val mockResponse = MockResponse()
            mockResponse.setBody(source?.readString(Charsets.UTF_8) ?: "")
            return mockResponse
        }

        fun responseString(fileName: String) : String {
            val inputStream = LoginServiceTest::class.java.classLoader?.getResourceAsStream(fileName)
            val source = inputStream?.source()?.buffer()
            return source?.readString(Charsets.UTF_8) ?: ""
        }
    }
}