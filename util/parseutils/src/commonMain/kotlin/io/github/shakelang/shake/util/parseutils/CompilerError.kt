package io.github.shakelang.shake.util.parseutils

import io.github.shakelang.shake.util.colorlib.functional.invert
import io.github.shakelang.shake.util.colorlib.functional.join
import io.github.shakelang.shake.util.colorlib.functional.red
import io.github.shakelang.shake.util.parseutils.characters.position.Position
import io.github.shakelang.shake.util.parseutils.characters.position.PositionMap
import kotlin.js.JsName

/**
 * A [CompilerError] is an error thrown by a Compiler. It has functionality for
 * marking source code locations
 */
open class CompilerError : Error {

    /**
     * The name of the [CompilerError]
     */
    @JsName("exceptionName")
    val exceptionName: String

    /**
     * The details of the [CompilerError]
     */
    @JsName("details")
    val details: String

    /**
     * The start position of the [CompilerError]
     */
    @JsName("start")
    val start: Position

    /**
     * The end position of the [CompilerError]
     */
    @JsName("end")
    val end: Position

    /**
     * The marker of the Error
     */
    @JsName("marker")
    val marker: ErrorMarker

    /**
     * Constructor for [CompilerError]
     *
     * @param message the message of the exception _(Value for [CompilerError.message])_
     * @param marker the marker of the exception _(Value for [CompilerError.marker])_
     * @param exceptionName the name of the exception _(Value for [CompilerError.exceptionName])_
     * @param details the details of the exception _(Value for [CompilerError.details])_
     * @param start the start position of the exception _(Value for [CompilerError.start])_
     * @param end the end position of the exception _(Value for [CompilerError.end])_
     * @param cause the cause for the exception
     */
    private constructor(
        message: String,
        marker: ErrorMarker,
        exceptionName: String,
        details: String,
        start: Position,
        end: Position,
        cause: Throwable? = null
    ) : super("$message: $details\n\n$marker\n", cause) {
        this.exceptionName = exceptionName
        this.details = details
        this.start = start
        this.end = end
        this.marker = marker
    }

    /**
     * Constructor for [CompilerError]
     *
     * @param message the message of the exception _(Value for [CompilerError.message])_
     * @param exceptionName the name of the exception _(Value for [CompilerError.exceptionName])_
     * @param details the details of the exception _(Value for [CompilerError.details])_
     * @param start the start position of the exception _(Value for [CompilerError.start])_
     * @param end the end position of the exception _(Value for [CompilerError.end])_
     * @param cause the cause for the exception
     */
    constructor(
        message: String,
        exceptionName: String,
        details: String,
        start: Position,
        end: Position,
        cause: Throwable? = null
    ) : this(
        message,
        createPositionMarker(
            MAX_LENGTH,
            start,
            end
        ),
        exceptionName,
        details,
        start,
        end,
        cause
    )

    /**
     * Constructor for [CompilerError]
     *
     * @param message the message of the exception _(Value for [CompilerError.message])_
     * @param exceptionName the name of the exception _(Value for [CompilerError.exceptionName])_
     * @param details the details of the exception _(Value for [CompilerError.details])_
     * @param map the position map to resolve start and end _(Value for [CompilerError.start])_
     * @param start the start position of the exception _(Value for [CompilerError.start])_
     * @param end the end position of the exception _(Value for [CompilerError.end])_
     * @param cause the cause for the exception
     */
    constructor(
        message: String,
        exceptionName: String,
        details: String,
        map: PositionMap,
        start: Int,
        end: Int,
        cause: Throwable? = null
    ) : this(
        message,
        exceptionName,
        details,
        map.resolve(start),
        map.resolve(end),
        cause
    )

    /**
     * Stringify the [CompilerError]
     */
    override fun toString() = message!!

    /**
     * A marker for the position of the [CompilerError]
     */
    class ErrorMarker(

        /**
         * The source of the [ErrorMarker]
         */
        @JsName("source")
        val source: String,

        /**
         * The colorPreview of the [ErrorMarker]
         */
        @JsName("colorPreview")
        val colorPreview: String,

        /**
         * The preview of the [ErrorMarker]
         */
        @JsName("preview")
        val preview: String,

        /**
         * The marker of the [ErrorMarker]
         */
        @JsName("marker")
        val marker: String

    ) {

        /**
         * Generate the [ErrorMarker] as a string
         *
         */
        @JsName("generateMarker")
        fun generateMarker() = "at $source\n$preview\n$marker"

        /**
         * Generate the [ErrorMarker] as a string including console colors
         *
         */
        @JsName("generateColoredMarker")
        fun generateColoredMarker() = "at $source\n$colorPreview\n$marker"

        /**
         * Stringify the [CompilerError] (just the same as [ErrorMarker.generateMarker]
         *
         */
        override fun toString() = generateMarker()
    }

