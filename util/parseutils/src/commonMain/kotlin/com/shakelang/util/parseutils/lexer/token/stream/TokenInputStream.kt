package com.shakelang.util.parseutils.lexer.token.stream

import com.shakelang.util.io.streaming.general.PeekableStream
import com.shakelang.util.parseutils.characters.position.PositionMap
import com.shakelang.util.parseutils.lexer.token.Token
import com.shakelang.util.parseutils.lexer.token.TokenContext
import com.shakelang.util.parseutils.lexer.token.TokenType

/**
 * A [TokenInputStream] provides the [Token]s for a Parser. It is
 * created by a lexer
 */
@Suppress("unused")
interface TokenInputStream<
    Self : TokenInputStream<Self, TT, T, CTX>,
    TT : TokenType,
    T : Token<T, TT, Self, CTX>,
    CTX : TokenContext<CTX, TT, T, Self>,
    > : PeekableStream<T> {

    val actual: T?

    /**
     * The source (mostly filename) of the [TokenInputStream]
     */
    val source: String

    /**
     * The map for the token-positions
     * We have this map to resolve the column / line of an index. This is useful for error-generation.
     */
    val map: PositionMap

    /**
     * Returns the next token of the [TokenInputStream] (and skips)
     * @return the next token
     */
    operator fun next() = read()

    /**
     * Skips the next token
     */
    fun skip()

    /**
     * Skips a number of tokens
     * @param amount the number of tokens to skip
     */
    fun skip(amount: Int)

    // The following methods are unimplemented overrides
    // The reason for this is that PeekableStream is part of another module
    // and these methods should be accessible without explicitly importing
    // the `common-io` module

    /**
     * Check whether this stream has a next token
     * @return whether this stream has a next token
     */
    override fun hasNext(): Boolean

    /**
     * Check whether this stream has a next token
     * @return whether this stream has a next token
     */
    override fun peek(): T

    /**
     * Read the next token
     * @return the next token
     */
    override fun read(): T

    /**
     * Peek a number of tokens
     * @param amount the number of tokens to peek
     * @return the token
     */
    override fun peek(amount: Int): T

    /**
     * Peek a number of tokens
     * @return the token
     */
    override fun peekOrNull(): T?

    /**
     * Peek a number of tokens
     * @param index the number of tokens to peek
     * @return the token
     */
    override fun peekOrNull(index: Int): T?

    /**
     * Read a number of tokens
     * @param amount the number of tokens to read
     * @return the token
     */
    override fun has(amount: Int): Boolean
}
