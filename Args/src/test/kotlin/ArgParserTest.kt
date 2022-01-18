import io.kotest.assertions.throwables.shouldThrowMessage
import io.kotest.matchers.collections.shouldContainAll
import org.junit.jupiter.api.Test

class ArgParserTest {

    private val argsParser = ArgsParser()

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

        shouldThrowMessage("Unknown arg -x. Please use proper args from: [p, d, l]") {
            argsParser.parse(input)
        }
    }

    @Test
    fun `should return 8080 and default args when input is '-p 8080' `() {
        val input = "-p 8080"

        argsParser.parse(input) shouldContainAll listOf(
            Pair("p", "8080"),
            Pair("l", "false"),
            Pair("d", "")
        )
    }

    @Test
    fun `should return true and default args when input is '-l' `() {
        val input = "-l"

        argsParser.parse(input) shouldContainAll listOf(
            Pair("p", "0"),
            Pair("l", "true"),
            Pair("d", "")
        )
    }

    @Test
    fun `should return value and default args when input is '-d value' `() {
        val input = "-d value"

        argsParser.parse(input) shouldContainAll listOf(
            Pair("p", "0"),
            Pair("l", "false"),
            Pair("d", "value")
        )
    }

    @Test
    fun `should return 8080 and value and default args when input has several args `() {
        val input = "-p 8080 -d value"

        argsParser.parse(input) shouldContainAll listOf(
            Pair("p", "8080"),
            Pair("l", "false"),
            Pair("d", "value")
        )
    }

    @Test
    fun `should return all values including implicit ones when input has several args `() {
        val input = "-l -p 8080 -d value"

        argsParser.parse(input) shouldContainAll listOf(
            Pair("p", "8080"),
            Pair("l", "true"),
            Pair("d", "value")
        )
    }
}
