package hello.advanced.trace.threadlocal

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.Thread.sleep

class ThreadLocalService(private val nameStore: ThreadLocal<String> = ThreadLocal()) {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    fun logic(name: String): String {
        log.info("저장 name={} -> nameStore={}", name, nameStore.get())
        nameStore.set(name)
        sleep(1000)
        log.info("조회 nameStore={}", nameStore.get())
        return nameStore.get()
    }
}