import java.io.File
import java.lang.Exception

class Aoc202221() {

    interface Operation<T> {
        fun compute() : T
    }


    var dictionary = mutableMapOf<String, Operation<Long>>()

    operator fun plus(rule: String) {
        val monkeyName = rule.trim().split(":")[0]
        val operators = rule.trim().split(":")[1].trim().split(" ")
        if (operators.size == 3) { //binary operator
            dictionary[monkeyName] = object : Operation<Long> {
                override fun compute(): Long {
                    val firstValue = dictionary[operators[0].trim()]!!.compute()
                    val secondValue = dictionary[operators[2].trim()]!!.compute()
                    when (val opSymbol = operators[1].trim()) {
                        "+" -> return firstValue + secondValue
                        "-" -> return firstValue - secondValue
                        "*" -> return firstValue * secondValue
                        "/" -> return firstValue / secondValue
                        else -> throw Exception("unknow operation $opSymbol")
                    }
                }
            }
        } else { //costant
            dictionary[monkeyName] = object : Operation<Long> {
                override fun compute(): Long { return  operators[0].trim().toLong() }
            }
        }
    }

    fun monkeyYells(monkeyName: String): Long {
        return dictionary[monkeyName]!!.compute()
    }

    companion object {
        fun run(fileName: String) {
            val aoc = Aoc202221()
            File(fileName).forEachLine {aoc + it}
            println( aoc.monkeyYells("root"))
        }

    }

}
