package com.toto_castaldi.common.algo

/**
 * https://www.youtube.com/watch?v=pVfj6mxhdMw
 */
class Dijkstra<T> {

    private val weights = mutableMapOf<Pair<T,T>, Int>()
    private val nodes = mutableSetOf<T>()

    operator fun plus(node: T): Dijkstra<T> {
        nodes.add(node)
        return this
    }

    fun weight(fromNode: T, toNode: T, w: Int) {
        weights[Pair(fromNode, toNode)] = w
        weights[Pair(toNode, fromNode)] = w
    }

    fun shortestFrom(startingNode: T): Map<T, Int> {
        val unvisited = mutableSetOf<T>()
        val result = mutableMapOf<T, Int>()

        val computation = mutableMapOf<T, Pair<Int,T?>>()
        for (n in nodes) {
            if (n == startingNode) {
                computation[n] = Pair(0, null)
                result[n] = 0
            } else {
                computation[n] = Pair(Int.MAX_VALUE, null)
                result[n] = Int.MAX_VALUE
            }
        }

        unvisited.addAll(nodes.toList())

        var current = startingNode


        while (unvisited.size > 0) {

            var smallestDistance = Int.MAX_VALUE

            for (u in unvisited) {
                val p = computation[u]!!
                if (p.first < smallestDistance) {
                    smallestDistance = p.first
                    current = u
                }
            }

            for (unvisitedNeighbour in weights.filter { entry -> entry.key.first == current && entry.key.second in unvisited }) {
                val dest = unvisitedNeighbour.key.second
                val prev = computation[current]!!.first

                val dist = prev + weights[Pair(current, dest)]!!

                if (dist < computation[dest]!!.first) {
                    computation[dest] = Pair(dist, current)
                    result[dest] = dist
                }
            }

            unvisited.remove(current)
        }

        return result
    }


}
