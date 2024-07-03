package com.shakelang.util.parseutils.lexer.token

import com.shakelang.util.parseutils.characters.position.Position
import com.shakelang.util.parseutils.characters.position.PositionMaker
import com.shakelang.util.parseutils.lexer.token.stream.TokenInputStream

/**
 * A lambda to create a token provided to the [TokenCreationFactory]
 */
typealias TokenCreationLambda<TT, T, ST, CTX> =
    (TokenCreationContext<TT, T, ST, CTX>) -> T

/**
 * A factory to create tokens
 *
 * **This is not the same as [TokenFactory]!
 * A [TokenCreationFactory] creates
 * tokens from data.
 * A [TokenFactory] is a provider for tokens generated from
 * a lexer.
 * It does not get any data to create the tokens.**
 *
 * @param TT The type of the token type
 * @param T The type of the token
 * @param ST The type of the token input stream
 * @param CTX The type of the token context
 * @param positionMaker The position maker to create positions
 * @param creationLambda The lambda to create the token
 * @param context The context of the token
 * @constructor Creates a new token creation factory
 * @property positionMaker The position maker to create positions
 * @property creationLambda The lambda to create the token
 * @property context The context of the token
 */
class TokenCreationFactory<
    TT : TokenType,
    T : Token<T, TT, ST, CTX>,
    ST : TokenInputStream<ST, TT, T, CTX>,
    CTX : TokenContext<CTX, TT, T, ST>,
    >(

    private val positionMaker: PositionMaker,
    private val creationLambda: TokenCreationLambda<TT, T, ST, CTX>,
    private val context: CTX,
) {

    /**
     * Create a token
     * @param type The type of the token
     * @param start The start index of the token
     * @param end The end index of the token
     * @param value The value of the token
     * @param channel The channel of the token
     * @return The created token
     */
    fun create(type: TT, start: Int, end: Int, value: String, channel: Int = 0): T = creationLambda(
        TokenCreationContext(
            context,
            type,
            start,
            end,
            value,
            channel,
        ),
    )

    /**
     * Create a token
     * @param type The type of the token
     * @param start The start position of the token
     * @param end The end position of the token
     * @param value The value of the token
     * @param channel The channel of the token
     * @return The created token
     */
    fun create(type: TT, start: Position, end: Position, value: String, channel: Int = 0): T = create(
        type,
        start.index,
        end.index,
        value,
        channel,
    )

    /**
     * Create a token
     * @param type The type of the token
     * @param end The end index of the token
     * @param value The value of the token
     * @param channel The channel of the token
     * @return The created token
     */
    fun create(type: TT, end: Int, value: String, channel: Int = 0): T = create(
        type,
        end - value.length + 1,
        end,
        value,
        channel,
    )

    /**
     * Create a token
     * @param type The type of the token
     * @param value The value of the token
     * @param channel The channel of the token
     * @return The created token
     */
    fun create(type: TT, start: Int, end: Int, channel: Int = 0): T = create(
        type,
        start,
        end,
        type.value ?: throw IllegalArgumentException("Implicit value is not allowed for $type"),
        channel,
    )

    /**
     * Create a token
     * @param type The type of the token
     * @param start The start position of the token
     * @param end The end position of the token
     * @param channel The channel of the token
     * @return The created token
     */
    fun create(type: TT, start: Position, end: Position, channel: Int = 0): T = create(
        type,
        start.index,
        end.index,
        type.value ?: throw IllegalArgumentException("Implicit value is not allowed for $type"),
        channel,
    )

    /**
     * Create a token
     * @param type The type of the token
     * @param end The end index of the token
     * @param channel The channel of the token
     * @return The created token
     */
    fun create(type: TT, end: Int, channel: Int = 0): T = create(
        type,
        end,
        type.value ?: throw IllegalArgumentException("Implicit value is not allowed for $type"),
        channel,
    )

    /**
     * Create a token
     * @param type The type of the token
     * @param value The value of the token
     * @param channel The channel of the token
     * @return The created token
     */
    fun create(type: TT, channel: Int = 0): T = create(
        type,
        this.positionMaker.index,
        channel,
    )
}

/**
 * Context for [TokenCreationLambda] to create a token
 * @param TT The type of the token type
 * @param T The type of the token
 * @param ST The type of the token input stream
 * @param CTX The type of the token context
 *
 * @param context The context of the token
 * @param type The type of the token
 * @param startIndex The start index of the token
 * @param endIndex The end index of the token
 * @param value The value of the token
 * @param channel The channel of the token
 * @constructor Creates a new token creation context
 *
 * @property context The context of the token
 * @property type The type of the token
 * @property startIndex The start index of the token
 * @property endIndex The end index of the token
 * @property value The value of the token
 * @property channel The channel of the token
 *
 * @property start The start position of the token
 * @property end The end position of the token
 */
data class TokenCreationContext<
    TT : TokenType,
    T : Token<T, TT, ST, CTX>,
    ST : TokenInputStream<ST, TT, T, CTX>,
    CTX : TokenContext<CTX, TT, T, ST>,
    >(
    val context: CTX,
    val type: TT,
    val startIndex: Int,
    val endIndex: Int,
    val value: String,
    val channel: Int,
) {
    val start: Position
        get() = context.positionMap.resolve(startIndex)
    val end: Position
        get() = context.positionMap.resolve(endIndex)
}
