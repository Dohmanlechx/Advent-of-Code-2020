import FileReader.inputFromDay

typealias Ticket = List<Int>

object Day16TicketTranslation {
    data class Category(
        var fieldIndex: Int = -1,
        val name: String,
        val lowRange: IntRange,
        val highRange: IntRange
    )

    fun solveFirstPart(): Int =
        nearbyTickets.fold(0, { total, ticket -> total + ticket.invalidFields().sum() })

    fun solveSecondPart(): Long {
        val validTickets = nearbyTickets.filter { it.isValid() }

        while (categories.any { it.fieldIndex == -1 }) {
            for (category in categories) {
                for (i in validTickets.first().indices) {
                    if (categories.any { it.fieldIndex == i }) continue
                    if (validTickets.all { ticket -> isInRange(ticket[i], category) }) {
                        category.fieldIndex = i
                        break
                    }
                }
            }
        }

        return categories
            .filter { category -> category.name.startsWith("departure") }
            .fold(1L, { total, category -> total * myTicket[category.fieldIndex] })
    }

    private val categories: List<Category> =
        mutableListOf<Category>().also { categories ->
            inputFromDay(16, splitBy = "\n\r")
                .first()
                .split("\n")
                .forEach { row ->
                    categories.add(
                        Category(
                            name = row.substringBefore(":"),
                            lowRange = row.substringAfter(": ").substringBefore(" or").toIntRange(),
                            highRange = row.substringAfter("or ").toIntRange(),
                        )
                    )
                }
        }

    private val myTicket: Ticket =
        inputFromDay(16, splitBy = "your ticket:")
            .last()
            .split("\n\r")
            .first()
            .split(",")
            .map { it.filter(Char::isDigit).toInt() }


    private val nearbyTickets: List<Ticket> =
        inputFromDay(16, splitBy = "nearby tickets:")
            .last()
            .split("\r\n")
            .map { row -> row.split(",").map { it.toInt() } }

    private fun isInRange(field: Int, category: Category): Boolean =
        field in category.lowRange || field in category.highRange

    private fun Ticket.isValid(): Boolean {
        for (field in this) {
            if (!categories.any { category -> isInRange(field, category) }) return false
        }
        return true
    }

    private fun Ticket.invalidFields(): List<Int> =
        filterNot { field -> categories.any { category -> isInRange(field, category) } }

    private fun String.toIntRange(): IntRange =
        substringBefore("-").filter(Char::isDigit).toInt()..substringAfter("-").filter(Char::isDigit).toInt()
}