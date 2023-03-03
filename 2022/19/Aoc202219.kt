import java.io.File
import java.util.*
import kotlin.math.ceil

class Aoc202219() {

    enum class MATERIAL {
        ORE, CLAY, OBSIDIAN, GEODE
    }

    private val lines = mutableListOf<String>()


    operator fun plus(bluePrint: String) {
        lines.add(bluePrint)
    }

    fun part1(t: Int): Int {
        var result = 0

        for ((i, line) in lines.withIndex()) {
            result += (i+1) * solve(line, t)
        }

        return result
    }

    private fun solve(bluePrintDesc: String, t: Int): Int {
        val robotsDesc = bluePrintDesc.split(":")[1].trim()
        val oreForRobotOre = robotsDesc.split("Each ore robot costs ")[1].split("ore")[0].trim().toInt()
        val oreForRobotClay = robotsDesc.split("Each clay robot costs ")[1].split("ore")[0].trim().toInt()
        val oreForRobotObsidian = robotsDesc.split("Each obsidian robot costs ")[1].split("ore")[0].trim().toInt()
        val clayForRobotObsidian = robotsDesc.split("Each obsidian robot costs $oreForRobotObsidian ore and ")[1].split("clay")[0].trim().toInt()
        val oreForRobotGeode = robotsDesc.split("Each geode robot costs ")[1].split("ore")[0].trim().toInt()
        val obsidianForRobotGeode = robotsDesc.split("Each geode robot costs $oreForRobotGeode ore and ")[1].split("obsidian")[0].trim().toInt()

        val maxSpend = mapOf(
            MATERIAL.ORE to maxOf(oreForRobotOre, oreForRobotClay, oreForRobotObsidian, oreForRobotGeode),
            MATERIAL.CLAY to clayForRobotObsidian,
            MATERIAL.OBSIDIAN to obsidianForRobotGeode,
            MATERIAL.GEODE to 0
        )

        val costs = mapOf(
            MATERIAL.ORE to RobotCost(oreForRobotOre, 0, 0, 0),
            MATERIAL.CLAY to RobotCost(oreForRobotClay, 0, 0, 0),
            MATERIAL.OBSIDIAN to RobotCost(oreForRobotObsidian, clayForRobotObsidian, 0, 0),
            MATERIAL.GEODE to RobotCost(oreForRobotGeode, 0, obsidianForRobotGeode, 0)
        )

        val queue: Queue<BluePrintExecution> = LinkedList()
        queue.add(BluePrintExecution(
            t,
            mapOf( MATERIAL.ORE to 0, MATERIAL.CLAY to 0, MATERIAL.OBSIDIAN to 0, MATERIAL.GEODE to 0 ),
            mapOf( MATERIAL.ORE to 1, MATERIAL.CLAY to 0, MATERIAL.OBSIDIAN to 0, MATERIAL.GEODE to 0 )
        ))

        val seen = mutableSetOf<BluePrintExecution>()

        var best = 0

        while (queue.isNotEmpty()) {
            val execution = queue.remove()
            val (executionTime, stuff, robots) = execution

            val minGeodeLeft = stuff[MATERIAL.GEODE]!! + (executionTime * robots[MATERIAL.GEODE]!!)
            if (minGeodeLeft > best) {
                best = minGeodeLeft
            }

            if (executionTime == 0 || execution in seen) {
                continue
            }

            seen.add(execution)

            for (resource in MATERIAL.values()) {
                val cost = costs[resource]!!

                if (resource != MATERIAL.GEODE && robots[resource]!! >= maxSpend[resource]!!) {
                    continue
                }

                if (cost.ore > 0 && robots[MATERIAL.ORE]!! == 0) continue
                if (cost.clay > 0 && robots[MATERIAL.CLAY]!! == 0) continue
                if (cost.obsidian > 0 && robots[MATERIAL.OBSIDIAN]!! == 0) continue
                if (cost.geode > 0 && robots[MATERIAL.GEODE]!! == 0) continue

                val wait = maxOf(
                    if (cost.ore > 0) ceil((cost.ore - stuff[MATERIAL.ORE]!!).toDouble() / robots[MATERIAL.ORE]!!.toDouble()).toInt() else 0,
                    if (cost.clay > 0) ceil((cost.clay - stuff[MATERIAL.CLAY]!!).toDouble() / robots[MATERIAL.CLAY]!!.toDouble()).toInt() else 0,
                    if (cost.obsidian > 0) ceil((cost.obsidian - stuff[MATERIAL.OBSIDIAN]!!).toDouble() / robots[MATERIAL.OBSIDIAN]!!.toDouble()).toInt() else 0,
                    if (cost.geode > 0) ceil((cost.geode - stuff[MATERIAL.GEODE]!!).toDouble() / robots[MATERIAL.GEODE]!!.toDouble()).toInt() else 0,
                    0 //avoid negative
                )

                if (executionTime - wait - 1 < 0) {
                    continue
                }

                val nextStuff = mapOf(
                    MATERIAL.ORE to stuff[MATERIAL.ORE]!! + (robots[MATERIAL.ORE]!! * (wait + 1)) - cost.ore,
                    MATERIAL.CLAY to stuff[MATERIAL.CLAY]!! + (robots[MATERIAL.CLAY]!! * (wait + 1)) - cost.clay,
                    MATERIAL.OBSIDIAN to stuff[MATERIAL.OBSIDIAN]!! + (robots[MATERIAL.OBSIDIAN]!! * (wait + 1)) - cost.obsidian,
                    MATERIAL.GEODE to stuff[MATERIAL.GEODE]!! + (robots[MATERIAL.GEODE]!! * (wait + 1)) - cost.geode
                )

                val nextRobots = mutableMapOf(
                    MATERIAL.ORE to robots[MATERIAL.ORE]!!,
                    MATERIAL.CLAY to robots[MATERIAL.CLAY]!!,
                    MATERIAL.OBSIDIAN to robots[MATERIAL.OBSIDIAN]!!,
                    MATERIAL.GEODE to robots[MATERIAL.GEODE]!!
                )

                nextRobots[resource] = nextRobots[resource]!! + 1

                queue.add(BluePrintExecution(executionTime - wait - 1, nextStuff, nextRobots))
            }

        }

        return best
    }

    data class  RobotCost(val ore: Int, val clay: Int, val obsidian: Int, val geode: Int)

    data class BluePrintExecution(val t: Int, val stuff: Map<MATERIAL, Int>, val robots: Map<MATERIAL, Int>)


    companion object {
        fun run(fileName: String) {
            val aoc = Aoc202219()
            File(fileName).forEachLine { line ->
                aoc + line
            }
            println( aoc.part1(24))
        }
    }

}