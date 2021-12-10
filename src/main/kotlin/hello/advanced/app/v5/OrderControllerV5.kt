package hello.advanced.app.v5

import hello.advanced.trace.callback.TraceTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderControllerV5(
    private val orderService: OrderServiceV5,
    private val traceTemplate: TraceTemplate
) {

    @GetMapping("/v5/request")
    fun request(itemId: String): String {
        return traceTemplate.execute(TRACE_MESSAGE) {
            orderService.orderItem(itemId)
            SUCCESS_MESSAGE
        }
    }

    companion object {
        private const val TRACE_MESSAGE = "OrderController.request()"
        private const val SUCCESS_MESSAGE = "ok"
    }
}
