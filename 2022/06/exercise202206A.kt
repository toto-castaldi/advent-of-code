import java.io.File

fun main(args: Array<String>) {
    test()
    val input = File(args[0]).readLines().first()
    println( markerPos(input, 4))
    println( markerPos(input, 14))
}

private fun markerPos(input: String, length : Int): Int {
    for (i in 0 until input.length - length - 1) {
        val packet = input.substring(i, i + length)
        if (packet.toSet().size == length ) {
            return  i + length
        }
    }
    return -1
}

private fun test() {
    assert(19 == markerPos("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 14))
    assert(23 == markerPos("bvwbjplbgvbhsrlpgdmjqwftvncz", 14))
    assert(23 == markerPos("nppdvjthqldpwncqszvftbrmjlhg", 14))
    assert(29 == markerPos("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 14))
    assert(26 == markerPos("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 14))
}

