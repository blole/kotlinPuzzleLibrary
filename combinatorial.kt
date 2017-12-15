package kotlinPuzzleLibrary

import kotlin.coroutines.experimental.buildSequence

fun <A,B,AC: Iterable<A>> AC.product(bs: Iterable<B>): Sequence<Pair<A,B>> = buildSequence {
    for (a: A in this@product) {
        for (b: B in bs) {
            yield(Pair(a, b))
        }
    }
}
fun <A,AC: Iterable<A>> AC.productSelf(): Sequence<Pair<A,A>> = this.product(this)

fun <A,AC: Iterable<A>> AC.productSelfExcluding(): Sequence<Pair<A,A>> = buildSequence {
    for ((i, a) in this@productSelfExcluding.withIndex()) {
        for ((j, b) in this@productSelfExcluding.withIndex()) {
            if (i != j)
                yield(Pair(a, b))
        }
    }
}

fun <A,AC: List<A>> AC.combinations(r: Int): Sequence<List<A>> = buildSequence {
    val n: Int = this@combinations.size
    if (r > n)
        return@buildSequence
    val indices: MutableList<Int> = (0..(r-1)).toMutableList()
    yield(this@combinations.slice(indices))

    outer@
    while (true) {
        for (i: Int in (0..(r-1)).reversed()) {
            if (indices[i] != i + n - r) {
                indices[i] += 1
                for (j: Int in (i+1)..(r-1))
                    indices[j] = indices[j-1] + 1
                yield(this@combinations.slice(indices))
                continue@outer
            }
        }
        break
    }
}
fun <A,AC: Iterable<A>> AC.combinations(r: Int): Sequence<List<A>> = toList().combinations(r)


fun <A,AC: List<A>> AC.permutations(r: Int? = null): Sequence<List<A>> = buildSequence {
    val n: Int = this@permutations.size
    val r = r ?: n
    if (r > n)
        return@buildSequence
    var indices: MutableList<Int> = (0..(n-1)).toMutableList()
    val cycles: MutableList<Int> = n.downTo(n-r+1).toMutableList()
    yield(this@permutations.slice(indices.subList(0,r)))

    outer@
    while (n > 0) {
        for (i: Int in (0..(r-1)).reversed()) {
            cycles[i]--
            if (cycles[i] == 0) {
                indices = (indices.subList(0,i) + indices.subList(i+1,n) + indices.subList(i,i+1)).toMutableList()
                cycles[i] = n-i
            }
            else {
                val j = cycles[i]
                val tmp = indices[n-j]
                indices[n-j] = indices[i]
                indices[i] = tmp
                yield(this@permutations.slice(indices.subList(0,r)))
                continue@outer
            }
        }
        break
    }
}
fun <A,AC: Iterable<A>> AC.permutations(r: Int? = null): Sequence<List<A>> = toList().permutations(r)





fun main(args: Array<String>) {
    require("ABCD".toList().combinations(2).toList().map { it.joinToString("") }.toString() == "[AB, AC, AD, BC, BD, CD]")
    require((0..3).combinations(3).toList().map { it.joinToString("") }.toString() == "[012, 013, 023, 123]")
    require(("ABCD".toList().permutations(2)).toList().map { it.joinToString("") }.toString() == "[AB, AC, AD, BA, BC, BD, CA, CB, CD, DA, DB, DC]")
    require((0..2).permutations().toList().map { it.joinToString("") }.toString() == "[012, 021, 102, 120, 201, 210]")
}
