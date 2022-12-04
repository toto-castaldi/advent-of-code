import java.io.File

fun main(args: Array<String>) {
    var containgCount = 0
    var overlappingCount = 0
    File(args[0]).forEachLine {
        line ->

        val (assignmentLeft, assignmentRight) = line.trim().split(",").map {it.trim().split("-").map { section -> section.toInt() }}

        if (assignmentLeft[0] <= assignmentRight[0] && assignmentLeft[1] >= assignmentRight[1]) {
            containgCount ++
            overlappingCount ++
        } else if (assignmentRight[0] <= assignmentLeft[0] && assignmentRight[1] >= assignmentLeft[1]) {
            containgCount ++
            overlappingCount ++
        } else if (assignmentRight[0] <= assignmentLeft[1] && assignmentRight[0] >= assignmentLeft[0]) {
            overlappingCount ++
        } else if (assignmentRight[1] <= assignmentLeft[1] && assignmentRight[1] >= assignmentLeft[0]) {
            overlappingCount ++
        }
    }

    println(containgCount)
    println(overlappingCount)
}

