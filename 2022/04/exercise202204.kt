import java.io.File

fun main(args: Array<String>) {
    var containgCount = 0
    var overlappingCount = 0
    File(args[0]).forEachLine {
        line ->

        val (assignmentLeft, assignmentRight) = line.trim().split(",").map {
            val (fromSeq, toSeq) = it.trim().split("-").map { it.toInt() }
            (fromSeq..toSeq).toSet()
        }

        if (assignmentLeft.containsAll(assignmentRight) || assignmentRight.containsAll(assignmentLeft)) {
            containgCount ++
            overlappingCount ++
        } else if (assignmentLeft.intersect(assignmentRight).isNotEmpty() || assignmentRight.intersect(assignmentLeft).isNotEmpty()) {
            overlappingCount ++
        }
    }

    println(containgCount)
    println(overlappingCount)
}

