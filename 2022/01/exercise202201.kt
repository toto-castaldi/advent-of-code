import java.io.File
import java.util.ArrayList

fun main(args: Array<String>) {
    val fileName : String = args[0]
    val fileInput = File(fileName)
    var elvesCalories: ArrayList<Int> = ArrayList()
    var currentCalories = 0
    fileInput.forEachLine {
        line ->

        if (line.trim().length == 0) {
            elvesCalories.add(currentCalories)
            currentCalories = 0
        } else {
            currentCalories += line.trim().toInt()
        }

    }

    var caloriesSorted = elvesCalories.sortedDescending()
    val first = caloriesSorted.get(0)
    val second = caloriesSorted.get(1)
    val thirth = caloriesSorted.get(2)
    val total = first + second + thirth
    println("result1 $first")
    println("result2 $total")
}
