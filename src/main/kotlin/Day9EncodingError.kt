import FileReader.inputFromDay

object Day9EncodingError {
    private val numbers = inputFromDay(9).map(String::toLong)
    private const val preambleLength = 25

    fun solveFirstPart(): Long {
        for (i in preambleLength until numbers.size) {
            for (j in 1 until preambleLength) {
                when {
                    canFindNumbersToSum(i, j) -> break
                    else -> if (j == preambleLength - 1) return numbers[i]
                }
            }
        }

        return -1
    }

    fun solveSecondPart(): Long {
        val invalidNumber = solveFirstPart()
        val endIndex = numbers.indexOf(invalidNumber)
        val contiguousNumbers = mutableListOf<Long>()

        for (i in 0 until endIndex) {
            contiguousNumbers.clear()
            for (j in (i + 1) until endIndex) {
                contiguousNumbers.add(numbers[j])
                if (contiguousNumbers.sum() == invalidNumber) {
                    return contiguousNumbers.minOrNull()!! + contiguousNumbers.maxOrNull()!!
                }
            }
        }

        return -1
    }

    private fun canFindNumbersToSum(i: Int, j: Int) =
        numbers.subList(i - preambleLength, i - 1).any { numbers[i - j] + it == numbers[i] }
}