import java.io.File

fun main() {
    var horizontal = 0
    var depth = 0
    File("input-1.txt").forEachLine { 
        val splitted = it.split("\\s".toRegex()).toTypedArray()
        val direction = splitted[0]
        val amount = splitted[1].toInt()
        when (direction) {
            "up" -> depth -= amount
            "down" -> depth += amount
            "forward" -> horizontal += amount
            else -> { 
                print("unknow direction $direction")
            }
        }
    }
    val result = horizontal * depth
    println("h $horizontal, d $depth -> $result")
}