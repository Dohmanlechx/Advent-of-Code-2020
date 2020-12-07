import FileReader.inputFromDay

object Day7HandyHaversacks {
    private val bags = inputFromDay(7)
    private val bagsMap = bags.mapIndexed { i, _ -> (parentBagOf(i) to childBagsOf(i)) }

    fun solveFirstPart() = bagsContainingGoldBag().size - 1

    private fun parentBagOf(index: Int) =
        bags.map { it.split("bags")[0] }[index]
            .filterNot(Char::isWhitespace)

    private fun childBagsOf(index: Int) =
        bags.map { it.split("contain")[1] }[index]
            .replace("bags", "")
            .replace("bag", "")
            .split(",")
            .map { it.filter(Char::isLetter) }

    private fun bagsContainingGoldBag(
        bagsToSearch: List<String> = listOf("shinygold"),
        bagsFound: List<String> = emptyList()
    ): List<String> {
        val bagsToSearchCopy = bagsToSearch.toMutableList()
        val bagsFoundCopy = bagsFound.toMutableList()

        return if (bagsToSearch.isEmpty()) {
            bagsFound
        } else {
            val bag = bagsToSearchCopy.first()

            bagsToSearchCopy.removeFirst()
            bagsFoundCopy.addIfAbsent(bag)

            for ((parent, children) in bagsMap) {
                if (children.contains(bag)) {
                    bagsToSearchCopy.addIfAbsent(parent)
                }
            }

            bagsContainingGoldBag(bagsToSearchCopy, bagsFoundCopy)
        }
    }

    private fun <T> MutableList<T>.addIfAbsent(element: T) {
        if (!contains(element)) add(element)
    }
}