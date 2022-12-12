import com.toto_castaldi.common.BidimentionalNode
import com.toto_castaldi.common.Coordinates
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
    val heightmap = BidimentionalNode.build(heightmapInput) { it }.node!!.topLeft()
    BidimentionalNode.printNodes(heightmap) {
        it.toString().padStart(3)
    }
    val startNode = heightmap.resolve(startCoordinates)
    val endNode = heightmap.resolve(endCoordinates)

    println(startNode)
    println(endNode)
}

private fun test() {
    assert(42 == 42)
}


