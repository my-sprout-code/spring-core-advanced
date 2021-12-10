package hello.advanced.trace.logtrace

import hello.advanced.trace.TraceId
import hello.advanced.trace.TraceStatus
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ThreadLocalLogTrace(private val threadLocal: ThreadLocal<TraceId> = ThreadLocal()) : LogTrace {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    override fun begin(message: String): TraceStatus {
        syncTraceId()
        val traceId = threadLocal.get()
        val startTimeMs = System.currentTimeMillis()
        log.info("[{}] {}{}", traceId.id, addSpace(START_PREFIX, traceId.level), message)
        return TraceStatus(traceId, startTimeMs, message)
    }

    private fun syncTraceId() {
        val thraceId = threadLocal.get()
        if (thraceId == null) {
            threadLocal.set(TraceId())
        } else {
            threadLocal.set(thraceId.createNextId())
        }
    }

    override fun end(traceStatus: TraceStatus): Unit = complete(traceStatus, null)

    override fun exception(traceStatus: TraceStatus, e: Exception): Unit = complete(traceStatus, e)

    private fun complete(status: TraceStatus, e: java.lang.Exception?) {
        val stopTimeMs = System.currentTimeMillis()
        val resultTimeMs = stopTimeMs - status.startTimeMs
        val traceId = status.traceId
        if (e == null) {
            log.info(
                "[{}] {}{} time={}ms", traceId.id,
                addSpace(COMPLETE_PREFIX, traceId.level), status.message,
                resultTimeMs
            )
        } else {
            log.info(
                "[{}] {}{} time={}ms ex={}", traceId.id,
                addSpace(EX_PREFIX, traceId.level), status.message, resultTimeMs,
                e.toString()
            )
        }
        releaseTraceId()
    }

    private fun releaseTraceId() {
        val traceId = threadLocal.get()
        if (traceId.isFirstLevel()) {
            threadLocal.remove()
        } else {
            threadLocal.set(traceId.createPreviousId())
        }
    }

    companion object {

        private const val START_PREFIX = "-->"
        private const val COMPLETE_PREFIX = "<--"
        private const val EX_PREFIX = "<X-"

        private fun addSpace(prefix: String, level: Int): String {
            val sb = StringBuilder()
            for (i in (0 until level)) {
                sb.append(if (i == level - 1) "|$prefix" else "|   ")
            }
            return sb.toString()
        }
    }
}