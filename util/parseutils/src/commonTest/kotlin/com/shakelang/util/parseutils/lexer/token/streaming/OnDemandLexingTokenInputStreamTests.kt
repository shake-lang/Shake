package com.shakelang.util.parseutils.lexer.token.streaming

import com.shakelang.util.parseutils.characters.position.PositionMap
import com.shakelang.util.parseutils.characters.source.CharacterSource
import com.shakelang.util.parseutils.characters.streaming.SourceCharacterInputStream
import com.shakelang.util.parseutils.lexer.AbstractLexer
import com.shakelang.util.parseutils.lexer.token.Token
import com.shakelang.util.parseutils.lexer.token.stream.OnDemandLexingTokenInputStream
import kotlin.test.*

class OnDemandLexingTokenInputStreamTests {
    enum class TokenType : com.shakelang.util.parseutils.lexer.token.TokenType {
        IDENTIFIER,
        NUMBER,
        STRING,
        ;

        override val hasValue: Boolean
            get() = this == IDENTIFIER

        override fun length(value: String?): Int {
            return value?.length ?: 0
        }
    }

    class TestLexer(
        tokens: List<Token<TokenType>>,
    ) : AbstractLexer<TokenType, Token<TokenType>>(
        SourceCharacterInputStream(
            CharacterSource.from(
                "qwgg".toCharArray(),
                "a",
            ),
        ),
    ) {

        val tokens = tokens.toMutableList()
        override fun makeToken(): Token<TokenType> {
            return tokens.removeAt(0)
        }

        override fun toString(): String {
            return "hello world"
        }
    }

    @Test
    fun testConstruct() {
        val tokens = mutableListOf<Token<TokenType>>()
        tokens.add(Token(TokenType.IDENTIFIER, "test", 0, 0))
        tokens.add(Token(TokenType.NUMBER, "123", 0, 0))
        tokens.add(Token(TokenType.STRING, "\"test\"", 0, 0))
        val lexer = TestLexer(tokens)
        OnDemandLexingTokenInputStream(lexer)
    }

    @Test
    fun testNext() {
        val tokens = mutableListOf<Token<TokenType>>()
        tokens.add(Token(TokenType.IDENTIFIER, "test", 0, 0))
        tokens.add(Token(TokenType.NUMBER, "123", 0, 0))
        tokens.add(Token(TokenType.STRING, "\"test\"", 0, 0))
        val lexer = TestLexer(tokens)
        val stream = OnDemandLexingTokenInputStream(lexer)
        assertEquals(tokens[0], stream.next())
        assertEquals(tokens[1], stream.next())
        assertEquals(tokens[2], stream.next())
        assertFailsWith<Error> { stream.next() }
    }

    @Test
    fun testHas() {
        val tokens = mutableListOf<Token<TokenType>>()
        tokens.add(Token(TokenType.IDENTIFIER, "test", 0, 0))
        tokens.add(Token(TokenType.NUMBER, "123", 0, 0))
        tokens.add(Token(TokenType.STRING, "\"test\"", 0, 0))
        val lexer = TestLexer(tokens)
        val stream = OnDemandLexingTokenInputStream(lexer)
        assertTrue(stream.has(1))
        assertTrue(stream.has(2))
        assertTrue(stream.has(3))
        assertFalse(stream.has(4))
    }

    @Test
    fun testPeek() {
        val tokens = mutableListOf<Token<TokenType>>()
        tokens.add(Token(TokenType.IDENTIFIER, "test", 0, 0))
        tokens.add(Token(TokenType.NUMBER, "123", 0, 0))
        tokens.add(Token(TokenType.STRING, "\"test\"", 0, 0))
        val lexer = TestLexer(tokens)
        val stream = OnDemandLexingTokenInputStream(lexer)
        assertEquals(tokens[0], stream.peek(1))
        assertEquals(tokens[1], stream.peek(2))
        assertEquals(tokens[2], stream.peek(3))

        assertEquals(tokens[0], stream.next())

        assertEquals(tokens[1], stream.peek(1))
        assertEquals(tokens[2], stream.peek(2))

        assertEquals(tokens[1], stream.next())

        assertEquals(tokens[2], stream.peek(1))

        assertFailsWith<Error> { stream.peek(2) }
    }

    @Test
    fun testSkip() {
        val tokens = mutableListOf<Token<TokenType>>()
        tokens.add(Token(TokenType.IDENTIFIER, "test", 0, 0))
        tokens.add(Token(TokenType.NUMBER, "123", 0, 0))
        tokens.add(Token(TokenType.STRING, "\"test\"", 0, 0))
        val lexer = TestLexer(tokens)
        val stream = OnDemandLexingTokenInputStream(lexer)
        assertEquals(tokens[0], stream.next())
        assertEquals(tokens[1], stream.next())
        assertEquals(tokens[2], stream.next())
        assertFailsWith<Error> { stream.next() }
    }

    @Test
    fun testSkipAmount() {
        val tokens = mutableListOf<Token<TokenType>>()
        tokens.add(Token(TokenType.IDENTIFIER, "test", 0, 0))
        tokens.add(Token(TokenType.NUMBER, "123", 0, 0))
        tokens.add(Token(TokenType.STRING, "\"test\"", 0, 0))
        val lexer = TestLexer(tokens)
        val stream = OnDemandLexingTokenInputStream(lexer)
        stream.skip(3)
        assertFailsWith<Error> { stream.next() }
    }

    @Test
    fun testSize() {
        val tokens = mutableListOf<Token<TokenType>>()
        tokens.add(Token(TokenType.IDENTIFIER, "test", 0, 0))
        tokens.add(Token(TokenType.NUMBER, "123", 0, 0))
        tokens.add(Token(TokenType.STRING, "\"test\"", 0, 0))
        val lexer = TestLexer(tokens)
        val stream = OnDemandLexingTokenInputStream(lexer)
        assertFailsWith<UnsupportedOperationException> { stream.size }
    }

    @Test
    fun testSource() {
        val tokens = mutableListOf<Token<TokenType>>()
        tokens.add(Token(TokenType.IDENTIFIER, "test", 0, 0))
        tokens.add(Token(TokenType.NUMBER, "123", 0, 0))
        tokens.add(Token(TokenType.STRING, "\"test\"", 0, 0))
        val lexer = TestLexer(tokens)
        val stream = OnDemandLexingTokenInputStream(lexer)
        assertEquals("a", stream.source)
    }

    @Test
    fun testPositionMap() {
        val tokens = mutableListOf<Token<TokenType>>()
        tokens.add(Token(TokenType.IDENTIFIER, "test", 0, 0))
        tokens.add(Token(TokenType.NUMBER, "123", 0, 0))
        tokens.add(Token(TokenType.STRING, "\"test\"", 0, 0))
        val lexer = TestLexer(tokens)
        val stream = OnDemandLexingTokenInputStream(lexer)
        assertIs<PositionMap>(stream.map)
    }

    @Test
    fun testToString() {
        val tokens = mutableListOf<Token<TokenType>>()
        tokens.add(Token(TokenType.IDENTIFIER, "test", 0, 0))
        tokens.add(Token(TokenType.NUMBER, "123", 0, 0))
        tokens.add(Token(TokenType.STRING, "\"test\"", 0, 0))
        val lexer = TestLexer(tokens)
        val stream = OnDemandLexingTokenInputStream(lexer)
        assertEquals("OnDemandLexingTokenInputStream(lexer=hello world)", stream.toString())
    }
}
