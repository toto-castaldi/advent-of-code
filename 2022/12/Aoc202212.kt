import com.toto_castaldi.common.algo.Dijkstra
import com.toto_castaldi.common.structure.BidimentionalNode
import com.toto_castaldi.common.structure.Graph
import java.io.File

class Aoc202212 () {

    private lateinit var topLeft: BidimentionalNode<XYH>
    private lateinit var dijkstra: Dijkstra<XYH>
    private lateinit var elementS: XYH
    private lateinit var elementE: XYH
    private val lines = mutableListOf<List<Char>>()

    val heightVal = { supply: Char -> supply.code - 'a'.code + 1 }

    data class XYH(val x: Int, val y: Int, val h : Int)

    operator fun plus(line: String) {
        lines.add(line.toList())
    }

    fun shortestPath(): Int {
        init()
        BidimentionalNode.navigate(topLeft) {
                from ->
            val valid = { f: XYH, d: XYH -> f.h + 1 >= d.h }
            from.u() ?.let { dest -> if (valid(from.data, dest.data)) dijkstra.edge(from.data, dest.data, 1)}
            from.d() ?.let { dest -> if (valid(from.data, dest.data)) dijkstra.edge(from.data, dest.data, 1)}
            from.l() ?.let { dest -> if (valid(from.data, dest.data)) dijkstra.edge(from.data, dest.data, 1)}
            from.r() ?.let { dest -> if (valid(from.data, dest.data)) dijkstra.edge(from.data, dest.data, 1)}
        }
        println("${elementS.x} ${elementS.y}")
        println("${elementE.x} ${elementE.y}")

        return dijkstra.shortestFrom(elementS)[elementE]!!
    }

    private fun init() {
        val graph = Graph<XYH>()
        topLeft = BidimentionalNode.build(lines) {
            x, y, c ->

            val n = when (c) {
                'S' -> {
                    elementS = XYH(x, y, heightVal('a'))
                    elementS
                }
                'E' -> {
                    elementE = XYH(x, y, heightVal('z'))
                    elementE
                }
                else -> {
                    XYH(x, y, heightVal(c))
                }
            }

            graph + n

            n
        }.node!!.topLeft()

        BidimentionalNode.printNodes(topLeft) {
            it.h.toString().padStart(3, ' ')
        }

        dijkstra = Dijkstra<XYH>(graph)


    }

    fun fewestShortestPath(): Int {
        init()

        BidimentionalNode.navigate(topLeft) {
                from ->
            val valid = { f: XYH, d: XYH -> f.h - d.h in (0 .. 1) || d.h - f.h > 1 }
            from.u() ?.let { dest -> if (valid(from.data, dest.data)) dijkstra.edge(from.data, dest.data, 1)}
            from.d() ?.let { dest -> if (valid(from.data, dest.data)) dijkstra.edge(from.data, dest.data, 1)}
            from.l() ?.let { dest -> if (valid(from.data, dest.data)) dijkstra.edge(from.data, dest.data, 1)}
            from.r() ?.let { dest -> if (valid(from.data, dest.data)) dijkstra.edge(from.data, dest.data, 1)}
        }

        println("${elementE.x} ${elementE.y}")
        println("${elementS.x} ${elementS.y}")

        val values = mutableListOf<Int>()

        for (v in dijkstra.shortestFrom(elementE).filter { entry ->
            entry.key.h == 1 && entry.value != null
        }.values) {
            values.add(v!!)
        }

        return values.min()
    }

    fun part1(fileName: String) {
        val aoc = Aoc202212()
        File(fileName).forEachLine {
            aoc + it
        }
        println( aoc.shortestPath())
    }

    fun part2(fileName: String) {
        val aoc = Aoc202212()
        File(fileName).forEachLine {
            aoc + it
        }
        println( aoc.fewestShortestPath())
    }

}


