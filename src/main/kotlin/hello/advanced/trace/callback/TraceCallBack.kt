package hello.advanced.trace.callback

fun interface TraceCallBack<T> {
    fun call(): T
}
