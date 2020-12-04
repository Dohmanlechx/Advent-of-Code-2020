object Day2PasswordPhilosophy {
    fun solveFirstPart(): Int {
        val entries = FileReader.entries(this, 2)

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

    fun solveSecondPart(): Int {
        val entries = FileReader.entries(this, 2)

        var validPasswordsCount = 0

        entries.forEach { entry ->
            var firstDigit: Int
            var secondDigit: Int

            entry.filterNot(Char::isLetter).split("-").let {
                firstDigit = it[0].filter(Char::isDigit).toInt() - 1
                secondDigit = it[1].filter(Char::isDigit).toInt() - 1
            }

            val charRequiredAndPassword = entry.replace("-", "").filterNot(Char::isDigit).trimStart()
            val charRequired = charRequiredAndPassword[0]
            val password = charRequiredAndPassword.substring(3)

            if (
                password[firstDigit] == charRequired && password[secondDigit] != charRequired ||
                password[firstDigit] != charRequired && password[secondDigit] == charRequired
            ) {
                validPasswordsCount++
            }
        }

        return validPasswordsCount
    }
}