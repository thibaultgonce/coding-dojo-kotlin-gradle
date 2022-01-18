import ArgsParser.ArgsType.BOOLEAN
import ArgsParser.ArgsType.INT
import ArgsParser.ArgsType.STRING
import kotlin.reflect.KClass

class ArgsParser {

    enum class ArgsType(
        val type: KClass<out Any>,
        val isWrongTypeFunction: ((String) -> Boolean)? = null,
        val defaultValue: Any,
        val implicitValue: Any? = null
    ) {
        BOOLEAN(
            type = Boolean::class,
            isWrongTypeFunction = { s -> s.toBooleanStrictOrNull() == null },
            defaultValue = false,
            implicitValue = true
        ),
        INT(
            type = Int::class,
            isWrongTypeFunction = { s -> s.toIntOrNull() == null },
            defaultValue = 0
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
    )

    fun parse(input: String): List<Pair<String, *>> {
        if (input.isEmpty()) {
            throw Error("Input is empty. Please provide a valid input")
        }

        val values: Map<String, String> = Regex("-([a-z])(?: (\\w+)|)")
            .findAll(input).toList()
            .ifEmpty {
                throw Error("No Args found. Please provide an input with valid args")
            }.map {
                it.groupValues
            }.associate { (_, arg, value) ->
                val argType =
                    schema[arg] ?: throw Error("Unknown arg -${arg}. Please use proper args from: ${schema.keys}")
                if (value.isBlank()) {
                    if (argType.implicitValue == null) {
                        throw Error("Missing value of type ${argType.type.simpleName} for arg -${arg}")
                    }
                    Pair(arg, argType.implicitValue.toString())
                } else {
                    if (isWrongType(value, argType)) {
                        throw Error("Wrong value type provided for arg ${arg}. Use value of ${argType.type.simpleName} type")
                    }
                    Pair(arg, value)
                }
            }

        return schema.map { (arg, argType) ->
            values[arg]?.let { Pair(arg, it) }
                ?: Pair(arg, argType.defaultValue.toString())
        }
    }

    fun isWrongType(value: String, argType: ArgsType): Boolean =
        argType.isWrongTypeFunction?.invoke(value) ?: false
}
