import java.io.File

class Aoc202213 {

    fun run(fileName: String) {
        println(File(fileName).readLines().first())
    }

}
