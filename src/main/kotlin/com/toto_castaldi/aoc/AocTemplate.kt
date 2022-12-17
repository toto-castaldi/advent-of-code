import java.io.File

class AocTemplate() {

    companion object {
        fun run1(fileName: String) {
            File(fileName).readLines().forEach { println(it) }
        }
    }

}