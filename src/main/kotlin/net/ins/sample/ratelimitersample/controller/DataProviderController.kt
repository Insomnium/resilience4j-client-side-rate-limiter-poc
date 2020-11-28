package net.ins.sample.ratelimitersample.controller

import net.ins.sample.ratelimitersample.domain.DataSnapshot
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.*

@RestController
@RequestMapping(path = ["/data"])
class DataProviderController {

    @GetMapping
    fun provideData() = DataSnapshot(UUID.randomUUID(), LocalDateTime.now(), UUID.randomUUID().toString())
}