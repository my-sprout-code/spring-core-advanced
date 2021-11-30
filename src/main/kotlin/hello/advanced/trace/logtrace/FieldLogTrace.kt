package hello.advanced.trace.logtrace

import hello.advanced.trace.TraceId
import hello.advanced.trace.TraceStatus
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class FieldLogTrace(private var traceIdHolder: TraceId? = null) : LogTrace {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    override fun begin(message: String): TraceStatus {
        syncTraceId()
        val traceId = traceIdHolder!!
        val startTimeMs = System.currentTimeMillis()
        log.info("[{}] {}{}", traceId.id, addSpace(START_PREFIX, traceId.level), message)
        return TraceStatus(traceId, startTimeMs, message)
    }

    private fun syncTraceId() {
        if (traceIdHolder == null) {
            traceIdHolder = TraceId()
        } else {
            traceIdHolder = traceIdHolder!!.createNextId()
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
        if (traceIdHolder!!.isFirstLevel()) {
            traceIdHolder = null
        } else {
            traceIdHolder = traceIdHolder!!.createPreviousId()
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