import java.io.File

fun main(args: Array<String>) {
    val matrix = File(args[0]).readLines().map { line -> line.toCharArray().map { c -> c.digitToInt() }.toMutableList() }
    for (i in 1..2) {
        step(matrix)
        for (row in matrix) {
            println(row)
        }
        println()

    }

}

fun step(matrix: List<MutableList<Int>>) {
    for (y in 0 until matrix.size) {
        for(x in 0 until matrix[y].size) {
            matrix[y][x] += 1
        }
    }
    for (y in 0 until matrix.size) {
        for(x in 0 until matrix[y].size) {
            if (matrix[y][x] > 9) {
                if (y > 0 && x > 0) matrix[y-1][x-1] += 1
                if (y > 0) matrix[y-1][x] += 1
                if (y > 0 && x < matrix[y].size -1) matrix[y-1][x+1] += 1
                if (x > 0) matrix[y][x-1] += 1
                if (x < matrix[y].size -1) matrix[y][x+1] += 1
                if (y < matrix.size -1 && x > 0) matrix[y+1][x-1] += 1
                if (y < matrix.size -1) matrix[y+1][x] += 1
                if (y < matrix.size -1 && x < matrix[y].size -1) matrix[y+1][x+1] += 1
            }
        }
    }
    for (y in 0 until matrix.size) {
        for(x in 0 until matrix[y].size) {
            if (matrix[y][x] > 9) matrix[y][x] = 0
        }
    }

}

