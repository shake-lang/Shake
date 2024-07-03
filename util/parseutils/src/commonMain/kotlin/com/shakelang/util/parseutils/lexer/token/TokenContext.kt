package com.shakelang.util.parseutils.lexer.token

import com.shakelang.util.parseutils.characters.position.PositionMap
import com.shakelang.util.parseutils.lexer.token.stream.TokenInputStream

open class TokenContext<
    Self : TokenContext<Self, TT, T, ST>,
    TT : TokenType,
    T : Token<T, TT, ST, Self>,
    ST : TokenInputStream<ST, TT, T, Self>,
    > {
    lateinit var stream: ST
        private set
    lateinit var positionMap: PositionMap
        private set

    internal object Tools {
        fun <
            Self : TokenContext<Self, TT, T, ST>,
            TT : TokenType,
            T : Token<T, TT, ST, Self>,
            ST : TokenInputStream<ST, TT, T, CTX>,
            CTX : TokenContext<CTX, TT, T, ST>,
            > initStream(
            context: Self,
            stream: ST,
        ) {
            context.stream = stream
        }

        fun <
            Self : TokenContext<Self, TT, T, ST>,
            TT : TokenType,
            T : Token<T, TT, ST, Self>,
            ST : TokenInputStream<ST, TT, T, CTX>,
            CTX : TokenContext<CTX, TT, T, ST>,
            > initPositionMap(
            context: Self,
            positionMap: PositionMap,
        ) {
            context.positionMap = positionMap
        }
    }
}
