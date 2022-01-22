class ArgType<T>(
    val classT: Class<T>,
    val parseFunction: ((String) -> T?)? = null,
    val defaultValue: T,
    val implicitValue: T? = null
) {
    companion object {
        enum class ArgsType(
            val argType: ArgType<*>
        ) {
            BOOLEAN(
                ArgType(
                    classT = Boolean::class.java,
                    parseFunction = { s -> s.toBooleanStrictOrNull() },
                    defaultValue = false,
                    implicitValue = true
                )
            ),
            INT(
                ArgType(
                    classT = Int::class.java,
                    parseFunction = { s -> s.toIntOrNull() },
                    defaultValue = 0
                )
            ),
            DOUBLE(
                ArgType(
                    classT = Double::class.java,
                    parseFunction = { s -> s.toDoubleOrNull() },
                    defaultValue = 0.0
                )
            ),
            STRING(
                ArgType(
                    classT = String::class.java,
                    defaultValue = ""
                )
            );
        }
    }

    fun parse(value: String): Any? = this.parseFunction?.invoke(value)
}
