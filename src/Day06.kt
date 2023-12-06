fun main() {
    fun distance(hold: Long, time: Long): Long = hold * (time - hold)

    fun parse(input: String): List<Pair<Long, Long>> {
        val (a, b) = input.lines()
        val times = a.removePrefix("Time:").split(' ').mapNotNull { it.toLongOrNull() }
        val distances = b.removePrefix("Distance:").split(' ').mapNotNull { it.toLongOrNull() }
        return times.zip(distances)
    }

    fun part1(input: String): Int {
        val parsed = parse(input)
        return parsed.map { (time, dist) ->
            (1..<time).count { distance(it, time) > dist }
        }.reduce(Int::times)
    }

    fun part2(input: String): Int {
        return part1(input.replace(" ", ""))
    }

    val test = """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent()
    println(part1(test))
    println(part2(test))

    val input = readInput(6)
    println(part1(input))
    println(part2(input))
}