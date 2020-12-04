object FileReader {
    fun <T> entries(thisClass: T, date: Int): List<String> =
        thisClass!!::class.java.getResource("day_$date")
            .readText()
            .split("\n")
            .map(String::trim)
}