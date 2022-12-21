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

    fun humanYellForMonkeyEquality(name: String): Long {
        TODO("Not yet implemented")
    }

    fun human(name: String) {
        //
    }

    companion object {
        fun run1(fileName: String) {
            val aoc = Aoc202221()
            File(fileName).forEachLine {aoc + it}
            println( aoc.monkeyYells("root"))
        }

        fun run2(fileName: String) {
            val root = "root"
            val aoc = Aoc202221()
            File(fileName).forEachLine {aoc + it}

            aoc.human("humn")

            val (leftName, rightName) = aoc.monkeyOperandNames(root)

            val noRoot = mutableMapOf<String, CsAcademyGraph.CsAcademyGraphNode>()
            for (entry in aoc.dictionary.entries) {
                if (entry.key != root) {
                    noRoot[entry.key] = entry.value
                }
            }

            println("############## $leftName ##############")
            CsAcademyGraph.printGraph(noRoot, leftName)
            println("############## $rightName ##############")
            CsAcademyGraph.printGraph(noRoot, rightName)

            println( aoc.humanYellForMonkeyEquality("root"))
        }

    }

}
