import java.io.File

private val caves = mutableListOf<Cave>()

private class Cave(val name: String) {
    private val bindedCaves = mutableListOf<Cave>()

    fun bindTo(otherCave: Cave) {
        if (otherCave !in bindedCaves) {
            bindedCaves.add(otherCave)
        }
        if (this !in otherCave.bindedCaves) {
            otherCave.bindedCaves.add(this)
        }
    }

    override fun toString(): String {
        return "$name " + bindedCaves.fold("(") { acc, cave -> acc + (if (acc.endsWith("(")) "" else "; ") + cave.name} + ")"
    }

    //iterator.contains
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cave

        if (other.name == name) return true

        return false
    }
}

fun main(
    args: Array<String>
) {
    test()
    File(args[0]).forEachLine {
        line ->

        val (caveAName, caveBName) = line.split("-")
        val caveA = caves.find { it.name == caveAName }?.let { it } ?: run {
            val cave = Cave(caveAName)
            caves.add(cave)
            cave
        }
        val caveB = caves.find { it.name == caveBName }?.let { it } ?: run {
            val cave = Cave(caveBName)
            caves.add(cave)
            cave
        }

        caveA.bindTo(caveB)
    }
    println(caves)
}

private fun test() {

}
