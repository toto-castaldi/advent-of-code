import java.io.File

private val caves = mutableListOf<Cave>()

private class Cave(val name: String) {
    private val bindedCaves = mutableListOf<Cave>()

    fun bindTo(otherCave: Cave) {
        if (otherCave !in bindedCaves) {
            bindedCaves.add(otherCave)
            otherCave.bindedCaves.add(this)
        }
    }

    override fun toString(): String {
        return name
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
        val caveA = Cave(caveAName)
        val caveB = Cave(caveBName)
        if (caveA !in caves) {
            caves.add(caveA)
        }
        if (caveB !in caves) {
            caves.add(caveB)
        }
        caveA.bindTo(caveB)
    }
    println(caves)
}

private fun test() {

}
