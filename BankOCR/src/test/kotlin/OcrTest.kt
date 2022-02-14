import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.io.File

class OcrTest {

    private val usecase1Path = "src/test/resources/usecase1"

    @Test
    fun test_000000000() {
        // data
        val file = File("${usecase1Path}/000000000.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    }

    @Test
    fun test_111111111() {
        // data
        val file = File("${usecase1Path}/111111111.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(1, 1, 1, 1, 1, 1, 1, 1, 1)
    }

    @Test
    fun test_222222222() {
        // data
        val file = File("${usecase1Path}/222222222.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(2, 2, 2, 2, 2, 2, 2, 2, 2)
    }

    @Test
    fun test_333333333() {
        // data
        val file = File("${usecase1Path}/333333333.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(3, 3, 3, 3, 3, 3, 3, 3, 3)
    }

    @Test
    fun test_444444444() {
        // data
        val file = File("${usecase1Path}/444444444.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(4, 4, 4, 4, 4, 4, 4, 4, 4)
    }

    @Test
    fun test_555555555() {
        // data
        val file = File("${usecase1Path}/555555555.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(5, 5, 5, 5, 5, 5, 5, 5, 5)
    }

    @Test
    fun test_666666666() {
        // data
        val file = File("${usecase1Path}/666666666.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(6, 6, 6, 6, 6, 6, 6, 6, 6)
    }

    @Test
    fun test_777777777() {
        // data
        val file = File("${usecase1Path}/777777777.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(7, 7, 7, 7, 7, 7, 7, 7, 7)
    }

    @Test
    fun test_888888888() {
        // data
        val file = File("${usecase1Path}/888888888.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(8, 8, 8, 8, 8, 8, 8, 8, 8)
    }

    @Test
    fun test_999999999() {
        // data
        val file = File("${usecase1Path}/999999999.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(9, 9, 9, 9, 9, 9, 9, 9, 9)
    }

    @Test
    fun test_123456789() {
        // data
        val file = File("${usecase1Path}/123456789.txt")
        val text = file.readText()

        // exec
        val ocrParsed = Ocr().parse(text)

        // asserts
        ocrParsed shouldBe listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    }
}
