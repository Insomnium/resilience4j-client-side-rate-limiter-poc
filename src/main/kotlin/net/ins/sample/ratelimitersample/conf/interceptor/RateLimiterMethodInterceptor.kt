package net.ins.sample.ratelimitersample.conf.interceptor

import io.github.resilience4j.ratelimiter.RateLimiter
import io.github.resilience4j.ratelimiter.RateLimiterConfig
import io.github.resilience4j.ratelimiter.RateLimiterRegistry
import net.ins.sample.ratelimitersample.conf.meta.RateLimited
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.function.Function

@Aspect
@Component
class RateLimiterMethodInterceptor {

    private val rateLimiterRegistry = RateLimiterRegistry.ofDefaults()
    private val limiterAnnotationsByMethod = mutableMapOf<String, RateLimited>()
    private val limitersByMethod = mutableMapOf<String, RateLimiter>()

    @Around("@annotation(net.ins.sample.ratelimitersample.conf.meta.RateLimited)")
    fun limitRate(proceedingJoinPoint: ProceedingJoinPoint): Any =
            with(resolveRateLimiter(proceedingJoinPoint)) {
                executeSupplier {
                    proceedingJoinPoint.proceed()
                }
            }

    private fun resolveRateLimiter(proceedingJoinPoint: ProceedingJoinPoint): RateLimiter {
        val methodSignature = proceedingJoinPoint.signature as MethodSignature
        return limitersByMethod.computeIfAbsent(methodSignature.toString(), Function {
            val limiter = resolveRateLimiterAnnotation(proceedingJoinPoint)
            val rateLimiterConfig = RateLimiterConfig.custom()
                    .limitForPeriod(limiter.value)
                    .limitRefreshPeriod(Duration.of(1, limiter.per))
                    .timeoutDuration(Duration.of(limiter.timeout, limiter.timeoutIn))
                    .build()
            return@Function rateLimiterRegistry.rateLimiter(methodSignature.toString(), rateLimiterConfig)
        })
    }

    private fun resolveRateLimiterAnnotation(proceedingJoinPoint: ProceedingJoinPoint): RateLimited {
        val methodSignature = proceedingJoinPoint.signature as MethodSignature
        return limiterAnnotationsByMethod.computeIfAbsent(methodSignature.toString(), Function {
            proceedingJoinPoint
                    .target
                    .javaClass
                    .getMethod(methodSignature.name, *methodSignature.parameterTypes)
                    .getAnnotation(RateLimited::class.java)
        })
    }
}