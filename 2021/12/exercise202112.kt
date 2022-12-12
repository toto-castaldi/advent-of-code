import java.io.File

private val caves = mutableListOf<Cave>()

private class Cave(val name: String) {
    val bindedCaves = mutableListOf<Cave>()

    fun bindTo(otherCave: Cave) {
        if (otherCave !in bindedCaves) {
            bindedCaves.add(otherCave)
        }
        if (this !in otherCave.bindedCaves) {
            otherCave.bindedCaves.add(this)
        }
    }

    override fun toString(): String {
        //return "$name " + bindedCaves.fold("(") { acc, cave -> acc + (if (acc.endsWith("(")) "" else "; ") + cave.name} + ")"
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
    var caveStart: Cave? = null
    var caveEnd: Cave? = null
    File(args[0]).forEachLine { line ->

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

        if (caveA.name == "start") caveStart = caveA
        if (caveB.name == "start") caveStart = caveB
        if (caveA.name == "end") caveEnd = caveA
        if (caveB.name == "end") caveEnd = caveB

        caveA.bindTo(caveB)
    }
    println(caves)

    val paths = mutableListOf<String>()

    fun rp (prevSteps: String, cave : Cave)   {
        val pn : (Cave) -> String = { it -> "-${it.name}"}
        var st = prevSteps + pn(cave)
        if (st.endsWith(pn(caveEnd!!))) {
            paths.add(st)
        } else {
            for (c in cave.bindedCaves) {
                if (c.name[0].isLowerCase() && !st.contains(pn(c))) {
                    rp(st, c)
                } else if (c.name[0].isUpperCase() && !st.contains(pn(cave) + pn(c))) {
                    rp(st, c)
                }
            }
        }
    }

    rp("", caveStart!!)
    println(paths)
    println(paths.size)
}


private fun test() {

}
