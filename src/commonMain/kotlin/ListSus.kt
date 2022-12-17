package com.xingpeds.collectionsus

import kotlinx.coroutines.flow.Flow

interface ListSus<out E> : CollectionSus<E> {

    // Bulk Operations

    // Positional Access Operations
    /** Returns the element at the specified index in the list. */
    operator fun get(index: Int): Flow<E>

    /** return the element at the specified index. Use this if you do not want to use Flows */
    suspend fun at(index: Int): E

    // Search Operations
    /**
     * Returns the index of the first occurrence of the specified element in the list, or -1 if the
     * specified element is not contained in the list.
     */
    suspend fun indexOf(element: @UnsafeVariance E): Int

    /**
     * Returns the index of the last occurrence of the specified element in the list, or -1 if the
     * specified element is not contained in the list.
     */
    suspend fun lastIndexOf(element: @UnsafeVariance E): Int

    // List Iterators
    /** Returns a list iterator over the elements in this list (in proper sequence). */
    suspend fun listIterator(): ListIteratorSus<E>

    /**
     * Returns a list iterator over the elements in this list (in proper sequence), starting at the
     * specified [index].
     */
    suspend fun listIterator(index: Int): ListIteratorSus<E>

    // View
    /**
     * Returns a view of the portion of this list between the specified [fromIndex] (inclusive) and
     * [toIndex] (exclusive). The returned list is backed by this list, so non-structural changes in
     * the returned list are reflected in this list, and vice-versa.
     *
     * Structural changes in the base list make the behavior of the view undefined.
     */
    suspend fun subList(fromIndex: Int, toIndex: Int): ListSus<E>
}

interface MutableListSus<E> : ListSus<E>, MutableCollectionSus<E> {
    operator fun set(index: Int, element: E): Flow<E>

    /** Inserts an element into the list at the specified [index]. */
    suspend fun add(index: Int, element: E)

    /**
     * Removes an element at the specified [index] from the list.
     *
     * @return the element that has been removed.
     */
    suspend fun removeAt(index: Int): E
}
