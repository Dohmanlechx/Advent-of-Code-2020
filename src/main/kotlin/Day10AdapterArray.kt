import FileReader.inputFromDay

object Day10AdapterArray {
    private val adapters = inputFromDay(10).map(String::toInt).sorted()

    fun solveFirstPart(): Int {
        var oneStepDiffs = 1
        var threeStepsDiffs = 1

        for (i in adapters.indices) {
            if (i == adapters.size - 1) break

            when (adapters[i + 1] - adapters[i]) {
                1 -> oneStepDiffs++
                3 -> threeStepsDiffs++
            }
        }

        return oneStepDiffs * threeStepsDiffs
    }
}