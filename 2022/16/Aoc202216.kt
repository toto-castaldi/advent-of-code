import com.toto_castaldi.common.structure.Graph
import com.toto_castaldi.common.structure.Node
import java.io.File

class Aoc202216() {
    private val graph = Graph<Valve>()

    class Valve(var rate: Int = 0) {
        internal var opened: Boolean = false

        fun setInitialRate(initialRate: Int) {
            rate = initialRate
        }

        /**
         * 1 se si PUO' aprire e non è già aperta
         * ritorna il tempo impiegato nell'operazione
         */
        fun open() : Int {
            if (!opened && rate > 0) {
                opened = true
                return 1
            }  else {
                return 0
            }
        }

        fun reset() {
            opened = false
        }

        fun releasing(): Int {
            return if (opened) rate else 0
        }
    }

    data class Path(var passedTime: Int,val  maxTime: Int, var steps : String = "") {

        fun tickNode(node: Node<Valve>): Boolean {
            if (passedTime < maxTime) {
                steps += "-${node.name}"
                passedTime += 1
                return true
            } else {
                return false
            }
        }

        fun tickValve(node : Node<Valve>): Boolean {
            if (passedTime < maxTime) {
                passedTime += node.data.open()
                return true
            } else {
                return false
            }
        }

        fun clone(): Path {
            return Path(passedTime, maxTime, steps)
        }

        operator fun plus(bestPath: Path): Path {
            steps += bestPath.steps
            passedTime = bestPath.passedTime
            return this
        }

        fun nodes(): List<String> {
            return steps.split("-").filter { it.isNotEmpty() }
        }

        companion object {
            fun score(path: Path, graph: Graph<Valve>, maxTime: Int) : Int {
                val nodes = mutableMapOf<String, Node<Valve>>()
                graph.forEach { it ->
                    it.data.reset()
                    nodes[it.name] = it
                }
                val nodeNames = path.nodes()
                var stepIndex = 0
                var time = 0
                var totalReleasing = 0

                val tickTotalReleasing = {
                    nodes.values.fold(0) {
                        acc, node ->

                        acc + node.data.releasing()
                    }
                }

                while (time < maxTime) {
                    val nodeName = nodeNames[stepIndex]
                    val node = nodes[nodeName]!!
                    time ++

                    if (time < maxTime) {
                        time += node.data.open()
                    }

                    totalReleasing += tickTotalReleasing()

                    if (stepIndex < nodeNames.size - 1) stepIndex ++

                }
                return totalReleasing
            }
        }

    }

    /**
     * incrementare il prima possibile il rilascio totale in un determinato lasso di tempo
     *
     * ad ogni passo guardo le opzioni migliori e le aggiungo al percorso
     *
     * il calcolo dell'opzione è ricorsivo tenendo a mente lo stato attuale e futuro
     */
    fun mostPressureInMinutes(nodeName: String, maxTime: Int): Int {
        return Path.score(bestPath(graph[nodeName]!!, 0, maxTime, 0), graph, maxTime)
    }

    private fun bestPath(node: Node<Valve>, passed: Int, maxTime: Int, debugLevel : Int): Path {
        var path = Path(passed, maxTime)
        if (path.tickNode(node)) {
            if (path.tickValve(node)) {

                val paths = mutableSetOf<Path>()
                node.forEach {
                    paths.add(path.clone() + bestPath(it, path.passedTime, maxTime,debugLevel + 1))
                }

                path = paths.maxBy { Path.score(path.clone(), graph, maxTime) }
            }
        }
        return path
    }


    fun computeBestPath(nodeName: String, maxTime: Int): Path {
        return bestPath(graph[nodeName]!!, 0, maxTime, 0)
    }

    operator fun plus(rule: String): Aoc202216 {
        val regFind = "Valve (\\w+) has flow rate=(-?\\d+); tunnel[s]? lead[s]? to valve[s]? ([\\w,+\\s]+)".toRegex().find(rule)!!
        val name = regFind.groups[1]!!.value
        val rate = regFind.groups[2]!!.value.toInt()
        val neighbors = regFind.groups[3]!!.value.trim().split(",").map { it.trim() }

        val node = graph.getOrCreateNode(name, Valve())
        neighbors.forEach {
            node.addNeighbor(graph.getOrCreateNode(it, Valve()))
        }
        node.data.setInitialRate(rate)

        return this
    }


    companion object {
        fun run1(fileName: String) {
            File(fileName).readLines().forEach { println(it) }
        }
    }

}