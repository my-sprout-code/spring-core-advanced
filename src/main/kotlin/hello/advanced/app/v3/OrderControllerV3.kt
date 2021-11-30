package hello.advanced.app.v3

import hello.advanced.trace.logtrace.LogTrace
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderControllerV3(
    private val orderService: OrderServiceV3,
    private val logTrace: LogTrace
) {

    @GetMapping("/v3/request")
    fun request(itemId: String): String {
        val status = logTrace.begin("OrderController.request()")
        try {
            orderService.orderItem(itemId)
            logTrace.end(status)
            return "ok"
        } catch (e: Exception) {
            logTrace.exception(status, e)
            throw e
        }
    }
}
