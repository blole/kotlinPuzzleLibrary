package kotlinPuzzleLibrary

import java.math.BigInteger

operator fun BigInteger.plus(other: Int): BigInteger = this.add(other.toBigInteger())
operator fun BigInteger.minus(other: Int): BigInteger = this.subtract(other.toBigInteger())
operator fun BigInteger.times(other: Int): BigInteger = this.multiply(other.toBigInteger())
operator fun BigInteger.div(other: Int): BigInteger = this.divide(other.toBigInteger())
operator fun BigInteger.rem(other: Int): BigInteger = this.remainder(other.toBigInteger())

operator fun BigInteger.plus(other: Long): BigInteger = this.add(other.toBigInteger())
operator fun BigInteger.minus(other: Long): BigInteger = this.subtract(other.toBigInteger())
operator fun BigInteger.times(other: Long): BigInteger = this.multiply(other.toBigInteger())
operator fun BigInteger.div(other: Long): BigInteger = this.divide(other.toBigInteger())
operator fun BigInteger.rem(other: Long): BigInteger = this.remainder(other.toBigInteger())
