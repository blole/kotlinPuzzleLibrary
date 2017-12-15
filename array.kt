package kotlinPuzzleLibrary

inline fun <reified T> array2d(sizeOuter: Int, sizeInner: Int, noinline innerInit: (Int)->T): Array<Array<T>>
        = Array(sizeOuter) { Array(sizeInner, innerInit) }
fun array2dOfInt(sizeOuter: Int, sizeInner: Int): Array<IntArray> = Array(sizeOuter) { IntArray(sizeInner) }
fun array2dOfLong(sizeOuter: Int, sizeInner: Int): Array<LongArray> = Array(sizeOuter) { LongArray(sizeInner) }
fun array2dOfByte(sizeOuter: Int, sizeInner: Int): Array<ByteArray> = Array(sizeOuter) { ByteArray(sizeInner) }
fun array2dOfChar(sizeOuter: Int, sizeInner: Int): Array<CharArray> = Array(sizeOuter) { CharArray(sizeInner) }
fun array2dOfBoolean(sizeOuter: Int, sizeInner: Int): Array<BooleanArray> = Array(sizeOuter) { BooleanArray(sizeInner) }

data class Pos(val x: Int, val y: Int) {
    val up:    Pos get() = Pos(x  ,y-1)
    val down:  Pos get() = Pos(x  ,y+1)
    val left:  Pos get() = Pos(x-1,y  )
    val right: Pos get() = Pos(x+1,y  )
}
fun Pair<Int,Int>.toPos() = Pos(first, second)

operator fun Array<BooleanArray>.get(pos: Pos): Boolean = this[pos.y][pos.x]
operator fun Array<BooleanArray>.set(pos: Pos, value: Boolean) { this[pos.y][pos.x] = value}
