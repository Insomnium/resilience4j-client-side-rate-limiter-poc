package net.ins.sample.ratelimitersample.conf.interceptor

import io.github.resilience4j.ratelimiter.RateLimiterRegistry
import net.ins.sample.ratelimitersample.conf.meta.RateLimited
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import java.util.function.Function

@Aspect
@Component
class RateLimiterMethodInterceptor(
        private val rateLimiterRegistry: RateLimiterRegistry
) {

    private val limitersByMethod = mutableMapOf<String, String>()

    @Around("@annotation(net.ins.sample.ratelimitersample.conf.meta.RateLimited)")
    fun limitRate(proceedingJoinPoint: ProceedingJoinPoint): Any {
        val limiterName = resolveRateLimiterName(proceedingJoinPoint)
        return rateLimiterRegistry.rateLimiter(limiterName).executeSupplier {
            proceedingJoinPoint.proceed()
        }
    }

    private fun resolveRateLimiterName(proceedingJoinPoint: ProceedingJoinPoint): String {
        val methodSignature = proceedingJoinPoint.signature as MethodSignature
        val key = methodSignature.toString()
        return limitersByMethod.computeIfAbsent(key, Function {
            return@Function proceedingJoinPoint
                    .target
                    .javaClass
                    .getMethod(methodSignature.name, *methodSignature.parameterTypes)
                    .getAnnotation(RateLimited::class.java).limiterName
        })
    }
}