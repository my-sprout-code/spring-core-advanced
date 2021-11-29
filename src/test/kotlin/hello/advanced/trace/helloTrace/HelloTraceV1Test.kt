package hello.advanced.trace.helloTrace

import org.junit.jupiter.api.Test

internal class HelloTraceV1Test {

    @Test
    fun beginEnd() {
        val trace = HelloTraceV1()
        val status = trace.begin("hello")
        trace.end(status)
    }

    @Test
    fun beginException() {
        val trace = HelloTraceV1()
        val status = trace.begin("hello")
        trace.exception(status, IllegalArgumentException())
    }
}
