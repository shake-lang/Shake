package com.shakelang.util.parseutils.lexer.token

/**
 * A factory for creating tokens
 *
 * **This is not the same as [TokenCreationFactory]!
 * A [TokenCreationFactory] creates
 * tokens from data.
 * A [TokenFactory] is a provider for tokens generated from
 * a lexer.
 * It does not get any data to create the tokens.**
 *
 * @param T The type of the token
 */
interface TokenFactory<T : Token<*, out TokenType, *, *>> {

    /**
     * Create a new token
     * @return The created token
     */
    fun createToken(): T

    companion object {

        /**
         * Create a token factory from a factory function
         * @param factory The factory function
         * @return The created token factory
         */
        fun <T : Token<*, out TokenType, *, *>> of(factory: () -> T) = object : TokenFactory<T> {
            override fun createToken() = factory()
        }

        /**
         * Create a token factory from a list of tokens
         * @param list The list of tokens
         * @return The created token factory
         */
        fun <T : Token<*, out TokenType, *, *>> of(list: List<T>) = object : TokenFactory<T> {
            private val list = list.toMutableList()
            override fun createToken() = this.list.removeAt(0)
        }

        /**
         * Create a token factory from a vararg of tokens
         * @param tokens The vararg of tokens
         * @return The created token factory
         */
        fun <T : Token<*, out TokenType, *, *>> of(vararg tokens: T) = of(tokens.toList())
    }
}
