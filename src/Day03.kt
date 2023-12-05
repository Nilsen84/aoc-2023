fun collectNum(grid: List<String>, numX: Int, numY: Int): Int? {
    if (numY < 0 || numY > grid.lastIndex || numX < 0 || numX > grid[numY].lastIndex) return null
    val line = grid[numY]
    if (!line[numX].isDigit()) return null

    var x1 = numX
    while (x1 > 0 && line[x1-1].isDigit()) x1--

    var x2 = numX
    while (x2 < line.lastIndex && line[x2+1].isDigit()) x2++

    return line.substring(x1..x2).toInt()
}

fun collectNums(grid: List<String>, symX: Int, symY: Int): List<Int> {
    val res = mutableListOf<Int>()

    collectNum(grid, symX-1, symY)?.let(res::add)
    collectNum(grid, symX+1, symY)?.let(res::add)

    val top = collectNum(grid, symX, symY-1)
    if (top != null) {
        res += top
    } else {
        collectNum(grid, symX-1, symY-1)?.let(res::add)
        collectNum(grid, symX+1, symY-1)?.let(res::add)
    }

    val bottom = collectNum(grid, symX, symY+1)
    if (bottom != null) {
        res += bottom
    } else {
        collectNum(grid, symX-1, symY+1)?.let(res::add)
        collectNum(grid, symX+1, symY+1)?.let(res::add)
    }

    return res
}

fun part1(lines: List<String>): Int {
    var sum = 0
    for ((y, line) in lines.withIndex()) {
        for ((x, c) in line.withIndex()) {
            val symbol = !c.isDigit() && c != '.'
            if (!symbol) continue
            sum += collectNums(lines, x, y).sum()
        }
    }
    return sum
}

fun part2(lines: List<String>): Int {
    var sum = 0
    for ((y, line) in lines.withIndex()) {
        for ((x, c) in line.withIndex()) {
            if (c != '*') continue
            val nums = collectNums(lines, x, y)
            if (nums.size != 2) continue
            sum += nums.reduce(Int::times)
        }
    }
    return sum
}

fun main() {
    val test = """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...${'$'}.*....
        .664.598..
    """.trimIndent().lines()

    println(part1(test))
    println()

    val input = readInput(3).lines()
    println(part1(input))
    println(part2(input))
}