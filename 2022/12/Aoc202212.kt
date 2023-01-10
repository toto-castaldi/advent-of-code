import com.toto_castaldi.common.algo.Dijkstra
import com.toto_castaldi.common.structure.BidimentionalNode
import com.toto_castaldi.common.structure.Graph
import com.toto_castaldi.common.structure.IntCoordinates
import java.io.File

class Aoc202212 () {

    private val lines = mutableListOf<List<Char>>()

    val heightVal = { supply: Char -> supply.code - 'a'.code + 1 }

    data class XYH(val x: Int, val y: Int, val h : Int)

    fun run() {
        val heightmapInputChar = File("").readLines().map { line -> line.toCharArray().toList() }
        var startCoordinates = IntCoordinates(0, 0)
        var endCoordinates = IntCoordinates(0, 0)
        val heightmapInput = mutableListOf<MutableList<Int>>()
        for ((indexRow, row) in heightmapInputChar.withIndex()) {
            val rowInt = mutableListOf<Int>()
            for ((indexCol, element) in row.withIndex()) {
                when (element) {
                    'S' -> {
                        startCoordinates = IntCoordinates(indexCol, indexRow)
                        rowInt.add(heightVal('a'))
                    }
                    'E' -> {
                        endCoordinates = IntCoordinates(indexCol, indexRow)
                        rowInt.add(heightVal('z'))
                    }
                    else -> {
                        rowInt.add(heightVal(element))
                    }
                }
            }
            heightmapInput.add(rowInt)
        }
        val (lastNode, _) = BidimentionalNode.build(heightmapInput) { x,y,h -> XYH(x,y,h) }
        val firstNode = lastNode!!.topLeft()
        var destination : BidimentionalNode<XYH>? = null
        BidimentionalNode.printNodes(firstNode) {
            it.h.toString().padStart(3)
        }
        val allNodes = mutableListOf<BidimentionalNode<XYH>>()
        BidimentionalNode.navigate(firstNode) {
            allNodes.add(it)
            if (it.data.x == endCoordinates.x && it.data.y == endCoordinates.y) destination = it
        }

        for (node in allNodes) {
            val u = node.u()
            val d = node.d()
            val l = node.l()
            val r = node.r()
            if (u != null && u.data.h > node.data.h + 1) node.removeNeighbor(0, -1)
            if (d != null && d.data.h > node.data.h + 1) node.removeNeighbor(0, 1)
            if (l != null && l.data.h > node.data.h + 1) node.removeNeighbor(-1, 0)
            if (r != null && r.data.h > node.data.h + 1) node.removeNeighbor(1, 0)
            node
                .removeNeighbor(1, -1)
                .removeNeighbor(1, 1)
                .removeNeighbor(-1, 1)
                .removeNeighbor(-1, -1)
        }

        println(destination)
        println(startCoordinates)

        TODO()
    }

    operator fun plus(line: String) {
        lines.add(line.toList())
    }

    fun shortestPath(): Int {
        var start = XYH(0,0,0)
        var end = XYH(0,0,0)
        val graph = Graph<XYH>()

        val topLeft = BidimentionalNode.build(lines) {
            x, y, c ->

            val n = when (c) {
                'S' -> {
                    start = XYH(x, y, heightVal('a'))
                    start
                }
                'E' -> {
                    end = XYH(x, y, heightVal('z'))
                    end
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

        val dijkstra = Dijkstra<XYH>(graph)

        BidimentionalNode.navigate(topLeft) {
            n ->
            n.u() ?.let { other -> if (n.data.h + 1 >= other.data.h ) dijkstra.oneWay(n.data, other.data, 1)}
            n.d() ?.let { other -> if (n.data.h + 1 >= other.data.h ) dijkstra.oneWay(n.data, other.data, 1)}
            n.l() ?.let { other -> if (n.data.h + 1 >= other.data.h ) dijkstra.oneWay(n.data, other.data, 1)}
            n.r() ?.let { other -> if (n.data.h + 1 >= other.data.h ) dijkstra.oneWay(n.data, other.data, 1)}
        }

        println("${start.x} ${start.y}")
        println("${end.x} ${end.y}")

        return dijkstra.shortestFrom(start)[end]!!
    }

    fun part1(fileName: String) {
        val aoc = Aoc202212()
        File(fileName).forEachLine {
            aoc + it
        }
        println( aoc.shortestPath())
    }
}


