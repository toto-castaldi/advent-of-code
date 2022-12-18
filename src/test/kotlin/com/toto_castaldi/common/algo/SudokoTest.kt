package com.toto_castaldi.common.algo

import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
        println(sudoku)
        assertTrue { sudoku.valid(0,4, 8) }
        assertFalse { sudoku.valid(8,3, 3) } //invalid column
        assertFalse { sudoku.valid(0,4, 2) } //invalid row
        assertFalse { sudoku.valid(7,6, 8) } //invalid square
    }

    @Test
    @Ignore
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
        println(sudoku)
    }
}