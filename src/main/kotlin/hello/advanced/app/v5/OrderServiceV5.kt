package hello.advanced.app.v5

import hello.advanced.trace.callback.TraceTemplate
import org.springframework.stereotype.Service

@Service
class OrderServiceV5(
    private val orderRepository: OrderRepositoryV5,
    private val traceTemplate: TraceTemplate
) {

    fun orderItem(itemId: String) {
        traceTemplate.execute(TRACE_MESSAGE) {
            orderRepository.save(itemId)
        }
    }

    companion object {
        private const val TRACE_MESSAGE = "OrderService.request()"
    }
}
