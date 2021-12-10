package hello.advanced.app.v4

import hello.advanced.trace.logtrace.LogTrace
import hello.advanced.trace.template.AbstractTemplate
import org.springframework.stereotype.Service

@Service
class OrderServiceV4(
    private val orderRepository: OrderRepositoryV4,
    private val trace: LogTrace
) {

    fun orderItem(itemId: String) {
        val template = object : AbstractTemplate<Unit>(trace) {
            override fun call() = orderRepository.save(itemId)
        }
        template.execute("OrderService.request()")
    }
}
