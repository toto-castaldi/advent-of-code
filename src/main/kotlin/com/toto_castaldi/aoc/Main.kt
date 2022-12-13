fun main(args: Array<String>) {
    val aoc202213 : Aoc202213 = Aoc202213.build(args[0])
    val correctOrderIndexes = aoc202213.correctOrderIndexes()
    println(correctOrderIndexes)
    println(correctOrderIndexes.fold(0) { acc, value -> acc + value})
}