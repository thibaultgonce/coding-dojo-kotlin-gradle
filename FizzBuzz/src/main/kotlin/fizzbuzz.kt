fun fizzbuzz(n: Int) = (
    "Fizz".takeIf { n % 3 == 0 }.orEmpty() +
        "Buzz".takeIf { n % 5 == 0 }.orEmpty() +
        "Tick".takeIf { n % 7 == 0 }.orEmpty()
    ).takeUnless { it.isEmpty() }
    ?: n.toString()
