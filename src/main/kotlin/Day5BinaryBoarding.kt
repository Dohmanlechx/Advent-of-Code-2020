import FileReader.inputFromDay

object Day5BinaryBoarding {
    fun solveFirstPart() = generateSeatIds().maxOrNull()

    fun solveSecondPart(): Int {
        val sortedSeatIds = generateSeatIds().sorted()
        val min = sortedSeatIds.first()
        val max = sortedSeatIds.last()

        for (i in min..max) {
            if (i !in sortedSeatIds) return i
        }

        return -1
    }

    private fun generateSeatIds(): List<Int> {
        val entries = inputFromDay(5)
        val seatIds = mutableListOf<Int>()

        for (entry in entries) {
            var row = -1
            var column = -1

            val frontBackOnly = entry.take(7)
            val leftRightOnly = entry.takeLast(3)

            var min = 0
            var max = 127

            for (char in frontBackOnly) {
                when (char) {
                    'F' -> max -= (max - min) / 2 + 1
                    'B' -> min += (max - min) / 2 + 1
                }

                row = max
            }

            min = 0
            max = 7

            for (char in leftRightOnly) {
                when (char) {
                    'L' -> max -= (max - min) / 2 + 1
                    'R' -> min += (max - min) / 2 + 1
                }

                column = max
            }

            seatIds.add((row * 8) + column)
        }

        return seatIds
    }
}