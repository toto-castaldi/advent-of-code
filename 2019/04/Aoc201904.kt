class Aoc201904() {

    companion object {
        fun run1(intervalFrom : Int, intervalTo: Int) {
            val aoc = Aoc201904()
            println(aoc.validPasswordsCount(intervalFrom, intervalTo))
        }

    }

    private fun validPasswordsCount(intervalFrom: Int, intervalTo: Int): Int {
        var count = 0
        for (i in intervalFrom .. intervalTo) {
            if (validPassword(i)) {
                count ++
            }
        }
        return count
    }

    private fun validPassword(i: Int): Boolean {
        val s = i.toString()
        if (s.length != 6) return false

        var same = false
        for (cIndex in 0 .. 4) {
            if (s[cIndex] == s[cIndex + 1]) same = true
        }
        if (!same) return false

        var increase = true
        for (cIndex in 0 .. 4) {
            if (s[cIndex] > s[cIndex + 1]) increase = false
        }
        if (!increase) return false

        return true
    }

}
