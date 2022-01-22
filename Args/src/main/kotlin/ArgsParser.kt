import ArgType.Companion.ArgsType.BOOLEAN
import ArgType.Companion.ArgsType.DOUBLE
import ArgType.Companion.ArgsType.INT
import ArgType.Companion.ArgsType.STRING

class ArgsParser(
    schema: List<String>
) {
    companion object {

        private val argTypesMap: HashMap<String, ArgType.Companion.ArgsType> = hashMapOf(
            Pair("", BOOLEAN),
            Pair("#", INT),
            Pair("##", DOUBLE),
            Pair("*", STRING),
        )
    }

    private val validatedSchema: Map<String, ArgType<*>> = schema.associate {
        val arg: Char = it[0]
        val schemaEncoded = it.drop(1).trim()
        val argType: ArgType<*> = argTypesMap[schemaEncoded]?.argType
            ?: throw Error("Invalid type for provided schema")
        Pair(arg.toString(), argType)
    }

    fun parse(input: String): Map<String, Any> =
        input.ifBlank {
            throw Error("Input is empty. Please provide a valid input")
        }.let { notBlankInput ->
            Regex("-([a-z])(?: ((?:-\\d|)(?:[^\\s-]+|))|)")
                .findAll(notBlankInput)
                .ifEmpty {
                    throw Error("No Args found. Please provide an input with valid args")
                }.map { matchResult ->
                    matchResult.groupValues
                }.mapNotNull { (_, arg, values) ->
                    validatedSchema[arg]?.let { argType ->
                        Triple(arg, values.split(","), argType)
                    }
                }.map { (arg, stringValues, argType) ->
                    (
                        argType.takeIf {
                            it.parseFunction != null
                        }?.let {
                            stringValues.map { s ->
                                s.takeIf(String::isNotBlank)
                                    ?.let {
                                        argType.parse(s)
                                            ?: throw Error("Wrong value type provided for arg $arg. Use value of ${argType.classT.simpleName} type")
                                    }
                            }
                        } ?: stringValues
                        ).let { valuesParsed -> Triple(arg, valuesParsed, argType) }
                }.associate { (arg, values, argType) ->
                    arg to values.map { v ->
                        v
                            ?: argType.implicitValue
                            ?: throw Error("Missing value of type ${argType.classT.simpleName} for arg -$arg")
                    }.let {
                        if (it.size == 1) it.first() else it
                    }
                }.withDefault { arg ->
                    validatedSchema[arg]?.defaultValue
                        ?: throw Error("Unknown arg -$arg. Please use proper args from: ${validatedSchema.keys}")
                }
        }
}
