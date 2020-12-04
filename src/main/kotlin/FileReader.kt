object FileReader {
    fun <T> entries(thisClass: T, path: String): List<String> =
        thisClass!!::class.java.getResource(path)
            .readText()
            .split("\n")
            .map(String::trim)
}