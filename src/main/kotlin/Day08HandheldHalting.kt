import FileReader.inputFromDay

object Day08HandheldHalting {
    data class Operation(
        var action: String,
        var steps: Int,
        var alreadyRun: Boolean = false
    )

    private val originalOperations: List<Operation>
        get() = inputFromDay(8).mapToOperations()

    fun solveFirstPart() =
        runProgram(originalOperations, getAccumulatorEvenIfCrash = true)

    fun solveSecondPart(): Int {
        var iterator = 0
        var accumulator = -1

        while (accumulator < 0) {
            val operations = originalOperations

            operations[iterator].let {
                when (it.action) {
                    "nop" -> operations[iterator].action = "jmp"
                    "jmp" -> operations[iterator].action = "nop"
                }
            }

            iterator++
            accumulator = runProgram(operations)
        }

        return accumulator
    }

    private fun runProgram(
        operations: List<Operation>,
        getAccumulatorEvenIfCrash: Boolean = false
    ): Int {
        var iterator = 0
        var accumulator = 0

        while (true) {
            if (operations.last().alreadyRun) return accumulator

            operations[iterator].let {
                if (it.alreadyRun) {
                    return if (getAccumulatorEvenIfCrash) accumulator else -1
                }

                when (it.action) {
                    "acc" -> {
                        accumulator += it.steps
                        iterator++
                    }
                    "jmp" -> {
                        iterator += it.steps
                    }
                    "nop" -> {
                        iterator++
                    }
                }

                it.alreadyRun = true
            }
        }
    }

    private fun List<String>.mapToOperations() =
        map { Operation(action = it.take(3), steps = it.split(" ").last().toInt()) }
}