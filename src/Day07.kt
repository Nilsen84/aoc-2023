fun main() {
    data class Hand(val cards: String, val bid: Int)

    fun typeComparator() = compareByDescending<Hand> { hand ->
        val counts = hand.cards.groupingBy { it }.eachCount()
        when (counts.size) {
            1 -> 0
            2 -> if (counts.values.contains(4)) 1 else 2
            3 -> if (counts.values.contains(3)) 3 else 4
            4 -> 5
            else -> 6
        }
    }

    fun cardComparator(cardOrder: String) = Comparator<Hand> { a, b ->
        a.cards.zip(b.cards).map { cardOrder.indexOf(it.second) - cardOrder.indexOf(it.first) }.firstOrNull { it != 0 } ?: 0
    }

    fun removeJokers(hand: Hand) = hand.cards.map { hand.copy(cards = hand.cards.replace('J', it)) }.maxWith(typeComparator())

    fun parse(input: String): List<Hand> {
        return input.lines().map {
            val (cards, bid) = it.split(' ')
            Hand(cards, bid.toInt())
        }
    }

    fun part1(input: String): Int {
        return parse(input)
            .sortedWith(typeComparator().then(cardComparator("AKQJT98765432")))
            .asSequence()
            .zip(generateSequence(1, Int::inc))
            .sumOf { (hand, rank) -> hand.bid * rank }
    }

    fun part2(input: String): Int {
        return parse(input)
            .sortedWith(compareBy(typeComparator(), ::removeJokers).then(cardComparator("AKQT98765432J")))
            .asSequence()
            .zip(generateSequence(1, Int::inc))
            .sumOf { (hand, rank) -> hand.bid * rank }
    }

    val test = """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent()

    println(part1(test))
    println(part2(test))
    println()

    val input = readInput(7)
    println(part1(input))
    println(part2(input))
}