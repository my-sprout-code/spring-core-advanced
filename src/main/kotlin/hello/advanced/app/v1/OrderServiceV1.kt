package hello.advanced.app.v1

import hello.advanced.trace.helloTrace.HelloTraceV1
import org.springframework.stereotype.Service

@Service
class OrderServiceV1(
    private val orderRepository: OrderRepositoryV1,
    private val trace: HelloTraceV1
) {

    fun orderItem(itemId: String) {
        val status = trace.begin("OrderService.request()")
        try {
            orderRepository.save(itemId)
            trace.end(status)
        } catch (e: Exception) {
            trace.exception(status, e)
            throw e
        }
    }
}
