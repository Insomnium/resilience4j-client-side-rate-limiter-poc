package net.ins.sample.ratelimitersample.domain

import java.time.LocalDateTime
import java.util.*

data class DataSnapshot(
        val id: UUID,
        val timestamp: LocalDateTime,
        val snapshot: String
)