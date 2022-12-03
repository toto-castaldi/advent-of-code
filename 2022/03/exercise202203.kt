import java.io.File


val supplyVal = { supply: Char -> if (supply.isLowerCase()) supply.code - 'a'.code + 1 else  supply.code - 'A'.code + 27}

private class Rucksack() {

    private val compartments = mutableListOf<Map<Char, Int>>()
    private val duplicationSupplies = mutableListOf<Char>()
    private val commonSupplies = mutableMapOf<Char, Int>()
    var duplicationPriorities = 0

    fun insert(supplies: String) {
        val compartment = mutableMapOf<Char, Int>()
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
                        duplicationPriorities += supplyVal(supply)
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
    var totalDuplication = 0
    var totalInCommon = 0

    val groupSupplies = Array(3) { "" }
    var count = 0

    File(args[0]).forEachLine { line ->
        val rucksack = Rucksack()
        groupSupplies[count] = line
        count++

        rucksack.insert(line.substring(0, line.length / 2))
        rucksack.insert(line.substring(line.length / 2, line.length))

        totalDuplication += rucksack.duplicationPriorities

        if (count == 3) {
            count = 0
            val groupRucksack = Rucksack()
            for (supplies in groupSupplies) {
                groupRucksack.insert(supplies)
            }
            groupRucksack.getAllInCommons().forEach { entry ->
                totalInCommon += supplyVal(entry.key)
            }
        }
    }
    println("result1 $totalDuplication")
    println("result2 $totalInCommon")
}

