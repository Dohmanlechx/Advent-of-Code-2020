import FileReader.inputFromDay

/// Solution strongly inspired from Reddit

object Day14DockingData {
    private val memory: MutableMap<Long, Long> = mutableMapOf()
    private val input = inputFromDay(14)

    fun solveFirstPart(): Long {
        var mask = CharArray(36) { 'X' }.toString()

        input.forEach { instruction ->
            if (instruction.startsWith("mask")) {
                mask = instruction.substringAfter("= ")
            } else {
                val addressId = instruction
                    .substringAfter("[")
                    .substringBefore("]")
                    .toLong()

                val value = instruction.substringAfter("= ")

                memory[addressId] = value.maskedWith(mask)
            }
        }

        return memory.values.sum()
    }
}

private fun String.maskedWith(mask: String): Long =
    this.toBinary()
        .zip(mask)
        .map { (valueChar, maskChar) ->
            maskChar.takeUnless { it == 'X' } ?: valueChar
        }.joinToString("")
        .toLong(2)

private fun String.toBinary(): String =
    this.toLong()
        .toString(2)
        .padStart(36, '0')