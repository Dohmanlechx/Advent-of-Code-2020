import FileReader.inputFromDay

object Day15RambunctiousRecitation {
    fun solve(untilTurn: Int): Int {
        val startingNumbers: List<Int> =
            inputFromDay(15)[0].split(",").map(String::toInt)

        val memory: HashMap<Int, Int> =
            HashMap<Int, Int>().also { startingNumbers.forEachIndexed { index, i -> it[i] = index + 1 } }

        var lastNumber = startingNumbers.last()

        for (turn in (startingNumbers.size + 1)..untilTurn) {
            val maybeIndexOfLastTurnSpoken = memory[lastNumber] ?: -1
            memory[lastNumber] = turn - 1
            lastNumber = if (maybeIndexOfLastTurnSpoken == -1) 0 else turn - maybeIndexOfLastTurnSpoken - 1
        }

        return lastNumber
    }
}