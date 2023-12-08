import java.io.File

fun main() {
    fun parse(input: String): Pair<List<Int>, Map<String, List<String>>> {
        val (instructions, nodes) = input.split("\n\n")

        return instructions.map { if (it == 'L') 0 else 1 } to
                nodes.lines().associate {
                    val (node, next) = it.split(" = ")
                    node to next.removePrefix("(").removeSuffix(")").split(", ")
                }
    }

    fun part1(input: String): Int {
        val (instructions, nodes) = parse(input)
        return generateSequence { instructions }.flatten()
            .scan("AAA") { curr, insn -> nodes[curr]!![insn] }
            .takeWhile { it != "ZZZ" }
            .count()
    }

    fun part2(input: String): Long {
        val (instructions, nodes) = parse(input)
        fun steps(start: String) =
            generateSequence { instructions }.flatten()
                .scan(start) { curr, insn -> nodes[curr]!![insn] }
                .takeWhile { !it.endsWith('Z') }
                .count()

        return nodes.keys.filter { it.endsWith('A') }
            .map { steps(it) }
            .fold(1L) { acc, steps -> acc * steps / gcd(acc, steps.toLong()) }
    }

    val test = """
        LLR

        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent()

    println(part1(test))
    println()

    val input = readInput(8)
    println(part1(input))
    println(part2(input))
}