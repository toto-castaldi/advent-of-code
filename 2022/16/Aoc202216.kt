import com.toto_castaldi.common.structure.Graph
import java.io.File

class Aoc202216() {
    private val nodes = mutableMapOf<String, Graph<Valve>>()

    class Valve(var rate: Int = 0) {
        fun setInitialRate(initialRate: Int) {
            rate = initialRate
        }

    }

    /**
     * open as many valves as possible, preferring those with the most max rate
     *
     * incrementare il prima possibile il rilascio totale in un determinato lasso di tempo
     *
     * ad ogni passo guardo le opzioni migliori e le aggiungo al percorso
     *
     * il calcolo dell'opzione è ricorsivo tenendo a mente lo stato attuale e futuro
     */
    fun mostPressureInMinutes(minutes: Int): Long {
        TODO()
    }

    operator fun plus(rule: String) {
        //Valve QE has flow rate=3; tunnels lead to valves OU, ME, UX, AX, TW
        //Valve TN has flow rate=16; tunnels lead to valves UW, CG, WB
        val regFind = "Valve (\\w+) has flow rate=(-?\\d+); tunnels lead to valves ([\\w,\\s]+)".toRegex().find(rule)!!
        val name = regFind.groups[1]!!.value
        val rate = regFind.groups[2]!!.value.toInt()
        val neighbs = regFind.groups[3]!!.value.trim().split(",").map { it.trim() }
        val createNode = {
            nodeName : String ->
            nodes[nodeName] ?:run {
                val result = Graph(Valve())
                nodes[nodeName] = result
                result
            }
        }
        val node = createNode(name)
        node.data.setInitialRate(rate)

        neighbs.forEach {
            node.addNeighbor(createNode(it))
        }

    }

    companion object {
        fun run1(fileName: String) {
            File(fileName).readLines().forEach { println(it) }
        }
    }

}