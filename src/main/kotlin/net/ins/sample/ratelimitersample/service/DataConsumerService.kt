package net.ins.sample.ratelimitersample.service

import net.ins.sample.ratelimitersample.conf.meta.RateLimited
import net.ins.sample.ratelimitersample.domain.DataSnapshot
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.temporal.ChronoUnit

@Service
class DataConsumerService(
        private val restTemplate: RestTemplate
) {

    @RateLimited(5, per = ChronoUnit.SECONDS)
    fun consumeData() = restTemplate.getForEntity("/data", DataSnapshot::class.java).body
}