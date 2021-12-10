package hello.advanced.app.v4

import hello.advanced.trace.logtrace.LogTrace
import hello.advanced.trace.template.AbstractTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderControllerV4(
    private val orderService: OrderServiceV4,
    private val logTrace: LogTrace
) {

    @GetMapping("/v4/request")
    fun request(itemId: String): String {
        val template = object : AbstractTemplate<String>(logTrace) {
            override fun call(): String {
                orderService.orderItem(itemId)
                return "ok"
            }
        }
        return template.execute("OrderController.request()")
    }
}
