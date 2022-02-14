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

        private fun OcrNumber.compare(l1: String, l2: String, l3: String): Boolean =
            this.l1 == l1 && this.l2 == l2 && this.l3 == l3

        private fun OcrNumber.compare(l2: String, l3: String): Boolean =
            this.l2 == l2 && this.l3 == l3
        
        fun fromStrings(l1: String, l2: String, l3: String): Int? =
            values().find {
                it.compare(l1, l2, l3)
            }?.i

        fun fromStrings(l2: String, l3: String): Int? =
            OCR_NUMBER_WITHOUT_TOP.find {
                it.compare(l2, l3)
            }?.i
    }


}



