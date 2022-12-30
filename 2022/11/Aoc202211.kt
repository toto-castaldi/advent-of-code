import java.io.File
import java.lang.Exception

class Aoc202211(mokeyDefinitions: List<List<String>>) {

    private var commonDivider: Int
    private var monkeys: MutableMap<Int, Monkey>

    init {
        commonDivider = 1
        monkeys = mutableMapOf<Int, Monkey>()
        for (monkeyDefinition in mokeyDefinitions) {
            val monkeyId = monkeyDefinition[0].trim().substring("Monkey ".length).split(":")[0].toInt()
            //val monkeyId = "Monkey (\\d+):".toRegex().find(monkeyDefinition[0].trim())!!.groups[0]!!.value.toInt()
            val monkeyItems = monkeyDefinition[1].trim().substring("Starting items: ".length).split(",").map { it.trim().toLong() }.toMutableList()
            val monkeyOperation = MonkeyOperation(monkeyDefinition[2].trim().substring("Operation: new = old ".length).split(" ").subList(0, 2))
            val monkeyDivider = monkeyDefinition[3].trim().substring("Test: divisible by ".length).toInt()
            val monkeyTrueId = monkeyDefinition[4].trim().substring("If true: throw to monkey ".length).toInt()
            val monkeyFalseId = monkeyDefinition[5].trim().substring("If false: throw to monkey ".length).toInt()
            monkeys[monkeyId] = Monkey(
                monkeyItems,
                monkeyOperation,
                monkeyDivider,
                monkeyTrueId,
                monkeyFalseId,
                0
            )
            commonDivider *= monkeyDivider
        }
    }

    private class MonkeyOperation(parsed: List<String>) {
        private var operationValue: String
        private var operationType: String

        init {
            operationType = parsed[0]
            operationValue = parsed[1]
        }

        override fun toString(): String {
            return "old $operationType $operationValue"
        }

        fun compute(item: Long): Long {
            when (operationType) {
                "+" -> if (operationValue == "old") return item + item else return item + operationValue.toLong()
                "*" -> if (operationValue == "old") return item * item else return item * operationValue.toLong()
                "-" -> if (operationValue == "old") return 0 else return item - operationValue.toLong()
                "/" -> if (operationValue == "old") return 1 else return item / operationValue.toLong()
                else -> {
                    throw Exception("!")
                }
            }
        }
    }

    private data class Monkey(
        var items: MutableList<Long>,
        val operation: MonkeyOperation,
        val divider: Int,
        val trueId: Int,
        val falseId: Int,
        var activity: Int
    )

    fun part1(): Int {
        val rounds = 20
        for (i in 0 until rounds) {
            for (monkeyId in monkeys.keys) {
                val monkey = monkeys[monkeyId]!!
                val newItems = mutableListOf<Long>()
                for (item in monkey.items) {
                    monkey.activity ++
                    val level = monkey.operation.compute(item) / 3
                    if (level % monkey.divider == 0L) {
                        monkeys[monkey.trueId]!!.items.add(level)
                    } else {
                        monkeys[monkey.falseId]!!.items.add(level)
                    }
                }
                monkey.items = newItems
            }
        }
        val byActivity = monkeys.values.sortedBy { it.activity }.reversed()
        return byActivity[0].activity * byActivity[1].activity
    }

    fun part2(): Long {
        val rounds = 10000
        for (i in 0 until rounds) {
            for (monkeyId in monkeys.keys) {
                val monkey = monkeys[monkeyId]!!
                val newItems = mutableListOf<Long>()
                for (item in monkey.items) {
                    monkey.activity ++
                    val level = monkey.operation.compute(item) %  commonDivider
                    if (level % monkey.divider == 0L) {
                        monkeys[monkey.trueId]!!.items.add(level)
                    } else {
                        monkeys[monkey.falseId]!!.items.add(level)
                    }
                }
                monkey.items = newItems
            }
        }
        val byActivity = monkeys.values.sortedBy { it.activity }.reversed()
        return byActivity[0].activity.toLong() * byActivity[1].activity.toLong()
    }


    companion object {
        fun build(fileName: String): Aoc202211 {
            return Aoc202211(File(fileName).readLines().chunked(7))
        }
    }

}