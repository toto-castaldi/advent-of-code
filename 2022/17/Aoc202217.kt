import com.toto_castaldi.common.structure.BidimensionalShape
import com.toto_castaldi.common.structure.IntCoordinates
import com.toto_castaldi.common.structure.PlacedBidimensionalShape
import java.io.File
import kotlin.math.max
import kotlin.math.min

class Aoc202217(val movements: String) {

    private var pieceIndex = 0
    private var movIndex = 0
    private val base = BidimensionalShape(arrayOf("#######"))
    private var stack = PlacedBidimensionalShape(IntCoordinates(1, 4), base)
    private var stackedPiecesCount: Long = 0
    private val freeR = 7
    private val freeL = 1
    
    val pieces = listOf<BidimensionalShape>(
        BidimensionalShape(arrayOf("####")),
        BidimensionalShape(arrayOf(
            ".#.",
            "###",
            ".#."
        )),
        BidimensionalShape(arrayOf(
            "..#",
            "..#",
            "###"
        )),
        BidimensionalShape(arrayOf(
            "#",
            "#",
            "#",
            "#"
        )),
        BidimensionalShape(arrayOf(
            "##",
            "##"
        ))
    )

    fun towerHeight(maxStackedPiecesCount: Long, debug : Int = -1): Long {
        val boundX = freeL .. freeR
        var currentPiece = nextPiece()
        var movement = nextMovement()
        var time = 0
        while (stackedPiecesCount < maxStackedPiecesCount && (debug == -1 || time < debug)) {

            time ++

            val boundY = Int.MIN_VALUE .. stack.maxY()
            when (movement) {
                '>' -> currentPiece.moveInBounderies(1, 0, boundX, boundY )
                '<' -> currentPiece.moveInBounderies(-1, 0, boundX, boundY)
            }
            if (currentPiece.intersect(stack)) { //illegal move. Go back
                when (movement) {
                    '<' -> currentPiece.move(1, 0)
                    '>' -> currentPiece.move(-1, 0)
                }
            }

            currentPiece.moveInBounderies(0, 1 , boundX, boundY)


            if (currentPiece.intersect(stack)) { //move back and sum to stack
                currentPiece.move(0,-1)

                stack + currentPiece
                stackedPiecesCount ++
                currentPiece = nextPiece()
            }
            movement = nextMovement()

            if (debug == 1) debugPrint(currentPiece)
        }
        //debugPrint(currentPiece)
        return stack.shape.getHeight() - 1L
    }


    private fun debugPrint(currentPiece: PlacedBidimensionalShape, includeStack: Boolean = true) {
        val minX = if (includeStack) min(currentPiece.minX(), stack.minX()) else currentPiece.minX()
        val maxX = if (includeStack) max(currentPiece.maxX(), stack.maxX()) else currentPiece.maxX()
        val minY = if (includeStack) min(currentPiece.minY(), stack.minY()) else currentPiece.minY()
        val maxY = if (includeStack) max(currentPiece.maxY(), stack.maxY()) else currentPiece.maxY()
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                if (IntCoordinates(x,y) in stack && includeStack) {
                    print("#")
                } else if (IntCoordinates(x,y) in currentPiece) {
                    print("@")
                } else print(".")
            }
            println()
        }
        println()
    }


    private fun nextMovement(): Char {
        return movements[(movIndex ++) % movements.length]
    }

    private fun nextPiece(): PlacedBidimensionalShape {
        val nextShape = pieces[(pieceIndex++) % pieces.size]
        return PlacedBidimensionalShape(IntCoordinates(3, stack.minY() - 3 - nextShape.getHeight()), nextShape)
    }

    fun findPattern(moves: Long) {
        towerHeight(moves)

        var bottomY = stack.maxY()

        val stackHeight = stack.shape.getHeight()

        var foundPattern = false

        while (!foundPattern) {
            val patternHeight = 1

            while (!foundPattern) {
                val subStack = stack.subFromBottom(bottomY, patternHeight)
                var y = 1

                while (!foundPattern && y <= (stackHeight - patternHeight)) {
                    val matchSub = stack.subFromBottom(bottomY - y, patternHeight)

                    if (subStack == matchSub) {
                        foundPattern = checkPattern(bottomY, bottomY - y)
                    }

                    y++
                }

                bottomY--
            }

        }


    }

    private fun checkPattern(lastY: Int, firstY: Int): Boolean {
        if (lastY - firstY > 1) { //no adijacent lines
            val patternHeight = lastY - firstY
            val a = stack.subFromBottom(firstY, patternHeight)
            val b = stack.subFromBottom(lastY, patternHeight)
            if  (a == b) {
                BidimensionalShape.print(b)
                return true
            }
        }
        return false
    }

    companion object {
        fun run(fileName: String, maxStackedPiecesCount: Long) {
            println( Aoc202217(File(fileName).readLines().first()).towerHeight(maxStackedPiecesCount))
        }
    }

}