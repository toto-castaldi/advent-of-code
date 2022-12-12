import com.toto_castaldi.common.BidimentionalNode
import com.toto_castaldi.common.Coordinates
import java.io.File

operator fun <T> List<T>.component6(): T = get(5)
operator fun <T> List<T>.component7(): T = get(6)
operator fun <T> List<T>.component8(): T = get(7)
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

    var lowestPath = ""

    fun recMove (prevSteps: String, coordinates: Coordinates )   {
        val moveLabel : (Coordinates) -> String = { it -> "-${it.x.toString().padStart(2)}:${it.y.toString().padStart(2)}"}
        val currentNode = heightmap.resolve(coordinates)!!
        var currentSteps = prevSteps + moveLabel(coordinates)
        if (currentSteps.endsWith(moveLabel(endCoordinates))) {
            println("found path ${currentSteps}")
            if (lowestPath == "" || currentSteps.length < lowestPath.length) {
                lowestPath = currentSteps
            }
        } else {
            val (up, _, right, _, down, _, left, _) = heightmap.resolve(coordinates)!!.edges()
            fun moveIfCorrectAndNew  (newNode : BidimentionalNode<Int>?, newCoordinates: Coordinates) {
                if (
                    newNode != null &&
                    newNode!!.data - currentNode.data <= 1 &&
                    //!currentSteps.contains(moveLabel(newCoordinates)+moveLabel(coordinates))
                    !currentSteps.contains(moveLabel(newCoordinates))
                ) {
                    recMove(currentSteps, newCoordinates)
                }
            }
            moveIfCorrectAndNew(up, coordinates.clone().move(0, -1))
            moveIfCorrectAndNew(down, coordinates.clone().move(0, 1))
            moveIfCorrectAndNew(left, coordinates.clone().move(-1, 0))
            moveIfCorrectAndNew(right, coordinates.clone().move(1, 0))
        }
    }

    recMove("", startCoordinates)
    println(lowestPath)
    println(lowestPath.split("-").size - 2)

}


private fun test() {
    assert(42 == 42)
}


