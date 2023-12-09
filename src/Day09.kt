fun main() {
    fun parse(input: String): List<List<Int>> {
        return input.lines().map {
            it.split(' ').map(String::toInt)
        }
    }

    fun List<Int>.getNext(): Int {
        if (last() == 0) return 0
        return last() + zipWithNext { a, b -> b - a }.getNext()
    }

    fun List<Int>.getPrev(): Int {
        if (last() == 0) return 0
        return first() - zipWithNext { a, b -> b - a }.getPrev()
    }

    fun part1(input: String) = parse(input).sumOf { it.getNext() }
    fun part2(input: String) = parse(input).sumOf { it.getPrev() }

    val test = """
        0 3 6 9 12 15
        1 3 6 10 15 21
        10 13 16 21 30 45
    """.trimIndent()

    println(part1(test))
    println(part2(test))
    println()

    val input = readInput(9)
    println(part1(input))
    println(part2(input))
}