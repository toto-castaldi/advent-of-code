package com.toto_castaldi.common

import kotlin.math.min
import kotlin.math.max

class Numbers {
    companion object {
        fun merged(theIntervals: Set<IntRange>, interval: IntRange): Set<IntRange> {
            val intervals = theIntervals.toMutableSet()
            val covering = intervals.filter {
                it.contains(interval.last) || it.contains(interval.first) || (interval.first <= it.first && interval.last >= it.last)
            }.toSet()
            if (covering.isNotEmpty()) {
                val min = intervals.minBy { it.first }
                val max = intervals.maxBy { it.last }
                intervals.removeAll(covering)
                intervals.add(min(min.first, interval.first) .. max(max.last, interval.last))
            } else {
                intervals.add(interval)
            }
            return intervals
        }
    }

}
