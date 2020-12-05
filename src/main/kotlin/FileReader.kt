object FileReader {
    fun <T> T.entriesFromDay(date: Int, splitBy: String = "\n") =
        this!!::class.java.getResource("day_$date")
            .readText()
            .split(splitBy)
            .map(String::trim)
}