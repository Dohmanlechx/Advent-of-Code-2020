import FileReader.entriesFromDay

object Day4PassportProcessing {
    fun solveFirstPart(): Int {
        return entriesFromDay(4, splitBy = "\n\r")
            .map(String::toPassport)
            .filter(Passport::isValid)
            .size
    }

    fun solveSecondPart(): Int {
        return entriesFromDay(4, splitBy = "\n\r")
            .map(String::toPassport)
            .filter(Passport::isValidPartTwo)
            .size
    }
}

fun String.toPassport(): Passport {
    val attributes = this.replace(" ", "\r\n").split("\r\n")

    fun List<String>.findAttribute(attr: String) =
        this.find { it.contains(attr) }?.split(":")?.last()

    return Passport(
        attributes.findAttribute("byr"),
        attributes.findAttribute("iyr"),
        attributes.findAttribute("eyr"),
        attributes.findAttribute("hgt"),
        attributes.findAttribute("hcl"),
        attributes.findAttribute("ecl"),
        attributes.findAttribute("pid"),
        attributes.findAttribute("cid"),
    )
}

fun String.toIntOrZero() = this.filter(Char::isDigit).toIntOrNull() ?: 0

data class Passport(
    val birthYear: String?,
    val issueYear: String?,
    val expirationYear: String?,
    val height: String?,
    val hairColor: String?,
    val eyeColor: String?,
    val passportID: String?,
    val countryID: String?
) {
    fun isValid(): Boolean {
        return !birthYear.isNullOrBlank() &&
                !issueYear.isNullOrBlank() &&
                !expirationYear.isNullOrBlank() &&
                !height.isNullOrBlank() &&
                !hairColor.isNullOrBlank() &&
                !eyeColor.isNullOrBlank() &&
                !passportID.isNullOrBlank()
    }

    fun isValidPartTwo(): Boolean {
        return (birthYear?.toIntOrZero() in 1920..2002) &&
                (issueYear?.toIntOrZero() in 2010..2020) &&
                (expirationYear?.toIntOrZero() in 2020..2030) &&
                (height?.isValidHeight() == true) &&
                (hairColor?.isValidHairColor() == true) &&
                (eyeColor?.isValidEyeColor() == true) &&
                (passportID?.isValidPassportID() == true)
    }

    private fun String.isValidHeight(): Boolean {
        return when {
            this.contains("cm") -> this.filter(Char::isDigit).toInt() in 150..193
            this.contains("in") -> this.filter(Char::isDigit).toInt() in 59..76
            else -> false
        }
    }

    private fun String.isValidHairColor() =
        this.startsWith("#") && this.filter(Char::isLetterOrDigit).length == 6

    private fun String.isValidEyeColor(): Boolean {
        var validColorsCount = 0

        if (this.contains("amb")) validColorsCount++
        if (this.contains("blu")) validColorsCount++
        if (this.contains("brn")) validColorsCount++
        if (this.contains("gry")) validColorsCount++
        if (this.contains("grn")) validColorsCount++
        if (this.contains("hzl")) validColorsCount++
        if (this.contains("oth")) validColorsCount++

        return validColorsCount == 1
    }

    private fun String.isValidPassportID() =
        this.filter(Char::isDigit).length == 9
}