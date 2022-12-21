package com.toto_castaldi.common.tools

/**
 * output for https://csacademy.com/app/graph_editor/
 */
class CsAcademyGraph {
    interface CsAcademyGraphNode {
        fun noChild(): Boolean
        fun constantValue(): String?
        fun children(): List<String>
    }


    companion object {
        fun printGraph(dictionary: MutableMap<String, CsAcademyGraphNode>, name: String) {
            for (entry in dictionary.entries) {
                println(nodeName(entry))
            }
            addArc(dictionary, name, dictionary[name]!!)
        }

        private fun addArc(dictionary: MutableMap<String, CsAcademyGraphNode>, name: String, node: CsAcademyGraphNode) {
            for (childName in node.children()) {
                println("${nodeName(name, node)} ${nodeName(childName, dictionary[childName]!!)}")
                addArc(dictionary, childName, dictionary[childName]!!)
            }
        }

        private fun nodeName(name: String, node: CsAcademyGraphNode): String {
            var label = name
            if (node.noChild()) {
                label += "(${node.constantValue()})"
            }
            return label
        }

        private fun nodeName(entry: MutableMap.MutableEntry<String, CsAcademyGraphNode>): String {
            return nodeName(entry.key, entry.value)
        }

    }



}
