import java.io.File

private val supplyVal = { supply: Char -> if (supply.isLowerCase()) supply.code - 'a'.code + 1 else  supply.code - 'A'.code + 27}

private class Rucksack() {

    private val compartments = mutableListOf<List<Char>>()

    fun insert(supplies: String) {
        val compartment = mutableListOf<Char>()
        for (supply in supplies) {
            if (supply !in compartment) {
                compartment.add(supply)
            }
        }
        compartments.add(compartment)
    }

    fun commonItems(): List<Char> {
        return compartments.fold(compartments[0]) { acc, c -> acc.intersect(c.toSet()).toList() }
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

        for (supply in rucksack.commonItems()) {
            totalDuplication += supplyVal(supply)
        }

        if (count == 3) {
            count = 0
            val groupRucksack = Rucksack()
            for (supplies in groupSupplies) {
                groupRucksack.insert(supplies)
            }
            groupRucksack.commonItems().forEach { entry ->
                totalInCommon += supplyVal(entry)
            }
        }
    }
    println("result1 $totalDuplication")
    println("result2 $totalInCommon")
}

