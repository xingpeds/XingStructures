package com.xingpeds.collectionsus

interface IteratorSus<out T> {
    suspend fun hasNext(): Boolean

    suspend fun next(): T
}

interface IterableSus<out T> {
    suspend fun iterator(): IteratorSus<T>
}

interface MutableIteratorSus<out T> : IteratorSus<T> {
    /** Removes from the underlying collection the last element returned by this iterator. */
    suspend fun remove()
}

interface MutableIterableSus<out T> : IterableSus<T> {
    /**
     * Returns an iterator over the elements of this sequence that supports removing elements during
     * iteration.
     */
    override suspend fun iterator(): MutableIteratorSus<T>
}

interface ListIteratorSus<out T> : IteratorSus<T> {

    /** Returns `true` if there are elements in the iteration before the current element. */
    suspend fun hasPrevious(): Boolean

    /** Returns the previous element in the iteration and moves the cursor position backwards. */
    suspend fun previous(): T

    /** Returns the index of the element that would be returned by a subsequent call to [next]. */
    suspend fun nextIndex(): Int

    /**
     * Returns the index of the element that would be returned by a subsequent call to [previous].
     */
    suspend fun previousIndex(): Int
}

interface MutableListIteratorSus<T> : ListIteratorSus<T>, MutableIteratorSus<T> {

    // Modification Operations
    override suspend fun remove(): Unit

    /**
     * Replaces the last element returned by [next] or [previous] with the specified element
     * [element].
     */
    suspend fun set(element: T): Unit

    /**
     * Adds the specified element [element] into the underlying collection immediately before the
     * element that would be returned by [next], if any, and after the element that would be
     * returned by [previous], if any. (If the collection contains no elements, the new element
     * becomes the sole element in the collection.) The new element is inserted before the implicit
     * cursor: a subsequent call to [next] would be unaffected, and a subsequent call to [previous]
     * would return the new element. (This call increases by one the value \ that would be returned
     * by a call to [nextIndex] or [previousIndex].)
     */
    suspend fun add(element: T): Unit
}

interface CollectionSus<out T> : IterableSus<T> {
    /** Returns the size of the collection. */
    suspend fun size(): Int

    /** Checks if the specified element is contained in this collection. */
    suspend fun contains(element: Int): Boolean

    /** Checks if all elements in the specified collection are contained in this collection. */
    suspend fun containsAll(elements: CollectionSus<Int>): Boolean

    /** Returns `true` if the collection is empty (contains no elements), `false` otherwise. */
    suspend fun isEmpty(): Boolean
}
/**
 * A generic collection of elements that supports adding and removing elements.
 *
 * @param E the type of elements contained in the collection. The mutable collection is invariant in
 *   its element type.
 */
interface MutableCollectionSus<E> : CollectionSus<E>, MutableIterableSus<E> {
    // Query Operations
    override suspend fun iterator(): MutableIteratorSus<E>

    // Modification Operations
    /**
     * Adds the specified element to the collection.
     *
     * @return `true` if the element has been added, `false` if the collection does not support
     *   duplicates and the element is already contained in the collection.
     */
    suspend fun add(element: E): Boolean

    /**
     * Removes a single instance of the specified element from this collection, if it is present.
     *
     * @return `true` if the element has been successfully removed; `false` if it was not present in
     *   the collection.
     */
    suspend fun remove(element: E): Boolean

    // Bulk Modification Operations
    /**
     * Adds all of the elements of the specified collection to this collection.
     *
     * @return `true` if any of the specified elements was added to the collection, `false` if the
     *   collection was not modified.
     */
    suspend fun addAll(elements: CollectionSus<E>): Boolean

    /**
     * Removes all of this collection's elements that are also contained in the specified
     * collection.
     *
     * @return `true` if any of the specified elements was removed from the collection, `false` if
     *   the collection was not modified.
     */
    suspend fun removeAll(elements: CollectionSus<E>): Boolean

    /**
     * Retains only the elements in this collection that are contained in the specified collection.
     *
     * @return `true` if any element was removed from the collection, `false` if the collection was
     *   not modified.
     */
    suspend fun retainAll(elements: Collection<E>): Boolean

    /** Removes all elements from this collection. */
    suspend fun clear()
}
