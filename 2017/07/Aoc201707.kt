import com.toto_castaldi.common.structure.Node
import java.io.File

class Aoc201707() {

    private val nodes = mutableMapOf<String, Node<String>>()
    
    operator fun plus(s: String) {
        //tqlentr (214) -> gfxnuuk, thmlk
        val programName = s.split('(')[0].trim()
        val children = s.split("->").let { split ->
            if (split.size == 2) {
                split[1].split(',').map { sub -> sub.trim() }
            } else {
                emptyList<String>()
            }
        }

        val node = nodes.getOrPut(programName) { Node(programName) }

        for (child in children) {
            val childNode = nodes.getOrPut(child) { Node(child) }

            childNode.oneWay(node) // -> parent
        }
    }

    fun bottomProgramName(): String {
        return nodes.values.find { n -> n.neighborsSize() == 0 }.let { n -> n?.data ?: throw Exception("no root") }
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
