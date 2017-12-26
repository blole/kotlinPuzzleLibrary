package kotlinPuzzleLibrary

inline fun <reified T> array2d(sizeOuter: Int, sizeInner: Int, noinline innerInit: (Int)->T): Array<Array<T>>
        = Array(sizeOuter) { Array(sizeInner, innerInit) }
fun array2dOfInt(sizeOuter: Int, sizeInner: Int): Array<IntArray> = Array(sizeOuter) { IntArray(sizeInner) }
fun array2dOfLong(sizeOuter: Int, sizeInner: Int): Array<LongArray> = Array(sizeOuter) { LongArray(sizeInner) }
fun array2dOfByte(sizeOuter: Int, sizeInner: Int): Array<ByteArray> = Array(sizeOuter) { ByteArray(sizeInner) }
fun array2dOfChar(sizeOuter: Int, sizeInner: Int): Array<CharArray> = Array(sizeOuter) { CharArray(sizeInner) }
fun array2dOfBoolean(sizeOuter: Int, sizeInner: Int): Array<BooleanArray> = Array(sizeOuter) { BooleanArray(sizeInner) }

data class Dir(val x: Int, val y: Int, val index: Int, val name: String) {
    companion object {
        val UP    = Dir( 0,-1, 0, "UP")
        val RIGHT = Dir( 1, 0, 1, "RIGHT")
        val DOWN  = Dir( 0, 1, 2, "DOWN")
        val LEFT  = Dir(-1, 0, 3, "LEFT")
        val ALL = listOf(UP, RIGHT, DOWN, LEFT)
    }
    val turnRight: Dir get() = ALL[(index+1)%4]
    val turnLeft: Dir get() = ALL[(index+3)%4]
    val turnAround: Dir get() = ALL[(index+2)%4]
    override fun toString() = "Dir.$name"
}
data class Pos(val x: Int, val y: Int) {
    val up:    Pos get() = this+Dir.UP
    val right: Pos get() = this+Dir.RIGHT
    val down:  Pos get() = this+Dir.DOWN
    val left:  Pos get() = this+Dir.LEFT

    operator fun plus(dir: Dir) = Pos(x+dir.x, y+dir.y)
}
fun Pair<Int,Int>.toPos() = Pos(first, second)

typealias List2d<T> = List<List<T>>

operator fun Array<BooleanArray>.get(pos: Pos): Boolean = this[pos.y][pos.x]
operator fun Array<BooleanArray>.set(pos: Pos, value: Boolean) { this[pos.y][pos.x] = value}
operator fun List<String>.get(pos: Pos): Char = this[pos.y][pos.x]
operator fun <T> List2d<T>.get(pos: Pos): T = this[pos.y][pos.x]

fun String.toList2d(): List2d<Char> = lines().map { it.toList() }
fun <T> List2d<T>.transpose(): List2d<T> = this[0].indices.map { col -> indices.map { row -> this[row][col] } }
fun <T> List2d<T>.flipud(): List2d<T> = asReversed()
fun <T> List2d<T>.fliplr(): List2d<T> = map { it.asReversed() }
fun <T> List2d<T>.chunked2d(x: Int, y: Int): List2d<List2d<T>> = chunked(y).map { it.map { it.chunked(x) }.transpose() }
fun <T> List2d<T>.chunked2d(size: Pos): List2d<List2d<T>> = chunked2d(size.x, size.y)
/** rotate right times. negative repeats are ok **/
fun <T> List2d<T>.rot90(repeats: Int = 1): List2d<T> = when {
    repeats<0      -> rot90(repeats%4+4)
    repeats%4 == 0 -> this
    else           -> this[0].indices.map { col -> indices.reversed().map { row -> this[row][col] } }.rot90(repeats-1)
}
fun <T,R> List2d<T>.mapIndexed2d(transform: (Pos, T) -> R): List2d<R> = mapIndexed { y,row -> row.mapIndexed { x,e -> transform(Pos(x,y),e) } }
fun <T> List2d<T>.withIndex2d(): List2d<Pair<Pos,T>> = mapIndexed { y,row -> row.mapIndexed { x,e -> Pair(Pos(x,y),e) } }
fun <T> List2d<T>.forEachIndexed2d(action: (Pos, T) -> Unit): Unit = forEachIndexed { y, row -> row.forEachIndexed { x, e -> action(Pos(x,y),e) } }

fun <A,B,R> Iterable<Pair<A,B>>.mapFirst(transform: (A) -> R): List<Pair<R,B>> = map { Pair(transform(it.first), it.second) }
fun <A,B,R> Iterable<Pair<A,B>>.mapSecond(transform: (B) -> R): List<Pair<A,R>> = map { Pair(it.first, transform(it.second)) }

fun <K,V> List<Pair<K,V>>.toHashMap(): HashMap<K, V> = hashMapOf(*this.toTypedArray())
fun <T,R> List<T>.toHashMap(key: (T)->R): HashMap<R,T> = map { Pair(key(it), it) }.toHashMap()

fun <T> List2d(width: Int, height: Int, init: (Pos) -> T): List<List<T>> {
    return (0 until height).map { y -> (0 until width).map { x -> init(Pos(x,y)) } }
}

fun <T> List2d<T>.pad(margin: Int, init: (Pos) -> T): List2d<T> {
    val sizex = this.map { it.size }.max() ?: return List2d(margin*2, margin*2, init)
    //var list = List<List<T>>(this.size + margin*2,)

    val top:    List2d<T> =      (0 until margin)  .map { y -> (0 until sizex+margin*2).map { x -> init(Pos(x,y)) } }
    val bottom: List2d<T> = (margin until margin*2).map { y -> (0 until sizex+margin*2).map { x -> init(Pos(x,y)) } }
    val middle: List2d<T> = mapIndexed { y, list ->
                (0 until margin).map { x -> init(Pos(x,y+margin)) } +
                list +
                (list.size+margin until sizex+margin*2).map { x -> init(Pos(x,y+margin)) } }

    return top+middle+bottom
}
