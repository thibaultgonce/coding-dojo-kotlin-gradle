package sort

fun String.toSortedCharArray() = this.lowercase().toCharArray().sorted()

fun String.isAnagram(str: String): Boolean =
    this.takeUnless { it.isEmpty() }?.let {
        str.takeUnless { it.isEmpty() }?.let {
            this.toSortedCharArray() == str.toSortedCharArray()
        }
    } ?: false

fun String.isAnagram(vararg strs: String): Boolean =
    (this).isAnagram(strs.joinToString(""))

fun String.getAnagrams(strList: List<String>): List<String> =
    strList.filter { this.isAnagram(it) }

fun String.getTwoWordsAnagram(strList: List<String>): MutableList<Pair<String, String>> {
    val list = mutableListOf<Pair<String, String>>()

    strList.forEachIndexed { index, str1 ->
        for (i in index + 1 until strList.size) {
            val str2 = strList[i]
            if ((this).isAnagram(str1, str2)) {
                list.add(Pair(str1, str2))
            }
        }
    }

    return list
}



