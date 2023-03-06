import com.toto_castaldi.common.tools.CsAcademyGraph
import java.io.File
import java.lang.Exception

class Aoc202221() {

    interface Operation : CsAcademyGraph.CsAcademyGraphNode{
        fun compute() : Long
        fun getRightName(): String ?
        fun getLeftName(): String ?
        fun getOperation(): String ?
    }

    var dictionary = mutableMapOf<String, Operation>()

    operator fun plus(rule: String) {
        val monkeyName = rule.trim().split(":")[0]
        val operators = rule.trim().split(":")[1].trim().split(" ")
        if (operators.size == 3) { //binary operator
            dictionary[monkeyName] = object : Operation {
                override fun getRightName(): String {
                    return operators[2].trim()
                }

                override fun getLeftName(): String {
                    return operators[0].trim()
                }

                override fun getOperation(): String {
                    return operators[1].trim()
                }

                override fun noChild(): Boolean {
                    return false
                }

                override fun constantValue(): String? {
                    return null
                }

                override fun children(): List<String> {
                    return listOf(getLeftName(), getRightName())
                }

                override fun compute(): Long {
                    val firstValue = dictionary[getLeftName()]!!.compute()
                    val secondValue = dictionary[getRightName()]!!.compute()
                    when (val opSymbol = getOperation()) {
                        "+" -> return firstValue + secondValue
                        "-" -> return firstValue - secondValue
                        "*" -> return firstValue * secondValue
                        "/" -> return firstValue / secondValue
                        else -> throw Exception("unknow operation $opSymbol")
                    }
                }
            }
        } else { //costant
            dictionary[monkeyName] = object : Operation {
                override fun compute(): Long { return  operators[0].trim().toLong() }
                override fun getRightName(): String? {
                    return null
                }

                override fun getLeftName(): String? {
                    return null
                }

                override fun getOperation(): String? {
                    return null
                }

                override fun noChild(): Boolean {
                    return true
                }

                override fun constantValue(): String {
                    return compute().toString()
                }

                override fun children(): List<String> {
                    return emptyList()
                }
            }
        }
    }

    private fun contains(startName: String, name: String): Boolean {
        if (startName == name) return true
        val node = dictionary[startName]!!
        if (node.getLeftName() != null && contains(node.getLeftName()!!, name)) return true
        if (node.getRightName() != null && contains(node.getRightName()!!, name)) return true
        return false
    }


    fun monkeyYells(monkeyName: String): Long {
        return dictionary[monkeyName]!!.compute()
    }


    fun resolve(rootName: String, human: String, reachValue : Long?): Long {
        val operation = dictionary[rootName]!!

        if (operation.getLeftName() != null && operation.getRightName() != null) {
            val leftName = operation.getLeftName()!!
            val rightName = operation.getRightName()!!

            /*
            println("############## $leftName ##############")
            CsAcademyGraph.printGraph(convert(dictionary, rootName), leftName)
            println("############## $rightName ##############")
            CsAcademyGraph.printGraph(convert(dictionary, rootName), rightName)
            */

            val leftContainsHuman = contains(leftName, human)
            val rightContainsHuman = contains(rightName , human)
            if (leftContainsHuman && rightContainsHuman) throw Exception("impossible !")
            val costantValue = if (leftContainsHuman) {
                monkeyYells(rightName)
            } else {
                monkeyYells(leftName)
            }
            if (reachValue == null) { //equality
                return if (leftContainsHuman) {
                    resolve(leftName, human, costantValue)
                } else {
                    resolve(rightName, human, costantValue)
                }
            } else {
                return when (val opSymbol = operation.getOperation()!!) {
                    "/" -> if (leftContainsHuman) resolve(leftName, human, reachValue * costantValue) else resolve(rightName, human,costantValue / reachValue)
                    "-" -> if (leftContainsHuman) resolve(leftName, human, reachValue + costantValue) else resolve(rightName, human,costantValue - reachValue)
                    "+" -> if (leftContainsHuman) resolve(leftName, human, reachValue - costantValue) else resolve(rightName, human,reachValue - costantValue)
                    "*" -> if (leftContainsHuman) resolve(leftName, human, reachValue / costantValue) else resolve(rightName, human, reachValue / costantValue )
                    else -> throw Exception("unknow operation $opSymbol")
                }
            }
        } else {
            return reachValue!!
        }
    }

    companion object {
        fun run1(fileName: String) {
            val aoc = Aoc202221()
            File(fileName).forEachLine {aoc + it}
            println( aoc.monkeyYells("root"))
        }

        fun run2(fileName: String) {
            val aoc = Aoc202221()
            File(fileName).forEachLine {aoc + it}
            println( aoc.resolve("root", "humn",null))
        }

    }
}
