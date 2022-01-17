package sort

fun String.toSortedCharArray() = this.lowercase().toCharArray().sorted()

fun String.isAnagram(str: String): Boolean =
    this.takeUnless { it.isEmpty() }?.let {
        str.takeUnless { it.isEmpty() }?.let {
            this.toSortedCharArray() == str.toSortedCharArray()
        }
    } ?: false

fun String.isAnagram(vararg strs: String): Boolean = this.isAnagram(strs.toList())

fun String.isAnagram(strList: List<String>): Boolean = this.isAnagram(strList.joinToString(""))

fun String.getAnagrams(strList: List<String>): List<String> =
    strList.filter { this.isAnagram(it) }

fun String.getTwoWordsAnagram(strList: List<String>): List<Pair<String, String>> =
    strList.flatMapIndexed { index, str1 ->
        (index + 1 until strList.size)
            .map { strList[it] }
            .mapNotNull { str2 ->
                takeIf {
                    this.isAnagram(str1, str2)
                }?.let { Pair(str1, str2) }
            }
    }

fun String.getTwoWordsAnagramRecursive(strList: List<String>): List<Pair<String, String>> =
    when (strList.size) {
        0, 1 -> emptyList()
        2 -> Pair(strList[0], strList[1]).let { pair ->
            if (this.isAnagram(pair.toList()))
                listOf(pair)
            else emptyList()
        }
        else -> strList.drop(1)
            .let { droppedList ->
                droppedList.flatMap { str ->
                    this.getTwoWordsAnagramRecursive(listOf(strList[0], str))
                } + this.getTwoWordsAnagramRecursive(droppedList)
            }
    }




