import FileReader.inputFromDay

object Day13ShuttleSearch {
    data class Bus(
        val id: Int,
        var departures: MutableList<Long> = mutableListOf(0),
        var step: Long = -1
    )

    private val nowTimestamp = inputFromDay(13)[0].toInt()

    private val buses = inputFromDay(13)[1]
        .split(",")
        .mapIndexedNotNull { index, str ->
            if (str == "x") null else Bus(id = str.toInt(), step = index.toLong())
        }

    fun solveFirstPart(): Int {
        var timestamp = 0

        while (true) {
            timestamp++
            for (bus in buses) {
                if (timestamp % bus.id == 0 && timestamp >= nowTimestamp) {
                    return bus.id * (timestamp - nowTimestamp)
                }
            }
        }
    }

    // This only solves the examples, not the actual input, I gave up
    // Example of solution: https://github.com/tginsberg/advent-2020-kotlin/blob/main/src/main/kotlin/com/ginsberg/advent2020/Day13.kt
    fun solveSecondPart(): Long {
        var timestamp = 0L

        while (!isValidByRequirements(timestamp)) {
            timestamp++
            for (bus in buses) {
                if (timestamp % (bus.id) == 0L) {
                    if (bus.departures.size >= 3) {
                        bus.departures = bus.departures.takeLast(3).toMutableList()
                    }
                    bus.departures.add(timestamp)
                }
            }
        }

        return buses.first().departures.last()
    }

    private fun isValidByRequirements(timestamp: Long): Boolean {
        for (i in (buses.size - 1) downTo 1) {

            if (i == 1) {
                var index = 0
                while (buses.filter { it.departures.contains(timestamp) }.size > 1) {
                    buses[index].departures.removeLast()
                    index++
                }
            }

            val lastDepartureOfThisBus = buses[i].departures.last()
            val lastDepartureOfPreviousBus = buses[i - 1].departures.last()
            val stepOfThisBus = buses[i].step
            val stepOfPreviousBus = buses[i - 1].step

            if (lastDepartureOfThisBus - lastDepartureOfPreviousBus != stepOfThisBus - stepOfPreviousBus) {
                return false
            }
        }

        return true
    }
}