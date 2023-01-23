import java.io.File

class Aoc202216() {
    private val startingValve = "AA"
    private val minutesForTwo = 26

    private val valves = mutableMapOf<String, ValveDefinition>()
    private lateinit var cache : MutableMap<String, Int>

    data class ValveDefinition(val valveRate: Int, val valveDestinations: Set<String>)

    operator fun plus(valveDefinitionStr: String): Aoc202216 {
        //Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
        val match = "Valve (\\w+) has flow rate=(-?\\d+); tunnel[s]? lead[s]? to valve[s]? ([\\w,\\s]+)".toRegex().find(valveDefinitionStr)!!
        val valveName = match.groups[1]!!.value
        val valveRate = match.groups[2]!!.value.toInt()
        val valveDestinations = match.groups[3]!!.value.split(",").map { it -> it.trim() }.toSet()

        val valveDefinition = ValveDefinition(valveRate, valveDestinations)

        valves[valveName] = valveDefinition

        return this
    }

    private fun solve(currentValve: String, time: Int, openedValves: Set<String>, elephantWait : Boolean = false): Int {
        val cacheKey = cacheKey(currentValve, time, openedValves, elephantWait)
        //memoization
        if (cache[cacheKey] != null) return cache[cacheKey]!!

        if (time == 0) {
            if (elephantWait) {
                return solve(startingValve, minutesForTwo, openedValves, false)
            }
            return 0
        }

        val currentValveDefinition = valves[currentValve]!!

        var score = currentValveDefinition.valveDestinations.maxOf { dest ->
            solve(dest, time - 1, openedValves, elephantWait)
        }

        if (currentValveDefinition.valveRate > 0 && currentValve !in openedValves) {
            //clone opened
            val newOpened = openedValves.toMutableSet()
            newOpened.add(currentValve)
            score = maxOf(
                score,
                (time - 1) * currentValveDefinition.valveRate + solve(currentValve, time - 1, newOpened, elephantWait)
            )
        }

        cache[cacheKey] = score
        return score
    }

    private val cacheKey = {
            pos: String, time: Int, opened: Set<String>, elephantWait : Boolean ->
            pos + "-" + time.toString() + "--" + opened.sorted().fold ( "") { s, acc -> "$acc;$s" } + elephantWait.toString()
    }

    fun mostPressureBySingleOperator(): Int {
        cache = mutableMapOf<String, Int>()
        return solve(startingValve, 30, setOf(),)
    }

    fun mostPressureByTwoOperator(): Int {
        cache = mutableMapOf<String, Int>()
        return solve(startingValve, minutesForTwo, setOf(), true)
    }

    companion object {
        fun run1(fileName: String): Int {
            val aoc = Aoc202216()
            File(fileName).readLines().forEach { line -> aoc + line }
            return aoc.mostPressureBySingleOperator()
        }

        fun run2(fileName: String): Int {
            val aoc = Aoc202216()
            File(fileName).readLines().forEach { line -> aoc + line }
            return aoc.mostPressureByTwoOperator()
        }
    }


}