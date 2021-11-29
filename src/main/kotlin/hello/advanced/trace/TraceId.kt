package hello.advanced.trace

import java.util.UUID

class TraceId(val id: String = createId(), val level: Int = 0) {

    fun isFirstLevel(): Boolean = level == 0

    fun createNextId(): TraceId = TraceId(id, level + 1)

    fun createPreviousId(): TraceId = TraceId(id, level - 1)

    companion object {
        fun createId(): String = UUID.randomUUID().toString().substring(0, 8)
    }
}

