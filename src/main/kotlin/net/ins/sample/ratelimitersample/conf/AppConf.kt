package net.ins.sample.ratelimitersample.conf

import io.github.resilience4j.ratelimiter.RateLimiterConfig
import io.github.resilience4j.ratelimiter.RateLimiterRegistry
import net.ins.sample.ratelimitersample.conf.interceptor.RateLimiterConfigProps
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration
import java.time.temporal.ChronoUnit

@Configuration
@EnableConfigurationProperties(RateLimiterConfigProps::class)
class AppConf {

    @Bean
    fun restTemplate() = RestTemplateBuilder()
            .rootUri("http://localhost:8080")
            .build()

    @Bean
    fun rateLimiter(): RateLimiterRegistry {
        val oneRpsConf = RateLimiterConfig.custom()
                .limitForPeriod(1)
                .timeoutDuration(Duration.of(5, ChronoUnit.SECONDS))
                .limitRefreshPeriod(Duration.of(1, ChronoUnit.SECONDS))
                .build()

        val twoRpsConf = RateLimiterConfig.custom()
                .limitForPeriod(2)
                .timeoutDuration(Duration.of(5, ChronoUnit.SECONDS))
                .limitRefreshPeriod(Duration.of(1, ChronoUnit.SECONDS))
                .build()

        val registry = RateLimiterRegistry.of(twoRpsConf)

        registry.rateLimiter("1rps", oneRpsConf)
        registry.rateLimiter("2rps", twoRpsConf)

        return registry
    }
}
