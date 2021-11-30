package hello.advanced.trace.logtrace

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class FieldLogTraceTest {

    lateinit var trace: FieldLogTrace

    @BeforeEach
    internal fun setUp() {
        trace = FieldLogTrace()
    }

    @Test
    fun begin_end_level2() {
        val traceStatus1 = trace.begin("hello1")
        val traceStatus2 = trace.begin("hello2")

        trace.end(traceStatus2)
        trace.end(traceStatus1)
    }

    @Test
    fun begin_exception_level2() {
        val traceStatus1 = trace.begin("hello1")
        val traceStatus2 = trace.begin("hello2")

        trace.exception(traceStatus2, IllegalArgumentException())
        trace.exception(traceStatus1, IllegalArgumentException())
    }
}