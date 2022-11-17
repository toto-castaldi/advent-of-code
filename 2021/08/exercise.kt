import java.io.File
import java.util.Arrays
import java.util.Collections

val validDisplays : Map<String, Int> = mapOf(
    "cf" to 1,
    "acdeg" to 2,
    "acdfg" to 3,
    "bcdf" to 4,
    "abdfg" to 5,
    "abdefg" to 6,
    "acf" to 7,
    "abcdefg" to 8,
    "abcdfg" to 9,
    "abcefg" to 0
)

fun <T> allPermutations(set: Set<T>): Set<List<T>> {
    if (set.isEmpty()) return emptySet()

    fun <T> _allPermutations(list: List<T>): Set<List<T>> {
        if (list.isEmpty()) return setOf(emptyList())

        val result: MutableSet<List<T>> = mutableSetOf()
        for (i in list.indices) {
            _allPermutations(list - list[i]).forEach{
                item -> result.add(item + list[i])
            }
        }
        return result
    }

    return _allPermutations(set.toList())
}

fun validPermutation(permutation : DisplayPermutation, commands : List<Command>) : Boolean {
    for (c in commands) {
        val coded = permutation.applyRule(c)
        val result : Boolean = validDisplays.keys.contains(coded)

        if (!result) {
            return false
        }
    }
    return true
}

class Command {
    val size : Int
    val elements : List<Char>

    constructor (coded : String) {
        size = coded.length
        elements = coded.toCharArray().asList().sorted()
    }

    override fun toString() : String {
        return elements.toString()
    }
}


class DisplayPermutation {
    val ruleMap : HashMap<Char, Char> 

    constructor (positions : List<Char>) {
        ruleMap = HashMap()
        ruleMap.put('a', positions.get(0))
        ruleMap.put('b', positions.get(1))
        ruleMap.put('c', positions.get(2))
        ruleMap.put('d', positions.get(3))
        ruleMap.put('e', positions.get(4))
        ruleMap.put('f', positions.get(5))
        ruleMap.put('g', positions.get(6))
    }

    override fun toString() : String {
        return ruleMap.toString()
    }

    fun applyRule(command : Command) : String {
        var coded : String = ""
        for (e in command.elements) {
            coded += ruleMap.get(e)!!
        }
        return coded.toCharArray().sorted().fold("") { acc, value -> acc + value.toString() }
    }
}

fun main(args: Array<String>) {
    val fileName : String = args[0]
    val fileInput : File = File(fileName)
    var total1478 : Int = 0
    var total : Int = 0
    var permutations : List<DisplayPermutation> = allPermutations(setOf('a', 'b', 'c', 'd', 'e', 'f', 'g')).toList().map ({ DisplayPermutation(it) })
    fileInput.forEachLine() { 
        val input = it.split(" | ")[0]
        val output = it.split(" | ")[1]
        val commands : List<Command> = input.split("\\s".toRegex()).map({ Command(it.trim()) })
        val displays : List<Command> = output.split("\\s".toRegex()).map({ Command(it.trim()) })
        total1478 += displays.filter { it.size == 2 }.size
        total1478 += displays.filter { it.size == 3 }.size
        total1478 += displays.filter { it.size == 4 }.size
        total1478 += displays.filter { it.size == 7 }.size

        var pIndex = 0
        while (!validPermutation(permutations.get(pIndex), commands)) {
            pIndex ++
        }

        val correctPermutation: DisplayPermutation = permutations.get(pIndex)
        
        var displayTotal = displays.map( { 
            c : Command ->

            val p = correctPermutation.applyRule(c)
            val result = validDisplays.get(p)!! 
            println("$c -> $p -> $result")

            result.toString()
        }).fold("", { acc, value -> acc + value }).toInt()
        println(displayTotal)

        total += displayTotal
        

    }
    println("total1478 is $total1478")
    println("total is $total")
}
