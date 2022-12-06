import java.io.File
import java.nio.charset.Charset

fun main(args: Array<String>) {
    val file = File(args[0])
    println(markerPos(file, 4))
    println(markerPos(file, 14))
}

private fun markerPos(file: File, len: Int) : Int {
    var pos = 0
    val buffer = mutableListOf<Int>()
    val reader = file.reader(Charset.defaultCharset())
    var car = reader.read()
    while (car != -1) {
        pos ++
        buffer.add(car)
        if (buffer.size > len) {
            buffer.removeFirst()
        }
        if (buffer.toSet().size == len) {
            return pos
        }
        car = reader.read()
    }
    return -1
}


