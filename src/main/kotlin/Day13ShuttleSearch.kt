import FileReader.inputFromDay

object Day13ShuttleSearch {
    private val nowTimestamp = inputFromDay(13)[0].toInt()

    private val busIds =
        inputFromDay(13)[1]
            .split(",")
            .filterNot { it == "x" }
            .map(String::toInt)

    fun solveFirstPart(): Int {
        var timestamp = 0

        while (true) {
            timestamp++
            for (busId in busIds) {
                if (timestamp % busId == 0 && timestamp >= nowTimestamp) {
                    return busId * (timestamp - nowTimestamp)
                }
            }
        }
    }
}