import java.io.File

private class Rucksack(description: String) {

    private val firstCompartment = mutableMapOf<Char, Int>()
    private val secondCompartment = mutableMapOf<Char, Int>()
    private val commonSupplies = mutableMapOf<Char, Int>()
    private var firstCompartmentUsage = 0
    private var secondCompartmentUsage = 0
    var duplicationPriorities = 0
    private var compartmentSize: Int

    init {
        compartmentSize = description.length / 2
    }

    fun insert(supply: Char, lower: Array<Char>, upper: Array<Char>) {
        var compInsert = firstCompartment
        var compTest = secondCompartment

        if (firstCompartmentUsage < compartmentSize) {
            firstCompartmentUsage ++
        } else {
            secondCompartmentUsage ++
            compInsert = secondCompartment
            compTest = firstCompartment
        }

        compInsert[supply] = compInsert[supply]?.let { c -> c + 1 } ?: 1
        if (supply in compTest.keys) {
            if (supply !in commonSupplies.keys) {
                duplicationPriorities += if (supply in lower) lower.indexOf(supply) + 1 else upper.indexOf(supply) + 1 + lower.size
            }
            commonSupplies[supply] = commonSupplies[supply]?.let { c -> c + 1 } ?: 1
        }
    }
}

fun main(args: Array<String>) {
    val lower = Array(26) { ' ' }
    val upper = Array(26) { ' ' }

    var base = 'a'.code
    for (c in 'a' .. 'z') {
        lower[c.code - base] = c
    }
    base = 'A'.code
    for (c in 'A' .. 'Z') {
        upper[c.code - base] = c
    }

    println(lower.contentToString())
    println(upper.contentToString())

    var totalDuplication = 0

    File(args[0]).forEachLine { line ->
        val rucksack = Rucksack(line)

        for (supply in line) {
            rucksack.insert(supply, lower, upper)
        }

        totalDuplication += rucksack.duplicationPriorities
    }
    println("result $totalDuplication")
}
