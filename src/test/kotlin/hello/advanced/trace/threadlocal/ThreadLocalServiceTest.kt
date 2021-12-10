package hello.advanced.trace.threadlocal

import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.Thread.sleep

class ThreadLocalServiceTest() {

    private val log: Logger = LoggerFactory.getLogger(javaClass)
    private val service: ThreadLocalService = ThreadLocalService()

    @Test
    fun threadLocal_no_race_condition() {
        log.info("main start!")
        val threadA = Thread { service.logic("hello1") }
        threadA.name = "thread-A"

        val threadB = Thread { service.logic("hello2") }
        threadB.name = "thread-B"

        threadA.start()
        sleep(2000)
        threadB.start()
        sleep(3000)
    }

    @Test
    fun threadLocal_race_condition() {
        log.info("main start!")
        val threadA = Thread { service.logic("userA") }
        threadA.name = "thread-A"

        val threadB = Thread { service.logic("userB") }
        threadB.name = "thread-B"

        threadA.start()
        sleep(100)
        threadB.start()
        sleep(3000)
    }
}