package com.toto_castaldi.common.algo

import com.toto_castaldi.common.structure.Graph
import com.toto_castaldi.common.structure.Node
import java.util.*

class BFS<T>(val graph: Graph<T>) {

    fun edge(valueFrom: T, valueTo: T) {
        graph[valueFrom]!!.twoWays(graph[valueTo]!!)
    }

    fun path(nodeFrom: T, nodeTo: T): List<T> {
        val q: Queue<Node<T>> = LinkedList()
        val visited = mutableSetOf<T>()
        val child = mutableMapOf<T,T>()
        var node = graph[nodeFrom]!!

        visited.add(node.data)
        q.add(node)

        while (q.isNotEmpty()) {
            node = q.remove()

            if (node.data == nodeTo) {
                break
            } else {
                for (neighbor in node) {
                    if (!visited.contains(neighbor.data)) {
                        visited.add(neighbor.data)
                        child[neighbor.data] = node.data
                        q.add(neighbor)
                    }
                }
            }
        }

        val result = mutableListOf<T>()
        var current = nodeTo
        result.add(current)
        while (current != nodeFrom) {
            current = child[current]!!
            result.add(0, current)
        }

        return result
    }


}
