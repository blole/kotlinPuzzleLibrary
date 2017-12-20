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

operator fun Array<BooleanArray>.get(pos: Pos): Boolean = this[pos.y][pos.x]
operator fun Array<BooleanArray>.set(pos: Pos, value: Boolean) { this[pos.y][pos.x] = value}
operator fun List<String>.get(pos: Pos): Char = this[pos.y][pos.x]
operator fun <T> List<List<T>>.get(pos: Pos): T = this[pos.y][pos.x]

fun <T> List2d(width: Int, height: Int, init: (Pos) -> T): List<List<T>> {
    return (0 until height).map { y -> (0 until width).map { x -> init(Pos(x,y)) } }
}

fun <T> List<List<T>>.pad(margin: Int, init: (Pos) -> T): List<List<T>> {
    val sizex = this.map { it.size }.max() ?: return List2d(margin*2, margin*2, init)
    //var list = List<List<T>>(this.size + margin*2,)

    val top:    List<List<T>> =      (0 until margin)  .map { y -> (0 until sizex+margin*2).map { x -> init(Pos(x,y)) } }
    val bottom: List<List<T>> = (margin until margin*2).map { y -> (0 until sizex+margin*2).map { x -> init(Pos(x,y)) } }
    val middle: List<List<T>> = mapIndexed { y, list ->
                (0 until margin).map { x -> init(Pos(x,y+margin)) } +
                list +
                (list.size+margin until sizex+margin*2).map { x -> init(Pos(x,y+margin)) } }

    return top+middle+bottom
}
