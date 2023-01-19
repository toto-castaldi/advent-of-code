import java.io.File

class Aoc202216() {

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

    private fun solve(pos: String, time: Int, opened: MutableSet<String>): Int {
        val cacheKey = cacheKey(pos, time, opened)
        //memoization
        if (cache[cacheKey] != null) return cache[cacheKey]!!

        if (time == 0) {
            return 0
        }

        var score = valves[pos]!!.valveDestinations.maxOf { dest ->

            solve(dest, time - 1, opened)
        }

        if (valves[pos]!!.valveRate > 0 && pos !in opened) {
            //clone opened
            val newOpened = opened.toMutableSet()
            newOpened.add(pos)
            score = maxOf(
                score,
                (time - 1) * valves[pos]!!.valveRate + solve(pos, time - 1, newOpened,)
            )
        }

        cache[cacheKey] = score
        return score
    }

    private val cacheKey = {
            pos: String, time: Int, opened: MutableSet<String> ->
            pos + "-" + time.toString() + "--" + opened.sorted().fold ( "") { s, acc -> "$acc;$s" }
    }

    fun mostPressureIn(minutes: Int): Int {
        cache = mutableMapOf<String, Int>()
        return solve("AA", minutes, mutableSetOf(),)
    }

    companion object {
        fun run(fileName: String): Int {
            val aoc = Aoc202216()
            File(fileName).readLines().forEach { line -> aoc + line }
            return aoc.mostPressureIn(30)
        }
    }


}