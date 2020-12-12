import FileReader.inputFromDay

object Day11SeatingSystem {
    enum class Part { FIRST, SECOND }

    private val seatRows = inputFromDay(11)

    private val xSize = seatRows.size
    private val ySize = seatRows.first().length

    private lateinit var seatGrid: Array<Array<Char>>

    fun solve(part: Part): Int {
        seatGrid =
            Array(xSize) { Array(ySize) { '*' } }.also {
                for (i in 0 until xSize) {
                    for (j in 0 until ySize) {
                        it[i][j] = seatRows[i][j]
                    }
                }
            }

        while (true) {
            val newSeatGrid = copyOfSeatGrid()

            for (i in 0 until xSize) {
                for (j in 0 until ySize) {
                    when (seatGrid[i][j]) {
                        'L' -> if (isNoOccupiedRelevantSeats(i, j, part)) newSeatGrid[i][j] = '#'
                        '#' -> if (isTooManyOccupiedRelevantSeats(i, j, part)) newSeatGrid[i][j] = 'L'
                    }
                }
            }

            if (isEqual(seatGrid, newSeatGrid)) break

            seatGrid = newSeatGrid
        }

        return occupiedSeatsCount()
    }

    private fun copyOfSeatGrid() =
        Array(xSize) { Array(ySize) { '*' } }.also {
            for (i in 0 until xSize) {
                for (j in 0 until ySize) {
                    it[i][j] = seatGrid[i][j]
                }
            }
        }

    private fun occupiedSeatsCount(): Int {
        var count = 0

        for (i in 0 until xSize) {
            for (j in 0 until ySize) {
                if (seatGrid[i][j] == '#') count++
            }
        }

        return count
    }

    private fun isNoOccupiedRelevantSeats(i: Int, j: Int, part: Part): Boolean {
        coordinatesToCheck(i, j, part).forEach {
            try {
                if (seatGrid[it.first][it.second] == '#') {
                    return false
                }
            } catch (e: ArrayIndexOutOfBoundsException) {
                // Do nothing, continue the loop
            }
        }

        return true
    }

    private fun isTooManyOccupiedRelevantSeats(i: Int, j: Int, part: Part): Boolean {
        var occupiedSeats = 0

        coordinatesToCheck(i, j, part).forEach {
            try {
                if (seatGrid[it.first][it.second] == '#') occupiedSeats++
            } catch (e: ArrayIndexOutOfBoundsException) {
                // Do nothing, continue the loop
            }
        }

        return if (part == Part.FIRST) occupiedSeats >= 4 else occupiedSeats >= 5
    }

    private fun coordinatesToCheck(i: Int, j: Int, part: Part): List<Pair<Int, Int>> {
        if (part == Part.FIRST) {
            return mutableListOf<Pair<Int, Int>>().apply {
                // Upper row
                add(Pair(i - 1, j - 1))
                add(Pair(i - 1, j))
                add(Pair(i - 1, j + 1))

                // Same row
                add(Pair(i, j - 1))
                add(Pair(i, j + 1))

                // Lower row
                add(Pair(i + 1, j - 1))
                add(Pair(i + 1, j))
                add(Pair(i + 1, j + 1))
            }
        } else {
            val coordinates = mutableListOf<Pair<Int, Int>>()

            // Left
            var count = 1
            while (true) {
                try {
                    val seat = seatGrid[i][j - count]

                    if (seat == '#' || seat == 'L') {
                        coordinates.add(Pair(i, j - count))
                        break
                    }

                    count++
                } catch (e: ArrayIndexOutOfBoundsException) {
                    break
                }
            }

            // Left Upper
            count = 1
            while (true) {
                try {
                    val seat = seatGrid[i - count][j - count]

                    if (seat == '#' || seat == 'L') {
                        coordinates.add(Pair(i - count, j - count))
                        break
                    }

                    count++
                } catch (e: ArrayIndexOutOfBoundsException) {
                    break
                }
            }

            // Middle Upper
            count = 1
            while (true) {
                try {
                    val seat = seatGrid[i - count][j]

                    if (seat == '#' || seat == 'L') {
                        coordinates.add(Pair(i - count, j))
                        break
                    }

                    count++
                } catch (e: ArrayIndexOutOfBoundsException) {
                    break
                }
            }

            // Right Upper
            count = 1
            while (true) {
                try {
                    val seat = seatGrid[i - count][j + count]

                    if (seat == '#' || seat == 'L') {
                        coordinates.add(Pair(i - count, j + count))
                        break
                    }

                    count++
                } catch (e: ArrayIndexOutOfBoundsException) {
                    break
                }
            }

            // Right
            count = 1
            while (true) {
                try {
                    val seat = seatGrid[i][j + count]

                    if (seat == '#' || seat == 'L') {
                        coordinates.add(Pair(i, j + count))
                        break
                    }

                    count++
                } catch (e: ArrayIndexOutOfBoundsException) {
                    break
                }
            }

            // Right Lower
            count = 1
            while (true) {
                try {
                    val seat = seatGrid[i + count][j + count]

                    if (seat == '#' || seat == 'L') {
                        coordinates.add(Pair(i + count, j + count))
                        break
                    }

                    count++
                } catch (e: ArrayIndexOutOfBoundsException) {
                    break
                }
            }

            // Middle Lower
            count = 1
            while (true) {
                try {
                    val seat = seatGrid[i + count][j]

                    if (seat == '#' || seat == 'L') {
                        coordinates.add(Pair(i + count, j))
                        break
                    }

                    count++
                } catch (e: ArrayIndexOutOfBoundsException) {
                    break
                }
            }

            // Left Lower
            count = 1
            while (true) {
                try {
                    val seat = seatGrid[i + count][j - count]

                    if (seat == '#' || seat == 'L') {
                        coordinates.add(Pair(i + count, j - count))
                        break
                    }

                    count++
                } catch (e: ArrayIndexOutOfBoundsException) {
                    break
                }
            }

            return coordinates
        }
    }

    private fun <T> isEqual(first: Array<Array<T>>, second: Array<Array<T>>): Boolean {
        for (i in first.indices) {
            if (first[i].size != second[i].size) return false
            for (j in first[i].indices) {
                if (!first[i][j]?.equals(second[i][j])!!) return false
            }
        }

        return true
    }
}