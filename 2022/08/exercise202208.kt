import java.io.File
import com.toto_castaldi.common.BidimentionalNode

fun BidimentionalNode<Int>.scenicScore(): Int {
    if (resolve(0, -1) == null ||
        resolve(-1, 0) == null ||
        resolve(1, 0) == null ||
        resolve(0, 1) == null
    ) {
        return 0
    } else {
        val viewDistance : (Int, Int) -> Int = {
                stepX, stepY ->

            //stop if you reach an edge or at the first tree that is the same height or taller
            var p: BidimentionalNode<Int>? = this.resolve(stepX, stepY)
            var taller = false
            var count = 0
            while (p != null && !taller) {
                count ++
                taller = p.data >= data
                p = p.resolve(stepX, stepY)
            }

            count
        }

        return viewDistance(0, -1) * viewDistance(-1, 0) * viewDistance(1, 0) * viewDistance(0, 1)
    }
}

fun BidimentionalNode<Int>.isVisible(): Boolean {
    val up = resolve(0, -1)
    val left = resolve(-1, 0)
    val right = resolve(1, 0)
    val down = resolve(0, 1)
    if (up == null || left == null || right == null || down == null) {
        return true
    } else {
        val checkDir : (BidimentionalNode<Int>, Int, Int) -> Boolean = {
                node, stepX, stepY ->

            var p: BidimentionalNode<Int>? = node
            var max = -1
            while (p != null) {
                if (p.data > max) max = p.data
                p = p.resolve(stepX, stepY)
            }
            max < data
        }

        if (checkDir(up, 0, -1)) return true
        if (checkDir(left, -1, 0)) return true
        if (checkDir(right, 1, 0)) return true
        if (checkDir(down, 0, 1)) return true
    }
    return false
}

fun main(
    args: Array<String>
) {
    test()

    val matrix = File(args[0]).readLines().map { line -> line.toCharArray().map { c -> c.digitToInt() }.toMutableList() }
    var pointer: BidimentionalNode<Int>? = BidimentionalNode.build(matrix) { it }.first

    pointer = pointer!!.resolve(-matrix[0].size+1, -matrix.size+1 )!!

    var visibleCount = 0
    BidimentionalNode.navigate(pointer, {
        node ->

        if (node.isVisible()) {
            visibleCount ++
        }
    }, {})

    println("result1 $visibleCount")

    var maxScenicScore = 0
    BidimentionalNode.navigate(pointer, {
        node ->

        val score = node.scenicScore()
        if (score > maxScenicScore) maxScenicScore = score
    }, {})

    println("result2 $maxScenicScore")
}

private fun test() {
    assert(0 == BidimentionalNode(3).neighbor(1,0, BidimentionalNode(0)).neighbor(0,1, BidimentionalNode(2)).scenicScore())
    assert(4 == BidimentionalNode(5)
        .neighbor(-1, 0, BidimentionalNode(5).neighbor(-1, 0, BidimentionalNode(2)))
        .neighbor(0, -1, BidimentionalNode(3))
        .neighbor(1, 0, BidimentionalNode(1).neighbor(1, 0, BidimentionalNode(2)))
        .neighbor(0, 1, BidimentionalNode(3).neighbor(0, 1, BidimentionalNode(5).neighbor(0, 1, BidimentionalNode(3))))
        .scenicScore())

}