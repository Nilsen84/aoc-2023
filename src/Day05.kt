import kotlin.math.min

fun main() {
    data class Mapping(val dst: Long, val src: Long, val size: Long) {
        val srcRange = src..<src+size
        fun map(n: Long) = n - src + dst
    }

    fun List<Mapping>.map(range: LongRange): List<LongRange> {
        val res = mutableListOf<LongRange>()
        var work = range

        for (mapping in this) {
            if (work.isEmpty()) break

            if (work.first < mapping.src) {
                res += work.first..<min(work.last+1, mapping.src)
                work = mapping.src..work.last
            }

            val last = min(work.last, mapping.srcRange.last)
            if (last - work.first >= 0) {
                res += mapping.map(work.first)..mapping.map(last)
                work = last+1..work.last
            }
        }

        if (!work.isEmpty()) res += work
        return res
    }

    fun parse(input: String): Pair<List<Long>, List<List<Mapping>>> {
        val blocks = input.split("\n\n")
        val seeds = blocks.first().removePrefix("seeds: ").split(' ').map(String::toLong)

        val maps = blocks.drop(1).map { block ->
            block.lines().drop(1).map {
                val (dst, src, size) = it.split(' ').map(String::toLong)
                Mapping(dst, src, size)
            }.sortedBy { it.src }
        }

        return seeds to maps
    }

    fun solve(ranges: List<LongRange>, mappings: List<List<Mapping>>): Long {
        return mappings.fold(ranges) { acc, map ->
            acc.flatMap(map::map)
        }.minOf { it.first }
    }

    fun part1(input: String): Long {
        val (seeds, mappings) = parse(input)
        return solve(seeds.map { it..it }, mappings)
    }

    fun part2(input: String): Long {
        val (seeds, mappings) = parse(input)
        return solve(seeds.chunked(2) { (start, size) -> start..<start+size }, mappings)
    }
    
    val input = readInput(5)
    println(part1(input))
    println(part2(input))
}