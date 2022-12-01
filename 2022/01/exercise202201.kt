import java.io.File

fun main(args: Array<String>) {
    val fileName : String = args[0]
    val fileInput = File(fileName)
    val elvesCalories = mutableListOf<Int>()
    var currentCalories = 0
    fileInput.forEachLine {
        line ->

        line.trim().toIntOrNull()
            ?.let {
                currentCalories += it
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
