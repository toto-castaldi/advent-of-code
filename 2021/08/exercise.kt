import java.io.File
import java.util.Arrays
import java.util.Collections

class Command {
    val size : Int

    constructor (coded : String) {
        size = coded.length
    }
}

class SevenSegmentDisplay {
    val size : Int
    val description : String

    constructor (coded : String) {
        size = coded.length
        description = coded
    }

    override fun toString() : String {
        return description
    }
}

fun main(args: Array<String>) {
    val fileName : String = args[0]
    val fileInput : File = File(fileName)
    var total1478 : Int = 0
    fileInput.forEachLine() { 
        //val input = it.split(" | ")[0]
        val output = it.split(" | ")[1]
        //val commands : List<Command> = input.split("\\s".toRegex()).map({ Command(it.trim()) })
        val displays : List<SevenSegmentDisplay> = output.split("\\s".toRegex()).map({ SevenSegmentDisplay(it.trim()) })
        total1478 += displays.filter { it.size == 2 }.size
        total1478 += displays.filter { it.size == 3 }.size
        total1478 += displays.filter { it.size == 4 }.size
        total1478 += displays.filter { it.size == 7 }.size
        print(displays)
        println(" -> $total1478")
    }
    println(total1478)
}
