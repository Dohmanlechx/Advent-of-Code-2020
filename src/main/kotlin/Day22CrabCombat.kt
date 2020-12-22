import FileReader.inputFromDay

typealias Card = Int

object Day22CrabCombat {
    fun solveFirstPart(): Int {
        val p1Cards: MutableList<Card> = generateCardsOfPlayer(1)
        val p2Cards: MutableList<Card> = generateCardsOfPlayer(2)

        while (p1Cards.isNotEmpty() && p2Cards.isNotEmpty()) {
            val p1 = p1Cards.removeAt(0)
            val p2 = p2Cards.removeAt(0)
            val roundWinner = if (p1 > p2) p1Cards else p2Cards
            roundWinner.stackBehind(p1, p2)
        }

        val winner = if (p1Cards.isNotEmpty()) p1Cards else p2Cards

        return winner.reversed().foldIndexed(0, { index, total, card ->
            total + (card * (index + 1))
        })
    }

    private fun generateCardsOfPlayer(i: Int): MutableList<Card> =
        inputFromDay(22, splitBy = "\n\r")[i - 1]
            .split("\n")
            .drop(1)
            .map { it.filter(Char::isDigit).toInt() }
            .toMutableList()

    private fun MutableList<Card>.stackBehind(vararg elements: Card) {
        elements.sortedDescending().forEach { add(it) }
    }
}