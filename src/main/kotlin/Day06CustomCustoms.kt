import FileReader.inputFromDay

object Day06CustomCustoms {
    fun solveFirstPart() =
        inputFromDay(6, splitBy = "\n\r")
            .map { it.toSet().filter(Char::isLetter).size }
            .sum()

    fun solveSecondPart() =
        inputFromDay(6, splitBy = "\n\r")
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