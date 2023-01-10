package com.toto_castaldi.common.structure

open class Node<T>(val data : T) : Iterable<Node<T>> {

    private var neighbors = mutableSetOf<Node<T>>()

    fun oneWay(node: Node<T>) {
        neighbors.add(node)
    }

    override fun iterator(): Iterator<Node<T>> {
        return neighbors.iterator()
    }

    override fun toString(): String {
        return data.toString()
    }

    fun twoWays(n: Node<T>) {
        oneWay(n)
        n.neighbors.add(this)
    }

}