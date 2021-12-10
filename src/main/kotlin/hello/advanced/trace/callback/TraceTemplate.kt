package hello.advanced.trace.callback

import hello.advanced.trace.logtrace.LogTrace
import org.springframework.stereotype.Component

@Component
class TraceTemplate(private val trace: LogTrace) {

    fun <T> execute(message: String, traceCallBack: TraceCallBack<T>): T {
        val traceStatus = trace.begin(message)
        try {
            val result = traceCallBack.call()
            trace.end(traceStatus)
            return result
        } catch (e: Exception) {
            trace.exception(traceStatus, e)
            throw e
        }
    }
}
