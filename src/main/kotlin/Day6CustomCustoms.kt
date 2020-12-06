import FileReader.entriesFromDay

object Day6CustomCustoms {
    fun solveFirstPart() =
        entriesFromDay(6, splitBy = "\n\r")
            .map { it.toSet().filter(Char::isLetter).size }
            .sum()

    fun solveSecondPart() =
        entriesFromDay(6, splitBy = "\n\r")
            .map { entry ->
                val amountPeople = entry.split("\n").size
                var count = 0
                for (char in entry.toSet()) {
                    if (entry.filter { it == char }.length == amountPeople) count++
                }
                count
            }
            .sum()

}