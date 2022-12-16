package com.toto_castaldi.common.structure

class Graph<T> : Iterable<Node<T>> {

    private val nodes = mutableMapOf<String, Node<T>>()

    fun getOrCreateNode(name: String, t: T): Node<T> {
        return nodes[name] ?:run {
            val result = Node(t)
            nodes[name] = result
            result
        }
    }

    fun getNode(name: String): Node<T>? {
        return nodes[name]
    }

    override fun iterator(): Iterator<Node<T>> {
        return nodes.values.iterator()
    }

    fun clone(): Graph<T> {
        TODO("Not yet implemented")
    }

}