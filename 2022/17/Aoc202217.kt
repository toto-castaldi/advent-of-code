import com.toto_castaldi.common.structure.BidimensionalShape
import com.toto_castaldi.common.structure.Coordinates
import com.toto_castaldi.common.structure.PlacedBidimensionalShape
import java.io.File
import kotlin.math.max
import kotlin.math.min

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

    fun towerHeight(maxStackedPiecesCount: Int, debug : Int = -1): Int {
        val boundX = freeL .. freeR
        var currentPiece = nextPiece()
        var movement = nextMovement()
        var time = 0
        while (stackedPiecesCount < maxStackedPiecesCount && (debug == -1 || time < debug)) {
            debug(currentPiece)
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

            if (currentPiece.onTopOf(stack)) {
                stack + currentPiece
                stackedPiecesCount ++
                currentPiece = nextPiece()
            }
            movement = nextMovement()

        }
        return stack.shape.getHeight()
    }

    private fun debug(currentPiece: PlacedBidimensionalShape) {
        val minX = min(currentPiece.minX(), stack.minX())
        val maxX = max(currentPiece.maxX(), stack.maxX())
        val minY = min(currentPiece.minY(), stack.minY())
        val maxY = max(currentPiece.maxY(), stack.maxY())
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                if (Coordinates(x,y) in currentPiece) {
                    print("@")
                } else if (Coordinates(x,y) in stack) {
                    print("#")
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
        return PlacedBidimensionalShape(Coordinates(3, stack.minY() - 4), pieces[(pieceIndex ++) % pieces.size])
    }

    companion object {
        fun run1(fileName: String, maxStackedPiecesCount: Int) {
            println( Aoc202217(File(fileName).readLines().first()).towerHeight(maxStackedPiecesCount))
        }
    }

}