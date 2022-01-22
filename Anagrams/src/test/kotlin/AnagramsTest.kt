import WordListLoader.Companion.ACTION_WORDLIST_FILENAME
import WordListLoader.Companion.WORDLIST_FILENAME
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import sort.getAnagrams
import sort.getTwoWordsAnagram
import sort.getTwoWordsAnagramRecursive
import sort.isAnagram

class AnagramsTest {

    @Test
    fun empty_strings_should_not_be_anagram() {
        "".isAnagram("") shouldBe false
    }

    @Test
    fun a_and_b_should_not_be_anagram() {
        "a".isAnagram("b") shouldBe false
    }

    @Test
    fun a_and_a_should_be_anagram() {
        "a".isAnagram("a") shouldBe true
    }

    @ParameterizedTest
    @ValueSource(strings = ["action", "atonic", "cation"])
    fun action_should_have_anagrams(testStr: String) {
        "action".isAnagram(testStr) shouldBe true
    }

    @Test
    fun actionn_and_atonicc_should_not_be_anagram() {
        "actionn".isAnagram("atonicc") shouldBe false
    }

    @Test
    fun action_should_have_anagrams() {
        val strList = listOf("action", "atonic", "cation")
        "action".getAnagrams(strList) shouldBe strList
    }

    @Test
    fun action_should_be_2wordsAnagrams_with_a_and_tonic() {
        "action".isAnagram("a", "tonic") shouldBe true
    }

    @Test
    fun action_should_not_be_2wordsAnagrams_with_at_and_tonic() {
        "action".isAnagram("at", "tonic") shouldBe false
    }

    @Test
    fun action_should_have_2wordsAnagrams() {
        val wordlist = WordListLoader(ACTION_WORDLIST_FILENAME).load().toList()
        "action".getTwoWordsAnagram(wordlist) shouldHaveSize 59
    }
    @Test
    fun action_should_have_2wordsAnagramss() {
        val wordlist = WordListLoader(ACTION_WORDLIST_FILENAME).load().toList()
        "action".getTwoWordsAnagramRecursive(wordlist) shouldHaveSize 59
    }

    @Test
    fun documenting_should_not_be_2wordsAnagrams_with_confide_and_mustang() {
        "documenting".isAnagram("confide", "mustang") shouldBe false
    }

    @Test
    fun documenting_should_be_anagram_with_doc_and_men_and_tuing() {
        "documenting".isAnagram("doc", "men", "tuing") shouldBe true
    }

    @Test
    fun documenting_should_have_2wordsAnagrams() {
        val wordlist = WordListLoader(WORDLIST_FILENAME).load().toList()
        "documenting".getTwoWordsAnagram(wordlist) shouldHaveSize 0
    }
}
