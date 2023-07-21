class Aoc201904() {

    companion object {

        val validPassword1: (Int) -> Boolean = {
            val s = it.toString()
            var result = true

            if (s.length != 6) result = false

            if (result) {
                var same = false
                for (cIndex in 0..4) {
                    if (s[cIndex] == s[cIndex + 1]) same = true
                }
                if (!same) result = false
            }

            if (result) {
                var increase = true
                for (cIndex in 0..4) {
                    if (s[cIndex] > s[cIndex + 1]) increase = false
                }
                if (!increase) result = false
            }

            result
        }

        val validPassword2: (Int) -> Boolean = {
            val s = it.toString()
            var result = true

            if (s.length != 6) result = false

            if (result) {
                var same = false
                for (cIndex in 0..4) {
                    if (s[cIndex] == s[cIndex + 1]) {
                        same = cIndex < 4 && s[cIndex + 1] != s[cIndex + 2]
                    }
                }
                if (!same) result = false
            }

            if (result) {
                var increase = true
                for (cIndex in 0..4) {
                    if (s[cIndex] > s[cIndex + 1]) increase = false
                }
                if (!increase) result = false
            }

            result
        }

        private fun validPasswordsCount(intervalFrom: Int, intervalTo: Int, validPassword: (Int) -> Boolean): Int {
            var count = 0
            for (i in intervalFrom .. intervalTo) {
                if (validPassword(i)) {
                    println(i)
                    count ++
                }
            }
            return count
        }

        fun run1(intervalFrom : Int, intervalTo: Int) {
            println(validPasswordsCount(intervalFrom, intervalTo, validPassword1))
        }

        fun run2(intervalFrom : Int, intervalTo: Int) {
            println(validPasswordsCount(intervalFrom, intervalTo, validPassword2))
        }

    }



}
