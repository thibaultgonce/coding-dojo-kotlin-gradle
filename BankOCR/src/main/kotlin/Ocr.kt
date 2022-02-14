class Ocr {
    private val lineLength = 28
    private val endOfLine1Index = lineLength - 1
    private val endOfLine2Index = 2 * (lineLength - 1)

    private fun String.substring3Chars(startIndex: Int) = this.substring(startIndex, startIndex + 3)

    fun parse(entry: String): List<Int?> = entry
        .replace(Regex("[\n\r]"), "")
        .takeIf {
            it.length % 9 == 0
        }?.let { str ->
            (0 until 27 step 3)
                .map { index ->
                    val s1 = str.substring3Chars(index)
                    val s2 = str.substring3Chars(endOfLine1Index + index)

                    if (str.length == 81) {
                        val s3 = str.substring3Chars(endOfLine2Index + index)
                        OcrNumber.fromStrings(s1, s2, s3)
                    } else {
                        OcrNumber.fromStrings(s1, s2)
                    }
                }
        }
        ?: error("Wrong entry. Please provide a valid entry")
}
