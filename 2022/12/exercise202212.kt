import com.toto_castaldi.common.BidimentionalNode
import com.toto_castaldi.common.Coordinates
import com.toto_castaldi.common.Dijkstra
import java.io.File

fun main(
    args: Array<String>
) {
    test()

    val heightVal = { supply: Char -> supply.code - 'a'.code + 1 }

    val heightmapInputChar = File(args[0]).readLines().map { line -> line.toCharArray().toList() }
    var startCoordinates = Coordinates(0,0)
    var endCoordinates = Coordinates(0,0)
    val heightmapInput = mutableListOf<MutableList<Int>>()
    for ((indexRow, row) in heightmapInputChar.withIndex()) {
        val rowInt = mutableListOf<Int>()
        for ((indexCol, element) in row.withIndex() ) {
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
    val (node, countNodes) = BidimentionalNode.build(heightmapInput) { it }
    val heightmap = node!!.topLeft()
    BidimentionalNode.printNodes(heightmap) {
        it.toString().padStart(3)
    }
    val width = heightmapInputChar[0].size
    val startIndex = startCoordinates.linearIndex(width)
    val endIndex = endCoordinates.linearIndex(width)


    val dijkstra = Dijkstra<Int>()
    var count = 0
    val debugMap = heightmapInputChar.fold(mutableListOf<Char>(), { acc, value ->
        acc.addAll(value)
        acc
    })
    BidimentionalNode.navigate(heightmap,{
        val u = it.u()
        val d = it.d()
        val l = it.l()
        val r = it.r()
        if (u != null && (u.data - it.data) <= 1) dijkstra.arc(count, count - width)
        if (d != null && (d.data - it.data) <= 1) dijkstra.arc(count, count + width)
        if (l != null && (l.data - it.data) <= 1) dijkstra.arc(count, count - 1)
        if (r != null && (r.data - it.data) <= 1) dijkstra.arc(count, count + 1)
        count ++
    }, {})
    val dijkstraCompute = dijkstra.shortestPath(startIndex,endIndex)
    println(debugMap[startIndex])
    println(debugMap[endIndex])
    println(dijkstraCompute)
    println(dijkstraCompute.size - 1)
}


private fun test() {
    assert(42 == 42)
}


