import io.kotest.assertions.asClue
import io.kotest.assertions.throwables.shouldThrowMessage
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ArgParserTest {

    private val argsParser = ArgsParser(listOf("l", "p#", "f##", "d*"))

    @Test
    fun `should return Error when empty input`() {
        val input = ""
        shouldThrowMessage("Input is empty. Please provide a valid input") {
            argsParser.parse(input)
        }
    }

    @Test
    fun `should return Error when input contains no args`() {
        val input = "input with no args"

        shouldThrowMessage("No Args found. Please provide an input with valid args") {
            argsParser.parse(input)
        }
    }

    @Test
    fun `should return Error when wrong type - input contains Boolean value for Int arg type`() {
        val input = "-p true"

        shouldThrowMessage("Wrong value type provided for arg p. Use value of Int type") {
            argsParser.parse(input)
        }
    }

    @Test
    fun `should return Error when wrong type - input contains Int value for Boolean arg type`() {
        val input = "-l 123"

        shouldThrowMessage("Wrong value type provided for arg l. Use value of Boolean type") {
            argsParser.parse(input)
        }
    }

    @Test
    fun `should return Error when missing value for non-implicite arg type`() {
        val input = "-p"

        shouldThrowMessage("Missing value of type Int for arg -p") {
            argsParser.parse(input)
        }
    }

    @Test
    fun `should return Error when input contains unknown arg`() {
        val input = "-x"

        shouldThrowMessage("Unknown arg -x. Please use proper args from: [l, p, f, d]") {
            argsParser.parse(input).getValue("x")
        }
    }

    @Test
    fun `should return 8080 and default args when input is '-p 8080' `() {
        val input = "-p 8080"

        argsParser.parse(input).asClue {
            it.getValue("p") shouldBe "8080"
            it.getValue("l") shouldBe "false"
            it.getValue("d") shouldBe ""
        }
    }

    @Test
    fun `should return -2 and default args when input is '-p -2' `() {
        val input = "-p -2"

        argsParser.parse(input).asClue {
            it.getValue("p") shouldBe "-2"
            it.getValue("l") shouldBe "false"
            it.getValue("d") shouldBe ""
        }
    }

    @Test
    fun `should return -2,56 and default args when input is '-f -2,56' `() {
        val input = "-f -2.56"

        argsParser.parse(input).asClue {
            it.getValue("p") shouldBe "0"
            it.getValue("l") shouldBe "false"
            it.getValue("d") shouldBe ""
            it.getValue("f") shouldBe "-2.56"
        }
    }

    @Test
    fun `should return true and default args when input is '-l' `() {
        val input = "-l"

        argsParser.parse(input).asClue {
            it.getValue("p") shouldBe "0"
            it.getValue("l") shouldBe "true"
            it.getValue("d") shouldBe ""
        }
    }

    @Test
    fun `should return value and default args when input is '-d value' `() {
        val input = "-d value"

        argsParser.parse(input).asClue {
            it.getValue("p") shouldBe "0"
            it.getValue("l") shouldBe "false"
            it.getValue("d") shouldBe "value"
        }
    }

    @Test
    fun `should return 8080 and value and default args when input has several args `() {
        val input = "-p 8080 -d value"

        argsParser.parse(input).asClue {
            it.getValue("p") shouldBe "8080"
            it.getValue("l") shouldBe "false"
            it.getValue("d") shouldBe "value"
        }
    }

    @Test
    fun `should return all values including implicit ones when input has several args `() {
        val input = "-l -p 8080 -d value"

        argsParser.parse(input).asClue {
            it.getValue("p") shouldBe "8080"
            it.getValue("l") shouldBe "true"
            it.getValue("d") shouldBe "value"
        }
    }
}
