import java.io.File

fun main() {
    var horizontal1 = 0
    var depth1 = 0
    var horizontal2 = 0
    var depth2 = 0
    var aim = 0
    File("input-1.txt").forEachLine { 
        val splitted = it.split("\\s".toRegex()).toTypedArray()
        val direction = splitted[0]
        val amount = splitted[1].toInt()
        when (direction) {
            "up" -> { 
                depth1 -= amount
                aim -= amount
            }
            "down" -> { 
                depth1 += amount
                aim += amount
            }
            "forward" -> {
                horizontal1 += amount
                horizontal2 += amount
                depth2 += aim * amount
            }
            else -> { 
                print("unknow direction $direction")
            }
        }
    }
    val result1 = horizontal1 * depth1
    println("h $horizontal1, d $depth1 -> $result1")
    val result2 = horizontal2 * depth2
    println("h $horizontal2, d $depth2 -> $result2")
}