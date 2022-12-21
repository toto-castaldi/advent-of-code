import java.lang.Exception

class Aoc202221() {

    interface Operation<T> {
        fun compute() : T
    }


    var dictionary = mutableMapOf<String, Operation<Int>>()

    operator fun plus(rule: String) {
        val monkeyName = rule.trim().split(":")[0]
        val operators = rule.trim().split(":")[1].trim().split(" ")
        if (operators.size == 3) { //binary operator
            dictionary[monkeyName] = object : Operation<Int> {
                override fun compute(): Int {
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
            dictionary[monkeyName] = object : Operation<Int> {
                override fun compute(): Int { return  operators[0].trim().toInt() }
            }
        }
    }

    fun monkeyYells(monkeyName: String): Int {
        return dictionary[monkeyName]!!.compute()
    }

    companion object {
        fun run(fileName: String) {
            println( fileName)
        }

    }

}
