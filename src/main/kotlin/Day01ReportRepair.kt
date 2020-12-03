object Day01ReportRepair {
    fun solveFirstPart(): Int {
        val numbers: List<Int> = readNumbersFromFile()

        numbers.forEachIndexed { i, n1 ->
            val numbersWithoutCurrent = numbers.toMutableList().apply { removeAt(i) }

            numbersWithoutCurrent.forEach { n2 ->
                if (n1 + n2 == 2020) {
                    return n1 * n2
                }
            }
        }

        return -1
    }

    fun solveSecondPart(): Int {
        val numbers: List<Int> = readNumbersFromFile()

        numbers.forEachIndexed { i, _ ->
            numbers.forEachIndexed { j, _ ->
                numbers.forEachIndexed { k, _ ->
                    if (numbers[i] + numbers[j] + numbers[k] == 2020) {
                        return numbers[i] * numbers[j] * numbers[k]
                    }
                }
            }
        }

        return -1
    }

    private fun readNumbersFromFile() =
        this::class.java.getResource("day_01")
            .readText()
            .split("\n")
            .map { it.trim().toInt() }
}