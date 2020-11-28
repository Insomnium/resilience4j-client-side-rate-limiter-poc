package net.ins.sample.ratelimitersample.job

import mu.KotlinLogging
import net.ins.sample.ratelimitersample.service.DataConsumerService
import net.ins.sample.ratelimitersample.util.logger
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class DataConsumer(
        private val consumerService: DataConsumerService
) {

    private val logger = KotlinLogging.logger()

    @EventListener
    fun onApplicationRun(applicationStartedEvent: ApplicationStartedEvent) {
        for (i in 0..99) {
            logger.info("$i: ${consumerService.consumeData()}")
        }
    }
}