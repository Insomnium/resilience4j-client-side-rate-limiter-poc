package net.ins.sample.ratelimitersample.conf

import io.github.resilience4j.ratelimiter.RateLimiter
import io.github.resilience4j.ratelimiter.RateLimiterConfig
import io.github.resilience4j.ratelimiter.RateLimiterRegistry
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration
import java.time.temporal.ChronoUnit

@Configuration
class AppConf {

    @Bean
    fun restTemplate() = RestTemplateBuilder()
            .rootUri("http://localhost:8080")
            .build()

    @Bean
    fun rateLimiter(): RateLimiter {
        val rpsConf = RateLimiterConfig.custom()
                .limitForPeriod(2)
                .timeoutDuration(Duration.of(5, ChronoUnit.SECONDS))
                .limitRefreshPeriod(Duration.of(1, ChronoUnit.SECONDS))
                .build()
        val registry = RateLimiterRegistry.of(rpsConf)

        return registry.rateLimiter("1rps", rpsConf)
    }
}