package net.ins.sample.ratelimitersample.conf.meta

import java.time.temporal.ChronoUnit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RateLimited(
        val value: Int,
        val per: ChronoUnit = ChronoUnit.SECONDS,
        val timeout: Long = 1L,
        val timeoutIn: ChronoUnit = ChronoUnit.SECONDS
)