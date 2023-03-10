import com.toto_castaldi.common.Numbers

class Aoc202225 {
    companion object {
        fun decimalToSNAFU(decimal: Long): String {
            var index = 0
            while (fivePowTo(index) < decimal) {
                index++
            }

            for (first in listOf('1', '2')) {
                for (i in 1 .. fivePowTo(index - 1)) {
                    val decimalToBase5 = Numbers.decimalToBase5(i).padStart(index - 1, '0')
                    val snafu = first + convert(decimalToBase5)
                    if (SNAFUToDecimal(snafu) == decimal) {
                        return snafu
                    }

                }
            }
            throw Error("not found !!!")
        }

        private fun convert(base5: String): String {
            val map = listOf('2', '1', '-', '=', '0')
            var result = ""
            for (c in base5) {
                result += map[c.digitToInt()]
            }
            return result
        }

        fun SNAFUToDecimal(snafu: String): Long {
            var result : Long = 0
            for ((pos, c) in snafu.toCharArray().toList().withIndex()) {
                val expPos = fivePowTo(snafu.length - pos - 1)
                result += when (c) {
                    '2' -> 2 * expPos
                    '1' -> expPos
                    '-' -> -1 * expPos
                    '=' -> -2 * expPos
                    else -> {
                        0
                    }
                }
            }
            return result
        }

        private fun fivePowTo(pos: Int): Long {
            return Math.ceil(Math.pow(5.toDouble(), pos.toDouble())).toLong()
        }
    }

}
