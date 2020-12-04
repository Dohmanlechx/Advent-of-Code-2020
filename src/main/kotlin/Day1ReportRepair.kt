object Day1ReportRepair {
    fun solveFirstPart(): Int {
        val numbers: List<Int> = FileReader.entries(this, "day_01").map(String::toInt)

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
        val numbers: List<Int> = FileReader.entries(this, "day_01").map(String::toInt)

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
}