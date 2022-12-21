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

    private fun removeMonkey(name: String) {
        dictionary.remove(name)
    }


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

                override fun getOperation(): String? {
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

                override fun constantValue(): String? {
                    return compute().toString()
                }

                override fun children(): List<String> {
                    return emptyList()
                }
            }
        }
    }

    private fun monkeyOperandNames(name: String): List<String> {
        return listOf(dictionary[name]?.getLeftName()!!,dictionary[name]?.getRightName()!!)
    }

    fun monkeyYells(monkeyName: String): Long {
        return dictionary[monkeyName]!!.compute()
    }


    companion object {
        fun run1(fileName: String) {
            val aoc = Aoc202221()
            File(fileName).forEachLine {aoc + it}
            println( aoc.monkeyYells("root"))
        }

        fun run2(fileName: String) {
            val root = "root"
            val human = "humn"
            val aoc = Aoc202221()
            File(fileName).forEachLine {aoc + it}

            val (leftName, rightName) = aoc.monkeyOperandNames(root)

            aoc.removeMonkey(root)

            println("############## $leftName ##############")
            CsAcademyGraph.printGraph(convert(aoc.dictionary), leftName)
            println("############## $rightName ##############")
            CsAcademyGraph.printGraph(convert(aoc.dictionary), rightName)

            val leftContainsHuman = aoc.contains(leftName, human)
            val rightContainsHuman = aoc.contains(rightName, human)
            if (leftContainsHuman && rightContainsHuman) throw Exception("impossible !")
            val valueToReach = if (leftContainsHuman) {
                aoc.monkeyYells(rightName)
            } else {
                aoc.monkeyYells(leftName)
            }
            println("human must yells to reach $valueToReach")
            println(if (leftContainsHuman) {
                aoc.humanYellForValue(rightName, human, valueToReach)
            } else {
                aoc.humanYellForValue(leftName, human, valueToReach)
            })
        }

        private fun convert(dictionary: MutableMap<String, Operation>): MutableMap<String, CsAcademyGraph.CsAcademyGraphNode> {
            val result = mutableMapOf<String, CsAcademyGraph.CsAcademyGraphNode>()
            for (entry in dictionary.entries) {
                result[entry.key] = entry.value
            }
            return result
        }

    }

    private fun humanYellForValue(startName: String, humanName: String, valueToReach: Long): Long {
        TODO("Not yet implemented")
    }

    private fun contains(startName: String, name: String): Boolean {
        if (startName == name) return true
        val node = dictionary[startName]!!
        if (node.getLeftName() != null && contains(node.getLeftName()!!, name)) return true
        if (node.getRightName() != null && contains(node.getRightName()!!, name)) return true
        return false
    }

}
