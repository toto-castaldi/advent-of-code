package com.toto_castaldi.common.algo

import com.toto_castaldi.common.structure.Graph
import kotlin.test.Test
import kotlin.test.assertEquals

class DijkstraTest {

    @Test
    fun algoTest () {
        val graph = Graph("A" , "B" , "C" , "D" , "E")

        val dijkstra = Dijkstra(graph)

        dijkstra.arc("A", "B", 6)
        dijkstra.arc("A", "D", 1)
        dijkstra.arc("D", "B", 2)
        dijkstra.arc("D", "E", 1)
        dijkstra.arc("B", "E", 2)
        dijkstra.arc("B", "C", 5)
        dijkstra.arc("E", "C", 5)

        val shortest = dijkstra.shortestFrom("A")

        assertEquals(5, shortest.size)
        assertEquals(0, shortest["A"])
        assertEquals(3, shortest["B"])
        assertEquals(7, shortest["C"])
        assertEquals(1, shortest["D"])
        assertEquals(2, shortest["E"])

    }
}