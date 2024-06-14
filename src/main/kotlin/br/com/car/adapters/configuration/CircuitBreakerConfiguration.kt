package br.com.car.adapters.configuration

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration


@Configuration
class CircuitBreakerConfiguration {

    fun getConfiguration() = CircuitBreakerConfig.custom()
        .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
        .slidingWindowSize(10)
        .slowCallRateThreshold(70.0f)
        .failureRateThreshold(70.0f)
        .waitDurationInOpenState(Duration.ofSeconds(5))
        .slowCallDurationThreshold(Duration.ofSeconds(3))
        .permittedNumberOfCallsInHalfOpenState(3)
        .build()

    @Bean
    fun getCircuitBreaker() = CircuitBreakerRegistry
        .of(getConfiguration())
        .circuitBreaker("circuit-breaker-car-service")
}