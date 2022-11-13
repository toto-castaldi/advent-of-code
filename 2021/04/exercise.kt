import java.io.File
import java.util.Arrays
import java.util.Collections

fun main() {
    val unmarked = {board : List<Int> -> board.fold(0) { acc, value -> acc + (if (value >= 100) 0 else value )} } 
    
    val lines: List<String> = File("input.txt").readLines()
    val calledNumbers: List<Int> = lines.get(0).split(",").map({it.trim().toInt()})
    var boards: ArrayList<List<Int>> = ArrayList()
    for (i in 2..lines.size-1 step 6) {
        val board: ArrayList<Int> = ArrayList()
        for (j in i..i+4) {
            board.addAll(lines.get(j).chunked(3).map({it.trim().toInt()}))
        }
        boards.add(board)
    }
    calling@ for (called in calledNumbers) {
        val newBoards : ArrayList<List<Int>> = ArrayList()
        for (board in boards) {
            val newBoard = board.map { if (it == called) it + 100 else it }
            newBoards.add(newBoard)
            for (hStartingIndex in 0..newBoard.size-1 step 5) {
                var countH = 0
                for (h in hStartingIndex..4) {
                    countH += if (newBoard.get(h) >= 100) 1 else 0
                }
                if (countH >= 5) {
                    val u = unmarked(newBoard)
                    val result = u * called
                    println("H at $newBoard, c $called,  u $u -> $result")
                    break@calling
                }
            }
            for (vStartingIndex in 0..4) {
                var countV = 0
                for (v in 0..4) {
                    val index = vStartingIndex + v * 5
                    countV += if (newBoard.get(index) >= 100) 1 else 0
                }
                if (countV >= 5) {
                    val u = unmarked(newBoard)
                    val result = u * called
                    println("V at $newBoard, c $called,  u $u -> $result")
                    break@calling
                }   
            }
            
        }
        boards = ArrayList()
        boards.addAll(newBoards)
    }

}