package com.toto_castaldi.common.structure

class Node<T>(val name: String, val data : T) : Iterable<Node<T>> {

    private val neighbors = mutableSetOf<Node<T>>()

    fun addNeighbor(node: Node<T>) {
        neighbors.add(node)
    }

    override fun iterator(): Iterator<Node<T>> {
        return neighbors.iterator()
    }

    override fun toString(): String {
        return name
    }

}
