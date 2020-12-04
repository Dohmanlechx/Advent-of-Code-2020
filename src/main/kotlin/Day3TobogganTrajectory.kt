import FileReader.entriesFromDay

object Day3TobogganTrajectory {
    fun solveFirstPart() =
        countTrees(stepDown = 1, stepRight = 3)

    fun solveSecondPart() =
        countTrees(stepDown = 1, stepRight = 1) *
                countTrees(1, 3) *
                countTrees(1, 5) *
                countTrees(1, 7) *
                countTrees(2, 1)

    private fun countTrees(stepDown: Int, stepRight: Int): Long {
        val entries: List<List<Char>> = entriesFromDay(3).map(String::toList)

        val rowMaxIndex = entries.size - 1
        val columnMaxIndex = entries.first().size - 1

        var row = 0
        var column = 0
        var treeCount = 0L

        while (true) {
            row += stepDown
            column += stepRight

            if (row > rowMaxIndex) break
            if (column > columnMaxIndex) column %= (columnMaxIndex + 1)
            if (entries[row][column] == '#') treeCount++
        }

        return treeCount
    }
}