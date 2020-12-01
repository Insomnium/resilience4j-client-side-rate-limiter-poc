package net.ins.sample.ratelimitersample.conf

import net.ins.sample.ratelimitersample.conf.interceptor.RateLimiterConfigProps
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(RateLimiterConfigProps::class)
class AppConf {

    @Bean
    fun restTemplate() = RestTemplateBuilder()
            .rootUri("http://localhost:8080")
            .build()
}
