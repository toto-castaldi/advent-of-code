package com.toto_castaldi.common

/**
 * compute the minimum distance between a node all the others. This is the Dijkstra algorithm
 */
class Dijkstra (val nodes: Int){

    private var edges: Array<IntArray>

    init {
        edges = Array(nodes) { IntArray(nodes) { -1 } }
    }

    fun arc(fromNodeIndex: Int, toNodeIndex: Int, arcWeight: Int): Dijkstra {
        edges[fromNodeIndex][toNodeIndex] = arcWeight
        return this
    }

    fun arc(fromNodeIndex: Int, toNodeIndex: Int): Dijkstra {
        return arc(fromNodeIndex, toNodeIndex, 1)
    }

    fun aaaa(source: Int,  edges: Array<IntArray>, nodes: Int) {
        fun extractMin(Q: MutableList<Int>, d: IntArray): Int {
            var minNode = Q[0]
            var minDistance: Int = d[0]

            Q.forEach {
                if (d[it] < minDistance) {
                    minNode = it
                    minDistance = d[it]
                }
            }

            Q.remove(minNode)
            return minNode
        }
        val d = IntArray(nodes) { Integer.MAX_VALUE }
        val pi = IntArray(nodes) { -1 }
        d[source] = 0

        val S: MutableList<Int> = ArrayList()
        val Q: MutableList<Int> = (0 until nodes).toMutableList()

        // Iterations
        while (Q.isNotEmpty()) {
            val u: Int = extractMin(Q, d)
            S.add(u)

            edges[u].forEachIndexed { v, vd ->
                if (vd != -1 && d[v] > d[u] + vd) {
                    d[v] = d[u] + vd
                    pi[v] = u
                }
            }
        }

        println("d: ${d.contentToString()}")
        println("pi: ${pi.contentToString()}")

    }

    fun compute(fromNodeIndex: Int): DijkstraComputeResult {
        fun extractMin(Q: MutableList<Int>, d: Array<Int>): Int {
            var minNode = Q[0]
            var minDistance: Int = d[0]

            Q.forEach {
                if (d[it] < minDistance) {
                    minNode = it
                    minDistance = d[it]
                }
            }

            Q.remove(minNode)
            return minNode
        }

        val distances = Array<Int>(nodes) { Integer.MAX_VALUE }
        val precedingIndex = Array<Int>(nodes) { -1 }
        distances[fromNodeIndex] = 0

        val s: MutableList<Int> = ArrayList()
        val q: MutableList<Int> = (0 until nodes).toMutableList()

        while (q.isNotEmpty()) {
            val u: Int = extractMin(q, distances)
            s.add(u)

            edges[u].forEachIndexed { v, vd ->
                if (vd != -1 && distances[v] > distances[u] + vd) {
                    distances[v] = distances[u] + vd
                    precedingIndex[v] = u
                }
            }
        }

        return DijkstraComputeResult(distances, precedingIndex)
    }

    data class DijkstraComputeResult(val distances: Array<Int>, val precedingIndex: Array<Int>) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as DijkstraComputeResult

            if (!distances.contentEquals(other.distances)) return false
            if (!precedingIndex.contentEquals(other.precedingIndex)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = distances.contentHashCode()
            result = 31 * result + precedingIndex.contentHashCode()
            return result
        }
    }

    companion object {
        fun countBackwards(result: DijkstraComputeResult, fromIndex: Int, toIndex: Int): Int {
            val precedingIndex = result.precedingIndex
            var i = precedingIndex[fromIndex]
            var c = 1
            while (i != toIndex && i != -1) {
                i = precedingIndex[i]
                c ++
            }
            return c
        }
    }

}


fun main() {
    val dijkstra0 = Dijkstra(6)
        .arc(0,1,34)
        .arc(0,3,43)
        .arc(1,2,56)
        .arc(1,3,20)
        .arc(1,4,57)
        .arc(2,5,20)
        .arc(3,1,20)
        .arc(3,4,50)
        .arc(4,2,37)
    val dijkstra0result = dijkstra0.compute(0)
    println(dijkstra0result.distances.contentToString())
    println(dijkstra0result.precedingIndex.contentToString())

    val dijkstra1 = Dijkstra(5)
        .arc(0,1)
        .arc(0,2)
        .arc(1,2)
        .arc(2,3)
    val dijkstra1result = dijkstra1.compute(0)
    println(dijkstra1result.distances.contentToString())
    println(dijkstra1result.precedingIndex.contentToString())

    val dijkstra2 = Dijkstra(40)
        .arc(0,1)
        .arc(1,2)
        .arc(2,10)
        .arc(10,18)
        .arc(18,26)
        .arc(18,32)
        .arc(32,33)
        .arc(33,34)
        .arc(34,35)
        .arc(35,36)
        .arc(36,37)
        .arc(37,38)
        .arc(38,39)
        .arc(39,31)
        .arc(31,23)
        .arc(23,15)
        .arc(15,7)
        .arc(7,6)
        .arc(6,5)
        .arc(5,4)
        .arc(4,3)
    val dijkstra2result = dijkstra2.compute(0)
    println(Dijkstra.countBackwards(dijkstra2result, 3, 0))
}
