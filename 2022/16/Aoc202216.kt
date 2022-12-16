import com.toto_castaldi.common.structure.Graph
import com.toto_castaldi.common.structure.Node
import java.io.File

class Aoc202216() {
    private val graph = Graph<Valve>()

    class Valve(var rate: Int = 0) {
        private var opened: Boolean = false
        var released : Int = 0

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
    }

    /**
     * incrementare il prima possibile il rilascio totale in un determinato lasso di tempo
     *
     * ad ogni passo guardo le opzioni migliori e le aggiungo al percorso
     *
     * il calcolo dell'opzione è ricorsivo tenendo a mente lo stato attuale e futuro
     */
    fun mostPressureInMinutes(nodeName: String, minutes: Int): Long {

        fun bestPath(node: Node<Valve>, passed: Int, debugLevel : Int): Path {
            var path = Path(passed, minutes)
            if (path.tickNode(node)) {
                if (path.tickValve(node.data)) {

                    val paths = mutableSetOf<Path>()
                    node.forEach {
                        paths.add(path.clone() + bestPath(it, path.passedTime, debugLevel + 1))
                    }

                    path = paths.maxBy { path.score() }
                }
            }
            return path
        }

        return bestPath(graph.getNode(nodeName)!!, 0, 0).released()
    }

    class Path(var passedTime: Int,val  maxTime: Int) {

        private var steps: String = ""

        fun tickNode(node: Node<Valve>): Boolean {
            steps += "-${node.name}"
            if (passedTime + 1 < maxTime) {
                passedTime += 1
                return true
            } else {
                return false
            }
        }

        fun tickValve(valve : Valve): Boolean {
            if (passedTime + valve.rate < maxTime) {
                passedTime += valve.open()
                return true
            } else {
                return false
            }
        }

        fun clone(): Path {
            TODO("Not yet implemented")
        }

        operator fun plus(bestPath: Path): Path {
            TODO("Not yet implemented")
        }

        fun score() : Int {
            TODO("Not yet implemented")
        }

        fun released(): Long {
            TODO()
        }

    }

    operator fun plus(rule: String) {
        //Valve QE has flow rate=3; tunnels lead to valves OU, ME, UX, AX, TW
        //Valve TN has flow rate=16; tunnels lead to valves UW, CG, WB
        val regFind = "Valve (\\w+) has flow rate=(-?\\d+); tunnel[s]? lead[s]? to valve[s]? ([\\w,+\\s]+)".toRegex().find(rule)!!
        val name = regFind.groups[1]!!.value
        val rate = regFind.groups[2]!!.value.toInt()
        val neighbors = regFind.groups[3]!!.value.trim().split(",").map { it.trim() }

        val node = graph.getOrCreateNode(name, Valve())
        neighbors.forEach {
            node.addNeighbor(graph.getOrCreateNode(it, Valve()))
        }
        node.data.setInitialRate(rate)


    }

    companion object {
        fun run1(fileName: String) {
            File(fileName).readLines().forEach { println(it) }
        }
    }

}