package kotlinPuzzleLibrary

class WhenRegexBuilder<R> {
    data class WhenMatch<R>(val regex: Regex, val action: WhenMatchContext.()->R)
    val cases: MutableList<WhenMatch<R>> = mutableListOf()

    infix fun Regex.then(action: WhenMatchContext.()->R) = cases.add(WhenMatch(this, action))
    infix fun String.then(action: WhenMatchContext.()->R) = cases.add(WhenMatch(Regex(this), action))
}
class WhenMatchContext(val match: MatchResult) {
    val groups get() = match.groups
    val g: List<String> get() = match.groupValues
    val d: List<Int> = g.map { it.toIntOrNull() ?: 0 }
    val ds: List<Int?> = g.map { it.toIntOrNull() }
    val c: List<Char> = g.map { it[0] }

    val g0: String get() = g[0]
    val g1: String get() = g[1]
    val g2: String get() = g[2]
    val g3: String get() = g[3]
    val g4: String get() = g[4]
    val g5: String get() = g[5]
    val g6: String get() = g[6]
    val g7: String get() = g[7]
    val g8: String get() = g[8]
    val g9: String get() = g[9]
    val d0: Int get() = d[0]
    val d1: Int get() = d[1]
    val d2: Int get() = d[2]
    val d3: Int get() = d[3]
    val d4: Int get() = d[4]
    val d5: Int get() = d[5]
    val d6: Int get() = d[6]
    val d7: Int get() = d[7]
    val d8: Int get() = d[8]
    val d9: Int get() = d[9]
    val c0: Char get() = c[0]
    val c1: Char get() = c[1]
    val c2: Char get() = c[2]
    val c3: Char get() = c[3]
    val c4: Char get() = c[4]
    val c5: Char get() = c[5]
    val c6: Char get() = c[6]
    val c7: Char get() = c[7]
    val c8: Char get() = c[8]
    val c9: Char get() = c[9]
}
fun <R> whenRegex(s: CharSequence, init: WhenRegexBuilder<R>.() -> Unit): R? {
    val builder = WhenRegexBuilder<R>()
    builder.init()
    for (case in builder.cases) {
        val match = case.regex.matchEntire(s)
        if (match != null) {
            val matchContext = WhenMatchContext(match)
            return case.action(matchContext)
        }
    }
    return null
}
fun whenRegex(s: CharSequence, init: WhenRegexBuilder<Unit>.() -> Unit) { whenRegex<Unit>(s, init) }
