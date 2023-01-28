class Aoc202219() {

    private val bluePrints = mutableMapOf<Int, BluePrint>()
    private val executions = mutableMapOf<Int, BluePrintExecution>()

    enum class MATERIAL {
        ORE, CLAY, OBSIDIAN, GEODE
    }

    fun qualityLevels(minutes : Int): Map<Int, Int> {
        for (m in 0 until minutes) {
            for (exId in executions) {
                val id = exId.key
                val e = exId.value
                val b = bluePrints[id]!!

                if (e.canBuild(MATERIAL.GEODE, b)) e.build(MATERIAL.GEODE, b)
                else if (e.canBuild(MATERIAL.OBSIDIAN, b)) e.build(MATERIAL.OBSIDIAN, b)
                else if (e.canBuild(MATERIAL.CLAY, b)) e.build(MATERIAL.CLAY, b)
                else if (e.canBuild(MATERIAL.ORE, b)) e.build(MATERIAL.ORE, b)

                e.robotsProduction()
            }
        }
        return executions.mapValues {
            entry ->
            entry.value.productionOf(MATERIAL.GEODE)
        }
    }

    operator fun plus(bluePrint: String) {
        val bpIdRegex = "Blueprint (\\d+):".toRegex().find(bluePrint)!!
        val id = bpIdRegex.groups[1]!!.value.toInt()
        val robots = bluePrint.split(":")[1].trim()
        val oreForRobotOre = robots.split("Each ore robot costs ")[1].split("ore")[0].trim().toInt()
        val oreForRobotClay = robots.split("Each clay robot costs ")[1].split("ore")[0].trim().toInt()
        val oreForRobotObsidian = robots.split("Each obsidian robot costs ")[1].split("ore")[0].trim().toInt()
        val clayForRobotObsidian = robots.split("Each obsidian robot costs ${oreForRobotObsidian} ore and ")[1].split("clay")[0].trim().toInt()
        val oreForRobotGeode = robots.split("Each geode robot costs ")[1].split("ore")[0].trim().toInt()
        val obsidianForRobotGeode = robots.split("Each geode robot costs ${oreForRobotGeode} ore and ")[1].split("obsidian")[0].trim().toInt()

        bluePrints[id] = BluePrint() + RobotOre(oreForRobotOre) + RobotClay(oreForRobotClay) + RobotObsidian(oreForRobotObsidian, clayForRobotObsidian) + RobotGeode(oreForRobotGeode, obsidianForRobotGeode)
        executions[id] = BluePrintExecution() + bluePrints[id]!!.getRobots(MATERIAL.ORE)
    }

    class BluePrintExecution {
        private val robots = mutableListOf<Robot>()
        private val materials = mutableMapOf(MATERIAL.CLAY to 0, MATERIAL.OBSIDIAN to 0, MATERIAL.GEODE to 0, MATERIAL.ORE to 0)
        operator fun plus(robot: Robot): BluePrintExecution {
            robots.add(robot)
            return this
        }

        fun productionOf(material: MATERIAL) : Int {
            return materials[material]!!
        }

        fun robotsProduction() {
            for (r in robots) {
                val producing = r.producing()
                materials[producing] = materials[producing]!! + 1
            }
        }

        fun canBuild(material: MATERIAL, bluePrint: BluePrint): Boolean {
            val robot = bluePrint.getRobots(material)
            return robot.canBuild(materials)
        }

        fun build(material: MATERIAL, bluePrint: BluePrint) {
            val robot = bluePrint.getRobots(material)
            robot.build(materials)
            robots.add(robot.bake())
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
            materials[MATERIAL.CLAY] = materials[MATERIAL.CLAY]!! - ore
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
            materials[MATERIAL.OBSIDIAN] = materials[MATERIAL.OBSIDIAN]!! - ore
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

    class BluePrint {
        private val robots = mutableMapOf<MATERIAL, Robot>()

        operator fun plus(robot: Robot): BluePrint {
            robots[robot.producing()] = robot
            return this
        }

        fun getRobots(m: MATERIAL): Robot {
            return robots[m]!!.bake()
        }

    }

    companion object {
        fun run(fileName: String) {
            println( fileName)
        }
    }

}