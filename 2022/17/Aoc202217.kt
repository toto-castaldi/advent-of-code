import java.io.File

class Aoc202217() {

    companion object {
        fun run1(fileName: String) {
            File(fileName).readLines().forEach { println(it) }
        }
    }

}