import com.toto_castaldi.common.structure.BidimentionalNode
import com.toto_castaldi.common.structure.Coordinates
import com.toto_castaldi.common.algo.DiscreteHillClimbing
import com.toto_castaldi.common.algo.EuclidianDistance
import java.io.File

class Aoc202212 (private val fileName:String) {

    data class XYH(val x: Int, val y: Int, val h : Int)

    fun run() {
        val heightVal = { supply: Char -> supply.code - 'a'.code + 1 }

        val heightmapInputChar = File(fileName).readLines().map { line -> line.toCharArray().toList() }
        var startCoordinates = Coordinates(0, 0)
        var endCoordinates = Coordinates(0, 0)
        val heightmapInput = mutableListOf<MutableList<Int>>()
        for ((indexRow, row) in heightmapInputChar.withIndex()) {
            val rowInt = mutableListOf<Int>()
            for ((indexCol, element) in row.withIndex()) {
                if (element == 'S') {
                    startCoordinates = Coordinates(indexCol, indexRow)
                    rowInt.add(heightVal('a'))
                } else if (element == 'E') {
                    endCoordinates = Coordinates(indexCol, indexRow)
                    rowInt.add(heightVal('z'))
                } else {
                    rowInt.add(heightVal(element))
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

        val maxDistance = EuclidianDistance.distance2D(
            endCoordinates.x.toDouble(), endCoordinates.y.toDouble(),
            startCoordinates.x.toDouble(), startCoordinates.y.toDouble()
        )

        val eval: (BidimentionalNode<XYH>) -> Double = {
            maxDistance - EuclidianDistance.distance2D(
                destination!!.data.x.toDouble(), destination!!.data.y.toDouble(),
                it.data.x.toDouble(), it.data.y.toDouble()
            )
        }
        val path = DiscreteHillClimbing(firstNode).compute(destination!!, eval)

        for (p in path) {
            println(p)
        }
        println(path.size)


    }

}


