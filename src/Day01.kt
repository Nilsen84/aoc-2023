fun main() {
    val digits = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

    fun getDigit(s: String, offset: Int): Int? {
        for ((i, num) in digits.withIndex()) {
            if (s.regionMatches(offset, num, 0, num.length)) {
                return i + 1
            }
        }
        return null
    }

    fun part1(lines: List<String>): Int {
        return lines.map { line ->
            "" + line.first { it.isDigit() } + line.last { it.isDigit() }
        }.sumOf { it.toInt() }
    }

    fun part2(lines: List<String>): Int {
        return lines.sumOf { line ->
            val nums = line.indices.mapNotNull {
                line[it].digitToIntOrNull() ?: getDigit(line, it)
            }
            nums.first() * 10 + nums.last()
        }
    }

    val input = readInput(1).lines()
    println(part1(input))
    println(part2(input))
}