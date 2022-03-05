enum class OcrNumber(val i: Int, val l1: String, val l2: String, val l3: String) {
    ZERO(
        0,
        " _ ",
        "| |",
        "|_|"
    ),
    ONE(
        1,
        "   ",
        "  |",
        "  |"
    ),
    TWO(
        2,
        " _ ",
        " _|",
        "|_ "
    ),
    THREE(
        3,
        " _ ",
        " _|",
        " _|"
    ),
    FOUR(
        4,
        "   ",
        "|_|",
        "  |"
    ),
    FIVE(
        5,
        " _ ",
        "|_ ",
        " _|"
    ),
    SIX(
        6,
        " _ ",
        "|_ ",
        "|_|"
    ),
    SEVEN(
        7,
        " _ ",
        "  |",
        "  |"
    ),
    HEIGHT(
        8,
        " _ ",
        "|_|",
        "|_|"
    ),
    NINE(
        9,
        " _ ",
        "|_|",
        " _|"
    );

    companion object {
        private val OCR_NUMBER_WITHOUT_TOP = arrayOf(ONE, FOUR)

        private fun OcrNumber.compare(top: String, mid: String, bottom: String): Boolean =
            this.l1 == top && this.l2 == mid && this.l3 == bottom

        private fun OcrNumber.compare(mid: String, bottom: String): Boolean =
            this.l2 == mid && this.l3 == bottom

        fun fromStrings(top: String, mid: String, bottom: String): Int =
            values().find {
                it.compare(top, mid, bottom)
            }?.i ?: -1

        fun fromStrings(mid: String, bottom: String): Int =
            OCR_NUMBER_WITHOUT_TOP.find {
                it.compare(mid, bottom)
            }?.i ?: -1
    }
}
