package com.shakelang.util.parseutils.lexer.token.stream

import com.shakelang.util.io.streaming.general.PeekableStreamImpl
import com.shakelang.util.io.streaming.general.Stream
import com.shakelang.util.parseutils.characters.position.PositionMap
import com.shakelang.util.parseutils.lexer.token.Token
import com.shakelang.util.parseutils.lexer.token.TokenContext
import com.shakelang.util.parseutils.lexer.token.TokenFactory
import com.shakelang.util.parseutils.lexer.token.TokenType

open class FactoryTokenInputStream<
    Self : TokenInputStream<Self, TT, T, CTX>,
    TT : TokenType,
    T : Token<T, TT, Self, CTX>,
    CTX : TokenContext<CTX, TT, T, Self>,
    >(
    val factory: TokenFactory<T>,
    override val map: PositionMap,
    val eof: TT,
) : PeekableStreamImpl<T>(
    object : Stream<T> {
        override fun read(): T = factory.createToken()
        override fun hasNext(): Boolean = true
    },
),
    TokenInputStream<Self, TT, T, CTX> {

    final override var actual: T? = null
        private set

    override val source: String
        get() = map.location

    override fun read(): T {
        val token = super.read()
        actual = token
        return token
    }

    override fun skip() {
        actual = read()
    }

    override fun skip(amount: Int) {
        for (i in 0 until amount) read()
    }

    override fun has(amount: Int) = peek(amount).type != eof
    override fun hasNext(): Boolean = peek().type != eof
}
