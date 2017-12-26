package kotlinPuzzleLibrary

import java.math.BigInteger

// from https://codereview.stackexchange.com/a/139655
class PostponedPrimeIterator : AbstractIterator<Int>() {
    private lateinit var basePrimes: PostponedPrimeIterator
    private var basePrime = 0

    private val sieve = mutableMapOf<Int, Int>()
    private val initialPrimes = sequenceOf(2, 3, 5, 7).iterator()
    private var value = 0

    override fun computeNext() {
        if (initialPrimes.hasNext()) {
            value = initialPrimes.next()
            setNext(value)
        } else {
            value += 2

            if (value == 9) {
                basePrimes = PostponedPrimeIterator()
                basePrimes.next()
                basePrime = basePrimes.next()
                assert(value == basePrime * basePrime)
            }

            while (value > 0) {
                val factor = sieve.remove(value) ?:
                        if (value == basePrime * basePrime) {
                            basePrime.apply {
                                basePrime = basePrimes.next()
                            }
                        } else {
                            assert(value < basePrime * basePrime)
                            setNext(value)
                            break
                        }
                var j = value + 2 * factor
                while (j in sieve) {
                    j += 2 * factor
                }
                sieve[j] = factor
                value += 2
            }
        }
    }
}
fun primes() = PostponedPrimeIterator().asSequence()

val Int.isPrime:Boolean get() = toBigInteger().isProbablePrime(20)
