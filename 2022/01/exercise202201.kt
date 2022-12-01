import java.io.File
import java.util.ArrayList

fun main(args: Array<String>) {
    val fileName : String = args[0]
    val fileInput = File(fileName)
    var maxCalories = 0
    var currentCalories = 0
    fileInput.forEachLine {
        line ->

        if (line.trim().length == 0) {
            currentCalories = 0
        } else {
            currentCalories += line.trim().toInt()
        }
        if (currentCalories > maxCalories) {
            maxCalories = currentCalories
        }
    }
    println(maxCalories)


}
