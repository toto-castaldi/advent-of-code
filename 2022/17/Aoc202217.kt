import com.toto_castaldi.common.structure.BidimensionalShape
import com.toto_castaldi.common.structure.IntCoordinates
import com.toto_castaldi.common.structure.PlacedBidimensionalShape
import java.io.File
import kotlin.math.max
import kotlin.math.min

class Aoc202217(val movements: String) {

    private var piecesForBase: Long = 0L
    private var piecesForPattern: Long = 0L
    private var patternShape: BidimensionalShape? = null
    private var pieceIndex = 0
    private var movIndex = 0
    private val base = BidimensionalShape(arrayOf("#######"))
    private var stack = PlacedBidimensionalShape(IntCoordinates(1, 4), base)
    private val freeR = 7
    private val freeL = 1
    private var piecesLog = mutableMapOf<Int, Long>()
    
    private val pieces = listOf<BidimensionalShape>(
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

    fun towerHeight(maxStackedPiecesCountInput: Long, debug : Int = -1): Long {
        piecesLog = mutableMapOf<Int, Long>()
        stack = PlacedBidimensionalShape(IntCoordinates(1, 4), base)
        var maxStackedPiecesCount = maxStackedPiecesCountInput
        var optimizationCount = 0L

        if (piecesForPattern != 0L && piecesForBase != 0L) {
            println("$piecesForPattern $piecesForBase $maxStackedPiecesCount")
            optimizationCount = (maxStackedPiecesCount - piecesForBase) / piecesForPattern
            println(optimizationCount)
            maxStackedPiecesCount -= optimizationCount * piecesForPattern
            println("optimized is $maxStackedPiecesCount")
        }

        val boundX = freeL .. freeR
        var currentPiece = nextPiece()
        var movement = nextMovement()
        var time = 0
        var stackedPiecesCount = 0L
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

                piecesLog[stack.shape.getHeight()] = stackedPiecesCount
            }
            movement = nextMovement()

            if (debug == 1) debugPrint(currentPiece)
        }
        //debugPrint(currentPiece)
        if (optimizationCount != 0L) {
            println("current shape height ${stack.shape.getHeight()}")
            println("optimizationCount ${optimizationCount}")
            println("patternShape shape height ${patternShape!!.getHeight()}")
            return stack.shape.getHeight() - 1L + optimizationCount * patternShape!!.getHeight()
        } else {
            return stack.shape.getHeight() - 1L
        }
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

    fun patternInfo(moves: Long) {
        towerHeight(moves)

        var bottomY = stack.maxY()

        var stackHeight = stack.shape.getHeight()

        var foundPattern = false

        while (!foundPattern) {

            while (!foundPattern) {
                val subStack = stack.subFromBottom(bottomY, 1)
                var y = 1

                while (!foundPattern && y <= (stackHeight - 1)) {
                    val matchSub = stack.subFromBottom(bottomY - y, 1)

                    if (subStack == matchSub) {
                        foundPattern = checkPattern(bottomY, bottomY - y)
                        if (foundPattern) {
                            patternShape = stack.subFromBottom(bottomY, y)
                            stackHeight = stack.shape.getHeight()
                            val patternHeight = patternShape!!.getHeight()
                            val baseHeight = stack.maxY() - bottomY

                            //println()
                            //BidimensionalShape.print(stack.subFromBottom(stack.maxY(), baseHeight + 1))

                            println("patternHeight $patternHeight baseHeight $baseHeight")

                            piecesForBase = piecesLog[baseHeight]!!
                            piecesForPattern = piecesLog[patternHeight + baseHeight]!! - piecesForBase
                        }
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
                //BidimensionalShape.print(b)
                return true
            }
        }
        return false
    }

    companion object {
        fun run1(fileName: String, maxStackedPiecesCount: Long) {
            println( Aoc202217(File(fileName).readLines().first()).towerHeight(maxStackedPiecesCount))
        }

        fun run2(fileName: String, maxStackedPiecesCount: Long) {
            val aoc = Aoc202217(File(fileName).readLines().first())
            aoc.patternInfo(5000)
            println(aoc.towerHeight(maxStackedPiecesCount))
        }
    }

}