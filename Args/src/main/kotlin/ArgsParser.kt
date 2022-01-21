import ArgsParser.ArgsType.*
import kotlin.reflect.KClass

class ArgsParser {

    enum class ArgsType(
        val type: KClass<out Any>,
        val isWrongTypeFunction: ((String) -> Boolean)? = null,
        val defaultValue: String,
        val implicitValue: String? = null
    ) {
        BOOLEAN(
            type = Boolean::class,
            isWrongTypeFunction = { s -> s.toBooleanStrictOrNull() == null },
            defaultValue = "false",
            implicitValue = "true"
        ),
        INT(
            type = Int::class,
            isWrongTypeFunction = { s -> s.toIntOrNull() == null },
            defaultValue = "0"
        ),
        FLOAT(
            type = Float::class,
            isWrongTypeFunction = { s -> s.toFloatOrNull() == null },
            defaultValue = "0.0"
        ),
        STRING(
            type = String::class,
            defaultValue = ""
        )
    }

    private val schema: HashMap<String, ArgsType> = hashMapOf(
        Pair("p", INT),
        Pair("d", STRING),
        Pair("l", BOOLEAN),
        Pair("f", FLOAT),
    )

    fun parse(input: String): Map<String, String> =
        input.ifBlank {
            throw Error("Input is empty. Please provide a valid input")
        }.let {
            Regex("-([a-z])(?: ((?:-|)[0-9]+(?:\\.[0-9]+|)|\\w+)|)")
                .findAll(it)
                .ifEmpty {
                    throw Error("No Args found. Please provide an input with valid args")
                }.map { matchResult ->
                    matchResult.groupValues
                }.mapNotNull { (_, arg, value) ->
                    schema[arg]?.let { argType ->
                        Triple(arg, value, argType)
                    }
                }.onEach { (arg, value, argType) ->
                    if (value.isNotBlank() && argType.isWrong(value)) {
                        throw Error("Wrong value type provided for arg ${arg}. Use value of ${argType.type.simpleName} type")
                    }
                }.associate { (arg, value, argType) ->
                    Pair(arg, value.ifBlank {
                        argType.implicitValue
                            ?: throw Error("Missing value of type ${argType.type.simpleName} for arg -${arg}")
                    })
                }.withDefault { arg ->
                    schema[arg]?.defaultValue
                        ?: throw Error("Unknown arg -${arg}. Please use proper args from: ${schema.keys}")
                }
        }


    fun ArgsType.isWrong(value: String): Boolean =
        this.isWrongTypeFunction?.invoke(value) ?: false
}
