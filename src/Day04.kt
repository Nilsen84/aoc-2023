fun main() {
    fun getMatchingNumbers(card: String): Int {
        val (winning, nums) = card.substringAfter(": ").split(" | ")
        return winning.split(' ').filter { it.isNotEmpty() }.intersect(nums.split(' ')).size
    }

    fun part1(lines: List<String>): Int {
        return lines.sumOf {
            val matching = getMatchingNumbers(it)
            if (matching == 0) 0 else 1 shl matching -1
        }
    }

    fun part2(lines: List<String>): Int {
        val cards = IntArray(lines.size) { 1 }
        for ((i, card) in lines.withIndex()) {
            val matching = getMatchingNumbers(card)
            for (i2 in i+1..i+matching) {
                cards[i2] += cards[i]
            }
        }
        return cards.sum()
    }

    val test = """
        Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
        Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
        Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
        Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
        Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
    """.trimIndent().lines()

    println(part1(test))
    println(part2(test))
    println()

    val input = readInput(4).lines()
    println(part1(input))
    println(part2(input))
}