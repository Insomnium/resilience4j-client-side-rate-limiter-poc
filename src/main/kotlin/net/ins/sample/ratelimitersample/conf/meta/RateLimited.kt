package net.ins.sample.ratelimitersample.conf.meta

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RateLimited(
        val limiterName: String
)