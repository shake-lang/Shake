package com.shakelang.util.parseutils.lexer

import com.shakelang.util.parseutils.CompilerError
import com.shakelang.util.parseutils.LexerErrorFactory
import com.shakelang.util.parseutils.characters.position.Position
import com.shakelang.util.parseutils.characters.streaming.CharacterInputStream
import com.shakelang.util.parseutils.lexer.token.*
import com.shakelang.util.parseutils.lexer.token.stream.TokenInputStream

/**
 * An abstract lexer class
 * @param TT The type of the token type
 * @param T The type of the token
 * @param ST The type of the token stream
 * @param CTX The type of the token context
 */
@Suppress("unused")
abstract class AbstractLexer<
    TT : TokenType,
    T : Token<T, TT, ST, CTX>,
    ST : TokenInputStream<ST, TT, T, CTX>,
    CTX : TokenContext<CTX, TT, T, ST>,
    >(

    /**
     * The input stream
     */
    val input: CharacterInputStream,

    /**
     * The token context
     */
    val ctx: CTX,

    /**
     * The state of the lexer
     */
    private var state: Int = 0,
) {

    /**
     * The token creation factory
     */
    val factory = TokenCreationFactory(input.positionMaker, ::tokenFactory, ctx)

    /**
     * Create a factory for the lexer
     */
    private fun toFactory() = TokenFactory.of(this::makeToken)

    /**
     * Stores weather the stream was already created
     * (We don't want to create multiple token streams)
     */
    private var streamCreated = false

    /**
     * Create a token stream
     */
    fun stream(): ST {
        if (streamCreated) throw IllegalStateException("Stream already created for this lexer")
        streamCreated = true
        val stream = createStream(toFactory())
        TokenContext.Tools.initStream(ctx, stream)
        return stream
    }

    /**
     * Create a token stream
     */
    protected abstract fun createStream(factory: TokenFactory<T>): ST

    /**
     * Factory method for creating tokens
     */
    abstract fun makeToken(): T

    /**
     * Factory method for creating tokens
     */
    abstract fun tokenFactory(ctx: TokenCreationContext<TT, T, ST, CTX>): T

    /**
     * The error factory for the lexer
     */
    open val errorFactory = LexerErrorFactory(
        { message, start, end ->
            LexerError(message, start, end)
        },
        input,
    )

    /**
     * A [CompilerError] thrown by the lexer
     */
    class LexerError(
        message: String,
        start: Position,
        end: Position,
    ) : CompilerError(message, start, end)
}
