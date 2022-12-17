import com.toto_castaldi.common.structure.BidimensionalShape
import com.toto_castaldi.common.structure.Coordinates
import com.toto_castaldi.common.structure.PlacedBidimensionalShape
import java.io.File

class Aoc202217(movements: String) {
    private var stack: PlacedBidimensionalShape = PlacedBidimensionalShape(Coordinates(3, 0), BidimensionalShape.EMPTY)
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
            val boundY = Int.MIN_VALUE .. (if (stack.maxY() > 0) stack.maxY()  else 3)
            when (movement) {
                '>' -> currentPiece.moveInBounderies(1, 0, boundX, boundY )
                '<' -> currentPiece.moveInBounderies(-1, 0, boundX, boundY)
            }
            currentPiece.moveInBounderies(0, 1 , boundX, boundY)
            if (currentPiece.touch(stack)) {
                stack + currentPiece
                stackedPiecesCount ++
                currentPiece = nextPiece()
            }
            movement = nextMovement()
        }
        return stack.shape.height
    }

    private fun nextMovement(): Char {
        TODO("Not yet implemented")
    }

    private fun nextPiece(): PlacedBidimensionalShape {
        TODO("Not yet implemented")
    }

    companion object {
        fun run1(fileName: String, maxStackedPiecesCount: Int) {
            println( Aoc202217(File(fileName).readLines().first()).towerHeight(maxStackedPiecesCount))
        }
    }

}