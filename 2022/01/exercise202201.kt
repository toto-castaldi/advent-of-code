import java.io.File

fun main(args: Array<String>) {
    val elvesCalories = mutableListOf<Int>()
    var currentCalories = 0

    File(args[0]).forEachLine { line ->
        line.trim().toIntOrNull()
            ?.let {cal ->
                currentCalories += cal
            } //null case
            ?: run {
                elvesCalories.add(currentCalories)
                currentCalories = 0
            }
    }

    //change in place
    elvesCalories.sortDescending()
    val first = elvesCalories[0]
    val firstThree = elvesCalories.subList(0, 3).fold(0) { acc, value -> acc + value }

    println("result1 $first")
    println("result2 $firstThree")
}
