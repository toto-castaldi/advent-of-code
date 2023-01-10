package com.toto_castaldi.common.structure

class Graph<T>(vararg nodeValues: T) : Iterable<Node<T>> {

    private val nodes = mutableSetOf<Node<T>>()

    init {
        for (t in nodeValues) {
            getOrCreateNode(t)
        }
    }

    operator fun plus(nodeVal: T): Graph<T> {
        getOrCreateNode(nodeVal)
        return this
    }

    fun getOrCreateNode(v: T): Node<T> {
        return nodes.firstOrNull() { n -> n.data == v } ?:run {
            val result = Node(v)
            nodes.add(result)
            result
        }
    }

    override fun iterator(): Iterator<Node<T>> {
        return nodes.iterator()
    }

    operator fun get(v: T): Node<T>? {
        return nodes.firstOrNull() { n -> n.data == v }
    }

}