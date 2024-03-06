package com.shakelang.util.parseutils.lexer

import com.shakelang.util.parseutils.CompilerError
import com.shakelang.util.parseutils.characters.position.Position
import com.shakelang.util.parseutils.characters.streaming.CharacterInputStream
import com.shakelang.util.parseutils.lexer.token.Token
import com.shakelang.util.parseutils.lexer.token.TokenType
import kotlin.jvm.JvmOverloads

@Suppress("unused")
abstract class LexingBase<TT : TokenType, T : Token<*, TT, *>>(
    val input: CharacterInputStream,
) {

    abstract fun makeToken(): T

    open fun createError(
        message: String,
        name: String,
        details: String,
        start: Position,
        end: Position,
    ) = LexerError(message, name, details, start, end)

    open fun createError(
        name: String,
        details: String,
        start: Position = input.positionMaker.createPositionAtLocation(),
        end: Position = start,
    ) = LexerError(name, details, start, end)

    open fun createError(
        details: String,
        start: Position = input.positionMaker.createPositionAtLocation(),
        end: Position = start,
    ) = LexerError(details, start, end)

    open class LexerError(
        message: String,
        name: String,
        details: String,
        start: Position,
        end: Position,
    ) :
        CompilerError(message, name, details, start, end) {

        @JvmOverloads
        constructor(
            base: LexingBase<*, *>,
            name: String,
            details: String,
            start: Position = base.input.positionMaker.createPositionAtLocation(),
            end: Position = start,
        ) : this(
            "Error occurred in lexer: " + name + ", " + details + " in " + start.source + ":" + start.line + ":" + start.column,
            name,
            details,
            start,
            end,
        )

        @JvmOverloads
        constructor(
            base: LexingBase<*, *>,
            details: String,
            start: Position = base.input.positionMaker.createPositionAtLocation(),
            end: Position = start,
        ) : this(
            "Error occurred in lexer: " + details + " in " + start.source + ":" + start.line + ":" + start.column,
            "LexerError",
            details,
            start,
            end,
        )

        @JvmOverloads
        constructor(
            name: String,
            details: String,
            start: Position,
            end: Position = start,
        ) : this(
            "Error occurred in lexer: " + name + ", " + details + " in " + start.source + ":" + start.line + ":" + start.column,
            name,
            details,
            start,
            end,
        )

        @JvmOverloads
        constructor(
            details: String,
            start: Position,
            end: Position = start,
        ) : this(
            "Error occurred in lexer: " + details + " in " + start.source + ":" + start.line + ":" + start.column,
            "LexerError",
            details,
            start,
            end,
        )
    }
}
