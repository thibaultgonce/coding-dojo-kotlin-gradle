import ArgsParser.Companion.ArgsType.BOOLEAN
import ArgsParser.Companion.ArgsType.FLOAT
import ArgsParser.Companion.ArgsType.INT
import ArgsParser.Companion.ArgsType.STRING
import kotlin.reflect.KClass

class ArgsParser(
    schema: List<String>
) {
    companion object {
        private enum class ArgsType(
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
            );

            fun isWrong(value: String): Boolean =
                this.isWrongTypeFunction?.invoke(value) ?: false
        }

        private val argTypesMap: HashMap<String, ArgsType> = hashMapOf(
            Pair("", BOOLEAN),
            Pair("#", INT),
            Pair("##", FLOAT),
            Pair("*", STRING),
        )
    }

    private val validatedSchema: Map<String, ArgsType> = schema.associate {
        val arg: Char = it[0]
        val schemaEncoded = it.drop(1).trim()
        val argType: ArgsType = argTypesMap[schemaEncoded]
            ?: throw Error("Invalid type for provided schema")
        Pair(arg.toString(), argType)
    }

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
                    validatedSchema[arg]?.let { argType ->
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
                    validatedSchema[arg]?.defaultValue
                        ?: throw Error("Unknown arg -${arg}. Please use proper args from: ${validatedSchema.keys}")
                }
        }
}
