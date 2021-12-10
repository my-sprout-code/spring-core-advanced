package hello.advanced.app.v5

import hello.advanced.trace.callback.TraceTemplate
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryV5(private val traceTemplate: TraceTemplate) {

    fun save(itemId: String) {
        traceTemplate.execute(TRACE_MESSAGE) {
            if (itemId == EXCEPTION_CONDITION) {
                throw IllegalStateException(EXCEPTION_MESSAGE)
            }
            sleep(SLEEP_TIME)
        }
    }

    private fun sleep(millis: Long) {
        try {
            Thread.sleep(millis)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val TRACE_MESSAGE = "OrderRepository.request()"
        private const val EXCEPTION_CONDITION = "ex"
        private const val EXCEPTION_MESSAGE = "예외 발생!"
        private const val SLEEP_TIME = 1000L
    }
}
