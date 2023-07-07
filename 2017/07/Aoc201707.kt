import com.toto_castaldi.common.structure.Node
import java.io.File

class Aoc201707() {

    val nodes = mutableMapOf<String, Node<String>>()
    
    operator fun plus(s: String) {
        //tqlentr (214) -> gfxnuuk, thmlk
        val programName = s.split('(')[0].trim()
        val split = s.split("->")
        var children = emptyList<String>()
        if (split.size == 2) {
            children = split[1].split(',').map { sub -> sub.trim() }
        }

        var node = Node(programName)
        if (programName in nodes.keys) {
            node = nodes[programName]!!
        } else {
            nodes[programName] = node
        }
        for (child in children) {
            var childNode = Node(child)
            if (child in nodes.keys) {
                childNode = nodes[child]!!
            } else {
                nodes[child] = childNode
            }
            childNode.oneWay(node) // -> parent
        }
    }

    fun bottomProgramName(): String {
        for (n in nodes.values) {
            if (n.neighborsSize() == 0) {
                return n.data
            }
        }
        throw Exception("no root")
    }

    companion object {
        fun run1(fileName: String) {
            val aoc = Aoc201707()
            for (l in File(fileName).readLines()) {
                aoc + l
            }
            println(aoc.bottomProgramName())
        }
    }

}
