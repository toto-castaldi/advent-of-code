package com.toto_castaldi.common


fun <T> List<Pair<T, T>>.getUniqueValuesFromPairs(): Set<T> = this
    .map { (a, b) -> listOf(a, b) }
    .flatten()
    .toSet()

fun <T> List<Pair<T, T>>.getUniqueValuesFromPairs(predicate: (T) -> Boolean): Set<T> = this
    .map { (a, b) -> listOf(a, b) }
    .flatten()
    .filter(predicate)
    .toSet()

class Dijkstra<T>() {

    private lateinit var graph: Graph<T>
    private val weights = mutableMapOf<Pair<T,T>, Int>()

    fun arc(fromNode: T, toNode: T, weight: Int): Dijkstra<T> {
        weights[Pair(fromNode, toNode)] = weight
        graph = Graph<T>(weights)
        return this
    }

    fun arc(fromNode: T, toNode: T): Dijkstra<T> {
        return arc(fromNode, toNode, 1)
    }

    fun shortestPath(start: T, end: T): List<T> {
        val S: MutableSet<T> = mutableSetOf() // a subset of vertices, for which we know the true distance

        /*
         * delta represents the length of the shortest distance paths
         * from start to v, for v in vertices.
         *
         * The values are initialized to infinity, as we'll be getting the key with the min value
         */
        val delta = graph.vertices.map { it to Int.MAX_VALUE }.toMap().toMutableMap()
        delta[start] = 0

        val previous: MutableMap<T, T?> = graph.vertices.map { it to null }.toMap().toMutableMap()

        while (S != graph.vertices) {
            // let v be the closest vertex that has not yet been visited
            val v: T = delta
                .filter { !S.contains(it.key) }
                .minBy { it.value }!!
                .key

            graph.edges.getValue(v).minus(S).forEach { neighbor ->
                val newPath = delta.getValue(v) + graph.weights.getValue(Pair(v, neighbor))

                if (newPath < delta.getValue(neighbor)) {
                    delta[neighbor] = newPath
                    previous[neighbor] = v
                }
            }

            S.add(v)
        }

        val shortestPathTree: Map<T, T?> = previous.toMap()

        fun pathTo(start: T, end: T): List<T> {
            if (shortestPathTree[end] == null) return listOf(end)
            return listOf(pathTo(start, shortestPathTree[end]!!), listOf(end)).flatten()
        }

        return pathTo(start, end)
    }

}




fun main(
    args: Array<String>
) {
    //simple
    var dijkstra = Dijkstra<Int>()
        .arc(0,1)
        .arc(0,2)
        .arc(1,2)
        .arc(2,3)
    assert(listOf(0,1) == dijkstra.shortestPath(0,1))
    assert(listOf(0,2,3) == dijkstra.shortestPath(0,3))

    //line
    dijkstra = Dijkstra()
    for (i in 0 until 99) {
        dijkstra.arc(i, i + 1)
    }
    assert(dijkstra.shortestPath(0,50).size == 51)
    assert(dijkstra.shortestPath(0,99).size == 100)
    assert(listOf(50,51) == dijkstra.shortestPath(50,51))
    assert(listOf(0) == dijkstra.shortestPath(50,0))

    // U 3 * 3
    dijkstra = Dijkstra<Int>()
    .arc(0,3)
    .arc(3,6)
    .arc(6,7)
    .arc(7,8)
    .arc(8,5)
    .arc(5,2)
    assert(listOf(0, 3, 6, 7, 8, 5, 2) == dijkstra.shortestPath(0,2))

}
