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
            for (stepIdentifier in stepIdentifiers()) {
                if (isNewStep(stepIdentifier)) {
                    for (stepOption in stepOptions(stepIdentifier)) {
                        if (isValid(stepIdentifier, stepOption)) {
                            applyStep(stepIdentifier, stepOption)
                            solve()
                            revertStep(stepIdentifier)
                        }
                    }
                    return
                }
            }
        }
    }

    abstract fun isNewStep(step: S): Boolean
    abstract fun stepOptions(v: S): Collection<O>
    abstract fun isValid(nextMove: S, option: O): Boolean
    abstract fun revertStep(nextMove: S)
    abstract fun applyStep(nextMove: S, option: O)
    abstract fun stepIdentifiers(): Sequence<S>
    abstract fun isComplete(workInProgress: W): Boolean
    abstract fun currentResolution(): W

}
