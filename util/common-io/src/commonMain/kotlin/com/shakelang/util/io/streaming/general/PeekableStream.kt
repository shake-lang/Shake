package com.shakelang.util.io.streaming.general

interface PeekableStream<T> : Stream<T> {

    /**
     * Peek at the next elements in the stream
     * @param amount The amount of elements to peek
     * @return The peeked elements
     * @since 0.6.0
     */
    fun peek(amount: Int): T

    /**
     * Peek at the next element in the stream
     * @return The peeked element
     * @since 0.6.0
     */
    fun peek(): T

    /**
     * Peek at the next elements in the stream
     * @param amount The amount of elements to peek
     * @return The peeked elements or null if the stream is empty
     * @since 0.6.0
     */
    fun peekOrNull(index: Int): T?

    /**
     * Peek at the next element in the stream
     * @return The peeked element or null if the stream is empty
     * @since 0.6.0
     */
    fun peekOrNull(): T?

    /**
     * Read the next element from the stream
     * @return The next element
     * @since 0.6.0
     */
    override fun read(): T

    /**
     * Check if the stream has a next element
     * @return If the stream has a next element
     * @since 0.6.0
     */
    override fun hasNext(): Boolean

    /**
     * Check if the stream has a certain number of elements
     * @param amount The number of elements to check for
     * @return If the stream has the number of elements
     * @since 0.6.0
     */
    fun has(amount: Int): Boolean
}
