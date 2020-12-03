object Day2PasswordPhilosophy {
    fun solveFirstPart(): Int {
        val entries = readPasswordsFromFile()

        var validPasswordsCount = 0

        entries.forEach { entry ->
            var min: Int
            var max: Int

            entry.filterNot(Char::isLetter).split("-").let {
                min = it[0].filter(Char::isDigit).toInt()
                max = it[1].filter(Char::isDigit).toInt()
            }

            val charRequiredAndPassword = entry.replace("-", "").filterNot(Char::isDigit).trimStart()
            val charRequired = charRequiredAndPassword[0]
            val password = charRequiredAndPassword.substring(3)

            if (password.filter { it == charRequired }.length in min..max) {
                validPasswordsCount++
            }
        }

        return validPasswordsCount
    }

    private fun readPasswordsFromFile() =
        this::class.java.getResource("day_02")
            .readText()
            .split("\n")
}