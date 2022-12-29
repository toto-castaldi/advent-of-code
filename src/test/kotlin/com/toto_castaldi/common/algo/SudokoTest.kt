package com.toto_castaldi.common.algo

import kotlin.test.*

class SudokoTest {

    @Test
    fun validTest() {
        val sudoku = Sudoku(
            listOf(
                "005200000",
                "400009730",
                "908000605",
                "067800000",
                "031792504",
                "049056178",
                "700000003",
                "000420850",
                "004035000"
            )
        )
        assertEquals (0, sudoku.valid(2,6, 2))
        assertEquals (0, sudoku.valid(1,0, 1))
        assertEquals (-2, sudoku.valid(8,3, 3))
        assertEquals (0, sudoku.valid(0,4, 8))
        assertEquals (-1, sudoku.valid(0,4, 2))
        assertEquals (-3, sudoku.valid(7,6, 8))

    }

    @Test
    fun solveTest() {
        val sudoku = Sudoku(
            listOf(
                "005200000",
                "400009730",
                "908000605",
                "067800000",
                "031792504",
                "049056178",
                "700000003",
                "000420850",
                "004035000"
            )
        )
        sudoku.solve()
        assertEquals(1, sudoku.solutions.size)
        println(sudoku.solutions[0])
    }
}