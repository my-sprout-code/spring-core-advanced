package hello.advanced.trace.helloTrace

import org.junit.jupiter.api.Test

internal class HelloTraceV2Test {

    @Test
    fun beginEnd() {
        val trace = HelloTraceV2()
        val status = trace.begin("hello")
        val status2 = trace.beginSync(status.traceId, "hello2")
        trace.end(status2)
        trace.end(status)
    }

    @Test
    fun beginException() {
        val trace = HelloTraceV2()
        val status = trace.begin("hello")
        val status2 = trace.beginSync(status.traceId, "hello2")
        trace.exception(status2, IllegalArgumentException())
        trace.exception(status, IllegalArgumentException())
    }
}
