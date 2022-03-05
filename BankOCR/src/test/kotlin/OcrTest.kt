import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.io.File

class OcrTest {

    private val usecase1Path = "src/test/resources/usecase1"
    private val usecase3Path = "src/test/resources/usecase3"

    @Test
    fun test_000000000() {
        // data
        val file = File("$usecase1Path/000000000.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    }

    @Test
    fun test_111111111() {
        // data
        val file = File("$usecase1Path/111111111.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(1, 1, 1, 1, 1, 1, 1, 1, 1)
    }

    @Test
    fun test_222222222() {
        // data
        val file = File("$usecase1Path/222222222.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(2, 2, 2, 2, 2, 2, 2, 2, 2)
    }

    @Test
    fun test_333333333() {
        // data
        val file = File("$usecase1Path/333333333.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(3, 3, 3, 3, 3, 3, 3, 3, 3)
    }

    @Test
    fun test_444444444() {
        // data
        val file = File("$usecase1Path/444444444.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(4, 4, 4, 4, 4, 4, 4, 4, 4)
    }

    @Test
    fun test_555555555() {
        // data
        val file = File("$usecase1Path/555555555.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(5, 5, 5, 5, 5, 5, 5, 5, 5)
    }

    @Test
    fun test_666666666() {
        // data
        val file = File("$usecase1Path/666666666.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(6, 6, 6, 6, 6, 6, 6, 6, 6)
    }

    @Test
    fun test_777777777() {
        // data
        val file = File("$usecase1Path/777777777.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(7, 7, 7, 7, 7, 7, 7, 7, 7)
    }

    @Test
    fun test_888888888() {
        // data
        val file = File("$usecase1Path/888888888.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(8, 8, 8, 8, 8, 8, 8, 8, 8)
    }

    @Test
    fun test_999999999() {
        // data
        val file = File("$usecase1Path/999999999.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(9, 9, 9, 9, 9, 9, 9, 9, 9)
    }

    @Test
    fun test_123456789() {
        // data
        val file = File("$usecase1Path/123456789.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    }

    @Test
    fun test_all() {
        // data
        val file = File("$usecase1Path/all.txt")

        // exec
        val ocrParsed = Ocr().parse(file)

        // asserts
        ocrParsed shouldBe listOf(
            "000000000",
            "111111111 ERR",
            "222222222 ERR",
            "333333333 ERR",
            "444444444 ERR",
            "555555555 ERR",
            "666666666 ERR",
            "777777777 ERR",
            "888888888 ERR",
            "999999999 ERR",
            "123456789 ERR",
        )
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "1, 000000051 ERR",
            "2, 49006771? ILL",
            "3, 1234?678? ILL"
        ], ignoreLeadingAndTrailingWhitespace = true
    )
    fun test_usecase3(fileName: String, result: String) {
        // data
        val file = File("$usecase3Path/$fileName.txt")

        // exec
        val ocrParsed = Ocr().parse(file)

        // asserts
        ocrParsed shouldBe listOf(
            result
        )
    }
}
