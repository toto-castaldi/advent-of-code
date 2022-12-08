import com.toto_castaldi.common.BidimentionalNode
import java.io.File

fun BidimentionalNode<Int>.incEnergy () {
    if (data <= 9) {
        data++
        if (data > 9) {
            for (n in neighbors()) {
                n.incEnergy()
            }
        }
    }
}

fun BidimentionalNode<Int>.flash(): Boolean {
    if (data > 9) {
        data = 0
        return true
    }
    return false
}


private fun step(
    octopus: BidimentionalNode<Int>
): Int {
    var flashes = 0

    BidimentionalNode.navigate(octopus, { it.incEnergy() }, {})
    BidimentionalNode.navigate(octopus, { flashes += if (it.flash()) 1 else 0 }, {})

    return flashes
}


fun main(
    args: Array<String>
) {
    val matrix = File(args[0]).readLines().map { line -> line.toCharArray().map { c -> c.digitToInt() }.toMutableList() }
    var (octopus: BidimentionalNode<Int>?, count) = BidimentionalNode.build(matrix) { it }
    octopus = octopus!!.topLeft()

    BidimentionalNode.printNodes(octopus)

    var allFlashes = 0
    var flashesTogether = 0
    var i = 0

    while (flashesTogether == 0) {
        i++
        val stepFlashes = step(octopus)
        if (i < 100) {
            allFlashes += stepFlashes
        }

        BidimentionalNode.printNodes(octopus)

        if (stepFlashes == count) {
            flashesTogether = i
        }
    }
    println(allFlashes)
    println(flashesTogether)
}
