package com.toto_castaldi.common.algo

import com.toto_castaldi.common.structure.Graph
import kotlin.test.Test
import kotlin.test.assertEquals

class BFSTest {

    @Test
    fun algoTest () {
        val graph = Graph("A" , "B" , "C" , "D" , "E", "F", "G", "H", "I", "L")

        val bfs = BFS(graph)

        bfs.edge("A", "C")
        bfs.edge("A", "E")
        bfs.edge("A", "F")
        bfs.edge("B", "E")
        bfs.edge("B", "F")
        bfs.edge("C", "D")
        bfs.edge("C", "E")
        bfs.edge("E", "F")
        bfs.edge("D", "H")
        bfs.edge("G", "D")
        bfs.edge("I", "D")
        bfs.edge("L", "I")

        val path = bfs.path("G", "B")

        assertEquals(5, path.size)

        assertEquals(listOf("G", "D", "C", "E", "B"), path)
    }
}