package net.ins.sample.ratelimitersample.conf.interceptor

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.temporal.ChronoUnit

@ConfigurationProperties("msa.rate-limiter")
@ConstructorBinding
data class RateLimiterConfigProps(
        val limits: Map<String, RateLimiterConfProp>
)

@ConstructorBinding
data class RateLimiterConfProp(
        val requests: Int,
        val per: ChronoUnit = ChronoUnit.SECONDS,
        val timeout: Long = 5L,
        val timeoutIn: ChronoUnit = ChronoUnit.SECONDS
)
