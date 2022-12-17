import com.toto_castaldi.common.structure.BidimensionalShape
import com.toto_castaldi.common.structure.Coordinates
import com.toto_castaldi.common.structure.PlacedBidimensionalShape
import java.io.File

class Aoc202217(val movements: String) {
    private var pieceIndex = 0
    private var movIndex = 0
    private val base = BidimensionalShape(arrayOf("#######"))
    private var stack = PlacedBidimensionalShape(Coordinates(1, 4), base)
    private var stackedPiecesCount: Int = 0
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
            "#"
        )),
        BidimensionalShape(arrayOf(
            "##",
            "##"
        ))
    )

    fun towerHeight(maxStackedPiecesCount: Int): Int {
        val boundX = freeL .. freeR
        var currentPiece = nextPiece()
        var movement = nextMovement()
        while (stackedPiecesCount < maxStackedPiecesCount) {
            val boundY = Int.MIN_VALUE .. stack.maxY()
            when (movement) {
                '>' -> currentPiece.moveInBounderies(1, 0, boundX, boundY )
                '<' -> currentPiece.moveInBounderies(-1, 0, boundX, boundY)
            }
            if (currentPiece.intesects(stack)) { //illegal move. Go back
                when (movement) {
                    '<' -> currentPiece.move(1, 0)
                    '>' -> currentPiece.move(-1, 0)
                }
            }

            if (currentPiece.onTopOf(stack)) {
                stack + currentPiece
                stackedPiecesCount ++
                currentPiece = nextPiece()
            } else {
                currentPiece.moveInBounderies(0, 1 , boundX, boundY)
            }
            movement = nextMovement()
        }
        return stack.shape.getHeight()
    }

    private fun nextMovement(): Char {
        return movements[movIndex ++]
    }

    private fun nextPiece(): PlacedBidimensionalShape {
        return PlacedBidimensionalShape(Coordinates(2, stack.minY() - 4), pieces[(pieceIndex ++) % pieces.size])
    }

    companion object {
        fun run1(fileName: String, maxStackedPiecesCount: Int) {
            println( Aoc202217(File(fileName).readLines().first()).towerHeight(maxStackedPiecesCount))
        }
    }

}