    companion object {

        /**
         * The maxLength for generated [ErrorMarker]s
         *
         */
        const val MAX_LENGTH = 60

        /**
         * Creates a [ErrorMarker]
         *
         */
        private fun createPositionMarker(
            maxLength: Int = MAX_LENGTH,
            p1: Position,
            p2: Position
        ): ErrorMarker {
            var pos1 = p1
            var pos2 = p2

            try {
                // Check requirements
                if (pos1.source != pos2.source) throw Error("The two have to be located in the same source")
                if (pos1.line != pos2.line) throw Error("The two positions that should be marked have to be in the same line")
                if (pos1.column > pos2.column) throw Error("Position 1 must be before Position 2")

                if (pos1.source.lineSeparators.contains(pos1.index)) {
                    var pos = pos1.index
                    while (pos1.source.lineSeparators.contains(pos)) {
                        pos--
                    }
                    pos1 = pos1.source.resolve(pos)
                }

                if (pos2.source.lineSeparators.contains(pos2.index)) {
                    var pos = pos2.index
                    while (pos2.source.lineSeparators.contains(pos)) {
                        pos--
                    }
                    pos2 = pos2.source.resolve(pos)
                }

                // Line start (linenumber + 2 spaces)
                val lineStr = pos1.line.toString() + "  "

                // Length of the highlighted section
                val highlighted = pos2.column - pos1.column + 1

                // The maximum amount of characters that will be shown around the highlighted section
                val maxAround = maxLength - highlighted - lineStr.length
                val before = maxAround / 2 + maxAround % 2
                val after = maxAround / 2

                // The available tokens before the highlighted section
                val before2 = pos1.column - 1

                // The available tokens after the highlighted section
                val after2 = pos2.source.getAfterInLine(pos2)

                // Take the smallest value and use it
                var realBefore = smallest(before, before2)
                var realAfter = smallest(after, after2 + 1)

                // Get the differences (to display if there are tokens that can't be displayed)
                var beforeDif = before2 - realBefore
                var afterDif = after2 - realAfter

                // Resolve numbers if there is a non-displayed part
                if (beforeDif > 0) {
                    val baseLen = beforeDif.toString().length
                    var len = baseLen + 4
                    realBefore -= if (len.toString().length != baseLen) ++len else len
                    beforeDif += len
                }
                if (afterDif > 0) {
                    val baseLen = afterDif.toString().length
                    var len = baseLen + 4
                    realAfter -= if (len.toString().length != baseLen) ++len else len
                    afterDif += len
                }

                // The start of the line
                val start = (
                    lineStr +
                        (if (beforeDif > 0) "+$beforeDif..." else "") +
                        pos1.source.source[pos1.index - realBefore, pos1.index].concatToString()
                            .replace("\t".toRegex(), " ")
                    )

                // The end of the line
                val end = (
                    pos1.source.source[pos2.index + 1, pos2.index + realAfter].concatToString()
                        .replace("\t".toRegex(), " ")
                        .replace("\n".toRegex(), " ") +
                        if (afterDif > 0) "...+$afterDif" else ""
                    )

                // Generate end-string
                return ErrorMarker(
                    join(pos1.source.location, ":", pos1.line.toString(), ":", pos1.column.toString()),
                    join(
                        start,
                        invert(
                            red(
                                pos1.source.source[pos1.index, pos2.index + 1].concatToString()
                                    .replace("\t".toRegex(), " ")
                                    .replace("\n".toRegex(), " ")
                            )
                        ) + end
                    ),
                    join(start, pos1.source.source[pos1.index, pos2.index + 1].concatToString(), end),
                    join(' '.repeat(start.length), '^'.repeat(highlighted))
                )
            } catch (e: Throwable) {
                println("Error while creating position marker:")
                e.printStackTrace()
                return ErrorMarker(
                    "${pos1.source.location}:${pos1.line}:${pos1.column}",
                    red("Error while creating position marker: ${e.message}"),
                    "Error while creating position marker: ${e.message}",
                    ""
                )
            }
        }
    }
}

private fun Char.repeat(number: Int): String {
    val string = StringBuilder()
    for (i in 0 until number) string.append(this)
    return string.toString()
}

private fun smallest(vararg arr: Int): Int {
    var smallest = arr[0]
    for (i in 1 until arr.size) if (arr[i] < smallest) smallest = arr[i]
    return smallest
}
