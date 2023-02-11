package com.toto_castaldi.common.algo

import com.toto_castaldi.common.structure.Graph
import kotlin.test.Test
import kotlin.test.assertEquals

class DijkstraTest {

    @Test
    fun algoTest () {
        val graph = Graph("A" , "B" , "C" , "D" , "E")

        val dijkstra = Dijkstra(graph)

        dijkstra.edge("A", "B", 6)
        dijkstra.edge("A", "D", 1)
        dijkstra.edge("D", "B", 2)
        dijkstra.edge("D", "E", 1)
        dijkstra.edge("B", "E", 2)
        dijkstra.edge("B", "C", 5)
        dijkstra.edge("E", "C", 5)

        val shortest = dijkstra.shortestFrom("A")

        assertEquals(5, shortest.size)
        assertEquals(0, shortest["A"])
        assertEquals(3, shortest["B"])
        assertEquals(7, shortest["C"])
        assertEquals(1, shortest["D"])
        assertEquals(2, shortest["E"])

    }
}