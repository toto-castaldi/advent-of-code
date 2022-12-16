package com.toto_castaldi.common.structure

class Graph<T> : Iterable<Node<T>> {

    private val nodes = mutableMapOf<String, Node<T>>()

    fun getOrCreateNode(name: String, t: T): Node<T> {
        return nodes[name] ?:run {
            val result = Node(name, t)
            nodes[name] = result
            result
        }
    }

    override fun iterator(): Iterator<Node<T>> {
        return nodes.values.iterator()
    }

    operator fun get(name: String): Node<T>? {
        return nodes[name]
    }


}