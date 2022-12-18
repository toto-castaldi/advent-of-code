package com.toto_castaldi.common

import kotlin.math.min
import kotlin.math.max

class Numbers {
    companion object {
        fun merged(intervals: Set<IntRange>, interval: IntRange): Set<IntRange> {
            val result = intervals.toMutableSet()
            val covering = result.filter {
                it.contains(interval.last) || it.contains(interval.first) || (interval.first <= it.first && interval.last >= it.last)
            }.toSet()
            if (covering.isNotEmpty()) {
                val min = covering.minBy { it.first }
                val max = covering.maxBy { it.last }
                result.removeAll(covering)
                result.add(min(min.first, interval.first) .. max(max.last, interval.last))
            } else {
                result.add(interval)
            }
            return result
        }

        fun freeSpots(intervals: Set<IntRange>): Set<Int> {
            val result = mutableSetOf<Int>()
            val sortedBy = intervals.toList().sortedBy { it.first }
            for (i in 0 until  sortedBy.size - 1) {
                val first = sortedBy[i]
                val second = sortedBy[i+1]
                if (first.last < second.first) {
                    result.addAll(first.last + 1 until second.first)
                }
            }
            return result
        }

        val includes = { big: IntRange, small: IntRange ->  big.first <= small.first && big.last >= small.last }
    }

}
