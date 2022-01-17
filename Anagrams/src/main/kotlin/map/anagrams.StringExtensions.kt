package map

val String.mapCharsCount
    get() = this.toCharArray()
        .asList()
        .groupingBy { it }
        .eachCount()

private fun String.myIsAnagram(str: String) =
    this.takeUnless { it.isEmpty() }?.let {
        str.takeUnless { it.isEmpty() }?.let {
            this.mapCharsCount.all { (c, nb) ->
                str.count { it == c } == nb
            } && this.length == str.length
        }
    } ?: false



