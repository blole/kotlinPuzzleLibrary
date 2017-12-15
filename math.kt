package kotlinPuzzleLibrary

fun <I : Iterable<Int>> I.prod() = fold(1) { acc, i -> acc * i }
