object FileReader {
    fun <T> T.entriesFromDay(date: Int) =
        this!!::class.java.getResource("day_$date")
            .readText()
            .split("\n")
            .map(String::trim)
}