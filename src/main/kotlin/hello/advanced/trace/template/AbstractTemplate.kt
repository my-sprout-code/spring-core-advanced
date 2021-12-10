package hello.advanced.trace.template

import hello.advanced.trace.logtrace.LogTrace

abstract class AbstractTemplate<T>(private val trace: LogTrace) {

    fun execute(message: String): T {
        val traceStatus = trace.begin(message)
        try {
            val result: T = call()
            trace.end(traceStatus)
            return result
        } catch (e: Exception) {
            trace.exception(traceStatus, e)
            throw e
        }
    }

    abstract fun call(): T
}