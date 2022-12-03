import java.io.File

fun supplyVal(supply: Char, lower: Array<Char>, upper: Array<Char>): Int {
    return if (supply in lower) lower.indexOf(supply) + 1 else upper.indexOf(supply) + 1 + lower.size
}
private class Rucksack() {

    private val compartments = mutableListOf<Map<Char, Int>>()
    private val duplicationSupplies = mutableListOf<Char>()
    private val commonSupplies = mutableMapOf<Char, Int>()
    var duplicationPriorities = 0

    fun insert(supplies: String, lower: Array<Char>, upper: Array<Char>) {
        var compartment = mutableMapOf<Char, Int>()
        val localSupplies = mutableListOf<Char>()
        for (supply in supplies) {
            compartment[supply] = compartment[supply]?.let { c -> c + 1 } ?: 1
            if (supply !in localSupplies) {
                commonSupplies[supply] = commonSupplies[supply]?.let { c -> c + 1 } ?: 1
            }
            localSupplies.add(supply)
            for (compTest in compartments) {
                if (supply in compTest.keys) {
                    if (supply !in duplicationSupplies) {
                        duplicationPriorities += supplyVal(supply, lower, upper)
                    }
                    duplicationSupplies.add(supply)
                }
            }
        }
        compartments.add(compartment)
    }

    fun getAllInCommons(): List<MutableMap.MutableEntry<Char, Int>> {
        return commonSupplies.entries.filter { entry -> entry.value == compartments.size }
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

    var totalDuplication = 0
    var totalInCommon = 0

    var groupSupplies = Array(3) { "" }
    var count = 0

    File(args[0]).forEachLine { line ->
        val rucksack = Rucksack()
        groupSupplies[count] = line
        count++

        //part 1
        rucksack.insert(line.substring(0, line.length / 2), lower, upper)
        rucksack.insert(line.substring(line.length / 2, line.length), lower, upper)

        totalDuplication += rucksack.duplicationPriorities

        if (count == 3) {
            count = 0
            val groupRucksack = Rucksack()
            for (supplies in groupSupplies) {
                groupRucksack.insert(supplies, lower, upper)
            }
            groupRucksack.getAllInCommons().forEach { entry ->
                totalInCommon += supplyVal(entry.key, lower, upper)

            }
        }
    }
    println("result1 $totalDuplication")
    println("result2 $totalInCommon")
}

