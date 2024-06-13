package br.com.car.adapters.configuration

import br.com.car.adapters.http.CarHttpService
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.retrofit.CircuitBreakerCallAdapter
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Configuration
class CarHttpConfiguration(
    private val circuitBreaker: CircuitBreaker
) {

    private companion object {
        const val BASE_URL = "http://ec2-18-215-156-13.compute-1.amazonaws.com:8080"
    }

    fun okHttpClient() = OkHttpClient.Builder().build()

    fun buildRetrofit() = Retrofit.Builder()
        .addCallAdapterFactory(CircuitBreakerCallAdapter
            .of(circuitBreaker))
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient())
        .build()

    @Bean
    fun carHttpService(): CarHttpService = buildRetrofit()
        .create(CarHttpService::class.java)
}