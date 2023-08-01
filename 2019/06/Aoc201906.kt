import java.io.File

class Aoc201906() {

    val nodes = mutableMapOf<String, Node>()

    class Node {

        var child: Node? = null
        var parent: Node? = null
        private lateinit var name: String

        constructor(n: String) {
            name = n
        }

    }

    operator fun plus(orbit: String) {
        val parentName = orbit.split(')')[0].trim()
        val childName = orbit.split(')')[1].trim()
        val parentNode = nodes[parentName]?.let { it } ?: Node(parentName)
        val childNode = nodes[childName]?.let { it } ?: Node(childName)

        parentNode.child = childNode
        childNode.parent = parentNode

        nodes[parentName] = parentNode
        nodes[childName] = childNode
    }

    fun totalOrbitsCount(): Int {
        var totalCount = 0
        for (nodeName in nodes.keys) {
            var localCount = 0
            var node = nodes[nodeName]!!
            while (node.parent != null) {
                localCount ++
                node = node.parent!!
            }
            totalCount += localCount
        }
        return totalCount
    }

    companion object {
        fun run1(fileName: String) {
            val aoc = Aoc201906()
            for (line in File(fileName).readLines()) {
                aoc + line
            }
            println(aoc.totalOrbitsCount())
        }
    }

}
