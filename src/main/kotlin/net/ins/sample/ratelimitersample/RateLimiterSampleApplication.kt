package net.ins.sample.ratelimitersample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RateLimiterSampleApplication

fun main(args: Array<String>) {
    runApplication<RateLimiterSampleApplication>(*args)
}
