import com.toto_castaldi.common.structure.BidimensionalShape
import com.toto_castaldi.common.structure.Coordinates
import com.toto_castaldi.common.structure.PlacedBidimensionalShape
import java.io.File

class Aoc202217(movements: String) {
    private var stack: PlacedBidimensionalShape = PlacedBidimensionalShape(Coordinates(3, 0), BidimensionalShape.EMPTY)
    private var stackedPiecesCount: Int = 0
    
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
        var currentPiece = nextPiece()
        var movement = nextMovement()
        while (stackedPiecesCount < maxStackedPiecesCount) {
            when (movement) {
                '>' -> currentPiece.move(1, 0)
                '<' -> currentPiece.move(-1, 0)
            }
            if (currentPiece.touch(stack)) {
                stack + currentPiece
                stackedPiecesCount ++
                currentPiece = nextPiece()
            }
            movement = nextMovement()
        }
        return stack.height()
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