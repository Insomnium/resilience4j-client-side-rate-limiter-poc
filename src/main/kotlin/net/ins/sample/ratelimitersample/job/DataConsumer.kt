package net.ins.sample.ratelimitersample.job

import io.github.resilience4j.ratelimiter.RateLimiter
import io.github.resilience4j.ratelimiter.RateLimiterRegistry
import mu.KotlinLogging
import net.ins.sample.ratelimitersample.domain.DataSnapshot
import net.ins.sample.ratelimitersample.util.logger
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class DataConsumer(
        private val restTemplate: RestTemplate,
        private val rateLimiterRegistry: RateLimiterRegistry
) {

    private val logger = KotlinLogging.logger()

    @EventListener
    fun onApplicationRun(applicationStartedEvent: ApplicationStartedEvent) {
        for (i in 0..99) {
            val body = rateLimiterRegistry.rateLimiter("2rps").executeSupplier {
                restTemplate.getForEntity("/data", DataSnapshot::class.java)
            }.body

            logger.info("$i: ${body}")
        }
    }
}