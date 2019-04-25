package kotlinPuzzleLibrary

fun <K, V, R> Map<K,V>.mapIfPresent(key: K, transform: (value: V) -> R): R? {
    val value = get(key)
    return if (value != null) {
        transform(value)
    } else {
        null
    }
}
