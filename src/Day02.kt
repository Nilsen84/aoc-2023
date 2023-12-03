fun main() {
    fun part1(lines: List<String>): Int {
        val bag = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14
        )

        return lines.sumOf { l ->
            val (id, rest) = l.split(": ")
            val possible = rest.split(", ", "; ").all {
                val (num, col) = it.split(" ")
                bag[col]!! >= num.toInt()
            }
            if (possible) { id.removePrefix("Game ").toInt() } else 0
        }
    }

    fun part2(lines: List<String>): Int {
        return lines.sumOf { l ->
            val (_, rest) = l.split(": ")

            rest.split(", ", "; ")
                .map { it.split(' ') }
                .groupBy({ it[1] }, { it[0].toInt() })
                .values
                .fold(1.toInt()) { acc, cubes -> acc * cubes.max() }
        }
    }

    val test = """
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    """.trimIndent().lines()
    println(part1(test))
    println(part2(test))
    println()

    val input = readInput(2).lines()
    println(part1(input))
    println(part2(input))
}