package com.toto_castaldi.common.algo

import com.toto_castaldi.common.structure.Graph
import kotlin.test.Test
import kotlin.test.assertEquals

class DijkstraTest {

    @Test
    fun algoTest () {
        val graph = Graph("A" , "B" , "C" , "D" , "E")

        val dijkstra = Dijkstra(graph)

        dijkstra.twoWays("A", "B", 6)
        dijkstra.twoWays("A", "D", 1)
        dijkstra.twoWays("B", "D", 2)
        dijkstra.twoWays("D", "E", 1)
        dijkstra.twoWays("B", "E", 2)
        dijkstra.twoWays("B", "C", 5)
        dijkstra.twoWays("C", "E", 5)

        val shortest: Map<String, Int> = dijkstra.shortestFrom("A")

        assertEquals(5, shortest.size)
        assertEquals(0, shortest["A"])
        assertEquals(3, shortest["B"])
        assertEquals(7, shortest["C"])
        assertEquals(1, shortest["D"])
        assertEquals(2, shortest["E"])

    }
}