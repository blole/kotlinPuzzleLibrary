package kotlinPuzzleLibrary

fun <I : Iterable<Int>> I.prod() = reduce(Int::times)
