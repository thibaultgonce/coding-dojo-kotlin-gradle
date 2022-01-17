import org.junit.jupiter.api.Test

class FizzBuzzTest {

    @Test
    fun test_fizzbuzz() {
        for (i in 1..100) {
            println(fizzbuzz(i))
        }
    }
}
