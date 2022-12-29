package com.toto_castaldi.common.algo

/**
 * W work in progress
 * S work step
 * O step option
 */
abstract class BackTrackProblem<W, S, O> {

    val solutions = mutableListOf<W>()

    fun solve() {
        val workInProgress = currentResolution()
        if (isComplete(workInProgress)) {
            solutions.add(workInProgress)
        } else {
            for (step in nextSteps()) {
                for (stepOption in stepOptions(step)) {
                    if (isValid(step, stepOption)) {
                        applyStep(step, stepOption)
                        solve()
                        revertStep(step)
                    }
                }
                return
            }
        }
    }

    abstract fun stepOptions(v: S): Collection<O>
    abstract fun isValid(nextMove: S, option: O): Boolean
    abstract fun revertStep(nextMove: S)
    abstract fun applyStep(nextMove: S, option: O)
    abstract fun nextSteps(): Sequence<S>
    abstract fun isComplete(workInProgress: W): Boolean
    abstract fun currentResolution(): W

}
