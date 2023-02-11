class Aoc202219() {

    private val bluePrints = mutableSetOf<BluePrint>()
    private val executions = mutableMapOf<Int, BluePrintExecution>()
    private val cache = mutableMapOf<String, Int>()

    enum class MATERIAL {
        ORE, CLAY, OBSIDIAN, GEODE
    }

    private fun maxGeodes(minutes: Int, execution: BluePrintExecution, bluePrint: BluePrint, shouldBuildRobot : MATERIAL? = null): Int {
        if (minutes == 0) {
            return 0
        }

        val cacheKey = cacheKey(minutes, execution, bluePrint.id, shouldBuildRobot)
        if (cacheKey in cache.keys) return cache[cacheKey]!!


        execution.robotsProduction()

        if (shouldBuildRobot != null && execution.canBuild(shouldBuildRobot, bluePrint)) {
            execution.build(shouldBuildRobot, bluePrint)
        }

        val scoresMaterial = mutableMapOf<MATERIAL, Int>()
        for (material in MATERIAL.values()) {
            scoresMaterial[material] = execution.productionOf(MATERIAL.GEODE) + maxGeodes(minutes - 1, execution.bake(), bluePrint, material)
        }

        val result = scoresMaterial.values.max()
        cache[cacheKey] = result

        return result
    }

    private fun cacheKey(
        minutes: Int,
        execution: BluePrintExecution,
        id: Int,
        shouldBuildRobot: MATERIAL?
    ): String {
        return "$minutes-$execution-$id-$shouldBuildRobot"
    }

    fun qualityLevels(minutes : Int): Map<Int, Int> {
        val result = mutableMapOf<Int, Int>()
        for (entry in executions) {
            val id = entry.key
            val execution = entry.value
            val bluePrint = bluePrints.find { bluePrint -> bluePrint.id == id }!!

            val maxGeodes = maxGeodes(minutes, execution, bluePrint)

            result[id] = maxGeodes
        }
        return result
    }

    operator fun plus(bluePrint: String) {
        val bpIdRegex = "Blueprint (\\d+):".toRegex().find(bluePrint)!!
        val id = bpIdRegex.groups[1]!!.value.toInt()
        val robots = bluePrint.split(":")[1].trim()
        val oreForRobotOre = robots.split("Each ore robot costs ")[1].split("ore")[0].trim().toInt()
        val oreForRobotClay = robots.split("Each clay robot costs ")[1].split("ore")[0].trim().toInt()
        val oreForRobotObsidian = robots.split("Each obsidian robot costs ")[1].split("ore")[0].trim().toInt()
        val clayForRobotObsidian = robots.split("Each obsidian robot costs $oreForRobotObsidian ore and ")[1].split("clay")[0].trim().toInt()
        val oreForRobotGeode = robots.split("Each geode robot costs ")[1].split("ore")[0].trim().toInt()
        val obsidianForRobotGeode = robots.split("Each geode robot costs $oreForRobotGeode ore and ")[1].split("obsidian")[0].trim().toInt()

        val bluePrint = BluePrint(id) + RobotOre(oreForRobotOre) + RobotClay(oreForRobotClay) + RobotObsidian(oreForRobotObsidian, clayForRobotObsidian) + RobotGeode(oreForRobotGeode, obsidianForRobotGeode)
        executions[id] = BluePrintExecution() + bluePrint.getRobot(MATERIAL.ORE)
        bluePrints.add(bluePrint)
    }

    class BluePrintExecution {
        private val robots = mapOf(MATERIAL.CLAY to mutableListOf<Robot>(), MATERIAL.OBSIDIAN to mutableListOf<Robot>(), MATERIAL.GEODE to mutableListOf<Robot>(), MATERIAL.ORE to mutableListOf<Robot>())
        private val materials = mutableMapOf(MATERIAL.CLAY to 0, MATERIAL.OBSIDIAN to 0, MATERIAL.GEODE to 0, MATERIAL.ORE to 0)
        operator fun plus(robot: Robot): BluePrintExecution {
            val m = robot.producing()
            robots[m]!!.add(robot)
            return this
        }

        fun productionOf(material: MATERIAL) : Int {
            return materials[material]!!
        }

        fun robotsProduction() {
            for (pr in robots) {
                val producing = pr.key
                materials[producing] = materials[producing]!! + pr.value.size
            }
        }

        fun canBuild(material: MATERIAL, bluePrint: BluePrint): Boolean {
            val robot = bluePrint.getRobot(material)
            return robot.canBuild(materials)
        }

        fun build(material: MATERIAL, bluePrint: BluePrint) {
            val robot = bluePrint.getRobot(material)
            robot.build(materials)
            this + robot
        }

        fun bake(): BluePrintExecution {
            val result = BluePrintExecution()
            for (robot in robots) {
                for (r in robot.value) {
                    result + r
                }
            }
            for (e in materials) {
                result.materials[e.key] = e.value
            }
            return result
        }

        override fun toString(): String {
            var result = "R{"
            for (m in MATERIAL.values()) {
                result += "$m->${robots[m]!!.size},"
            }
            return "$result} M$materials"
        }
    }

    class RobotClay(private val ore: Int) : Robot{
        override fun producing(): MATERIAL {
            return MATERIAL.CLAY
        }

        override fun canBuild(materials: MutableMap<MATERIAL, Int>): Boolean {
            return materials[MATERIAL.ORE]!! >= ore
        }

        override fun build(materials: MutableMap<MATERIAL, Int>) {
            materials[MATERIAL.ORE] = materials[MATERIAL.ORE]!! - ore
        }

        override fun bake(): Robot {
            return RobotClay(ore)
        }

    }

    class RobotOre(private val ore: Int): Robot {
        override fun producing(): MATERIAL {
            return MATERIAL.ORE
        }

        override fun canBuild(materials: MutableMap<MATERIAL, Int>): Boolean {
            return materials[MATERIAL.ORE]!! >= ore
        }

        override fun build(materials: MutableMap<MATERIAL, Int>) {
            materials[MATERIAL.ORE] = materials[MATERIAL.ORE]!! - ore
        }

        override fun bake(): Robot {
            return RobotOre(ore)
        }

    }

    class RobotObsidian(private val ore: Int, private val clay : Int): Robot {
        override fun producing(): MATERIAL {
            return MATERIAL.OBSIDIAN
        }

        override fun canBuild(materials: MutableMap<MATERIAL, Int>): Boolean {
            return materials[MATERIAL.ORE]!! >= ore && materials[MATERIAL.CLAY]!! >= clay
        }

        override fun build(materials: MutableMap<MATERIAL, Int>) {
            materials[MATERIAL.ORE] = materials[MATERIAL.ORE]!! - ore
            materials[MATERIAL.CLAY] = materials[MATERIAL.CLAY]!! - clay
        }

        override fun bake(): Robot {
            return RobotObsidian(ore, clay)
        }

    }

    class RobotGeode(private val ore: Int, private val obsidian : Int): Robot {
        override fun producing(): MATERIAL {
            return MATERIAL.GEODE
        }

        override fun canBuild(materials: MutableMap<MATERIAL, Int>): Boolean {
            return materials[MATERIAL.ORE]!! >= ore && materials[MATERIAL.OBSIDIAN]!! >= obsidian
        }

        override fun build(materials: MutableMap<MATERIAL, Int>) {
            materials[MATERIAL.ORE] = materials[MATERIAL.ORE]!! - ore
            materials[MATERIAL.OBSIDIAN] = materials[MATERIAL.OBSIDIAN]!! - obsidian
        }

        override fun bake(): Robot {
            return RobotGeode(ore, obsidian)
        }

    }

    interface Robot {
        fun producing(): MATERIAL
        fun canBuild(materials: MutableMap<MATERIAL, Int>): Boolean
        fun build(materials: MutableMap<MATERIAL, Int>)
        fun bake(): Robot

    }

    class BluePrint(val id : Int) {
        private val robots = mutableMapOf<MATERIAL, Robot>()

        operator fun plus(robot: Robot): BluePrint {
            robots[robot.producing()] = robot
            return this
        }

        fun getRobot(m: MATERIAL): Robot {
            return robots[m]!!
        }

    }

    companion object {
        fun run(fileName: String) {
            println( fileName)
        }
    }

}