import FileReader.inputFromDay

typealias Parent = String
typealias Child = String
typealias Children = List<String>

object Day07HandyHaversacks {
    private const val SHINY_GOLD = "shinygold"

    private val bags = inputFromDay(7)
    private val bagsMap = mutableMapOf<Parent, Children>()

    init {
        for (i in bags.indices) {
            bagsMap[parentBagOf(i)] = childBagsOf(i)
        }
    }

    fun solveFirstPart() = bagsWithGoldBagCount() - 1
    fun solveSecondPart() = bagChildCount() - 1

    private fun parentBagOf(index: Int) =
        bags.map { it.split("bags")[0] }[index]
            .filterNot(Char::isWhitespace)

    private fun childBagsOf(index: Int) =
        bags.map { it.split("contain")[1] }[index]
            .replace("bags", "")
            .replace("bag", "")
            .split(",")
            .map { it.filter(Char::isLetterOrDigit) }

    private fun bagsWithGoldBagCount(
        bagsToSearch: List<Parent> = listOf(SHINY_GOLD),
        bagsFound: List<Parent> = emptyList()
    ): Int {
        val bagsToSearchCopy = bagsToSearch.toMutableList()
        val bagsFoundCopy = bagsFound.toMutableList()

        return if (bagsToSearch.isEmpty()) {
            bagsFound.size
        } else {
            val bag = bagsToSearchCopy.first()

            bagsToSearchCopy.removeFirst()
            bagsFoundCopy.addIfAbsent(bag)

            for ((parent, children) in bagsMap) {
                if (children.removeAmount().contains(bag)) {
                    bagsToSearchCopy.addIfAbsent(parent)
                }
            }

            bagsWithGoldBagCount(bagsToSearchCopy, bagsFoundCopy)
        }
    }

    private fun bagChildCount(parent: Parent = SHINY_GOLD): Int {
        var count = 1

        bagsMap[parent]?.let { children ->
            for (child in children) {
                val amount = child.amountOrZero()
                val bag = child.filterNot(Char::isDigit)
                count += amount * bagChildCount(parent = bag)
            }
        }

        return count
    }

    private fun MutableList<Parent>.addIfAbsent(element: Parent) {
        if (!contains(element)) add(element)
    }

    private fun Child.amountOrZero() =
        filter(Char::isDigit).takeIf(String::isNotBlank)?.toInt() ?: 0

    private fun Children.removeAmount() =
        map { it.filter(Char::isLetter) }
}