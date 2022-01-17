import java.io.File

class WordListLoader(
    private val filename: String = WORDLIST_FILENAME
) {
    fun load(): Set<String> =
        Regex("\\S+", setOf(RegexOption.MULTILINE))
            .findAll(
                File(filename).readText(Charsets.UTF_8)
            ).flatMap {
                it.groupValues
            }.toSortedSet()

    companion object {
        const val WORDLIST_FILENAME: String = "src/main/resources/anagram-wordlist.txt"
        const val ACTION_WORDLIST_FILENAME: String = "src/main/resources/action-anagram-wordlist.txt"
    }
}




