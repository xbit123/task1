package ru.skillbranch.kotlinexample.extensions

val list = listOf(1,2,3)

inline fun <T> List<T>.dropLastUntil(predicate: (T) -> Boolean): List<T> {
    val index = this.indexOfFirst(predicate)
    return if  (index == -1) this
    else this.take(index)
}