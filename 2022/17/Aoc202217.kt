import com.toto_castaldi.common.structure.BidimensionalShape
import com.toto_castaldi.common.structure.IntCoordinates
import com.toto_castaldi.common.structure.PlacedBidimensionalShape
import java.io.File
import java.lang.Math.max
import java.lang.Math.min

class Aoc202217(val movements: String) {
    private var cuttedStack: Long = 0
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

            val newStackShape = BidimensionalShape.base(stack.shape)

            cuttedStack += stack.shape.getHeight() - newStackShape.getHeight()

            stack =  PlacedBidimensionalShape(stack.anchorPoint, newStackShape )

            if (debug == 1) debugPrint(currentPiece)
        }
        return stack.shape.getHeight() - 1 + cuttedStack
    }


    private fun debugPrint(currentPiece: PlacedBidimensionalShape) {
        val minX = min(currentPiece.minX(), stack.minX())
        val maxX = max(currentPiece.maxX(), stack.maxX())
        val minY = min(currentPiece.minY(), stack.minY())
        val maxY = max(currentPiece.maxY(), stack.maxY())
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                if (IntCoordinates(x,y) in stack) {
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

    companion object {
        fun run(fileName: String, maxStackedPiecesCount: Long) {
            println( Aoc202217(File(fileName).readLines().first()).towerHeight(maxStackedPiecesCount))
        }
    }

}