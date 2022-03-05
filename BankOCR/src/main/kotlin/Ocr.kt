import java.io.File

class Ocr {
    private val lineLength = 27
    private val ocrNumberPartSize = 3

    private val line1EndIndex = lineLength
    private val line2EndIndex = 2 * (lineLength)

    private fun String.substringOcrNumberPart(startIndex: Int) =
        this.substring(startIndex, startIndex + ocrNumberPartSize)

    fun parse(entry: String): List<Int> = entry
        .replace(Regex("[\n\r]"), "")
        .let { str ->
            when (str.length) {
                54 -> (0 until lineLength step ocrNumberPartSize).map { index ->
                    OcrNumber.fromStrings(
                        mid = str.substringOcrNumberPart(index),
                        bottom = str.substringOcrNumberPart(line1EndIndex + index)
                    )
                }
                81 -> (0 until lineLength step ocrNumberPartSize).map { index ->
                    OcrNumber.fromStrings(
                        top = str.substringOcrNumberPart(index),
                        mid = str.substringOcrNumberPart(line1EndIndex + index),
                        bottom = str.substringOcrNumberPart(line2EndIndex + index)
                    )
                }
                else -> error("Wrong entry. Please provide a valid entry")
            }
        }

    private fun List<Int>.isValidChecksum(): Boolean =
        this.reduceIndexed { index, acc, i -> i * index + acc }
            .mod(11) == 0

    private fun List<Int>.format(): String = 
        this.joinToString("")
            .replace("-1", "?")

    fun parse(file: File): List<String> =
        file.useLines { seq ->
            seq.chunked(4)
                .map { fourLines ->
                    val parsedEntry = parse(
                        entry = fourLines.joinToString("")
                    )
                    parsedEntry.format().let {
                        if (parsedEntry.contains(-1)) {
                            "$it ILL"
                        } else if (!parsedEntry.isValidChecksum()) {
                            "$it ERR"
                        } else it
                    }
                }.toList()
        }
}
