import FileReader.entriesFromDay

object Day2PasswordPhilosophy {
    enum class Part { FIRST, SECOND }

    fun solve(part: Part): Int {
        val entries: List<String> = entriesFromDay(2)

        var validPasswordsCount = 0

        entries.forEach { entry ->
            var firstDigit: Int
            var secondDigit: Int

            entry.filterNot(Char::isLetter).split("-").let {
                firstDigit = it[0].filter(Char::isDigit).toInt()
                secondDigit = it[1].filter(Char::isDigit).toInt()
            }

            val charRequiredAndPassword = entry.replace("-", "").filterNot(Char::isDigit).trimStart()
            val charRequired = charRequiredAndPassword[0]
            val password = charRequiredAndPassword.substring(3)

            when (part) {
                Part.FIRST -> {
                    if (password.filter { it == charRequired }.length in firstDigit..secondDigit) {
                        validPasswordsCount++
                    }
                }
                Part.SECOND -> {
                    if (
                        password[firstDigit - 1] == charRequired && password[secondDigit - 1] != charRequired ||
                        password[firstDigit - 1] != charRequired && password[secondDigit - 1] == charRequired
                    ) {
                        validPasswordsCount++
                    }
                }
            }
        }

        return validPasswordsCount
    }
}