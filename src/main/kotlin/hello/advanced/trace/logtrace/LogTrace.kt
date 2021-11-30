package hello.advanced.trace.logtrace

import hello.advanced.trace.TraceStatus

interface LogTrace {

    fun begin(message: String): TraceStatus

    fun end(traceStatus: TraceStatus)

    fun exception(traceStatus: TraceStatus, e: Exception)
}