package com.xingpeds.collectionsus.convertions

import com.xingpeds.collectionsus.CollectionSus
import com.xingpeds.collectionsus.IteratorSus
import com.xingpeds.collectionsus.ListIteratorSus
import com.xingpeds.collectionsus.ListSus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

fun <E> List<E>.toSuspendableList(): ListSus<E> {
    val list = this
    return object : ListSus<E> {
        /** Returns the element at the specified index in the list. */
        override fun get(index: Int): Flow<E> = flowOf(list[index])

        /** Returns a list iterator over the elements in this list (in proper sequence). */
        override suspend fun listIterator(): ListIteratorSus<E> {
            TODO("Not yet implemented")
        }

        /**
         * Returns a list iterator over the elements in this list (in proper sequence), starting at
         * the specified [index].
         */
        override suspend fun listIterator(index: Int): ListIteratorSus<E> {
            TODO("Not yet implemented")
        }

        /**
         * Returns a view of the portion of this list between the specified [fromIndex] (inclusive)
         * and [toIndex] (exclusive). The returned list is backed by this list, so non-structural
         * changes in the returned list are reflected in this list, and vice-versa.
         *
         * Structural changes in the base list make the behavior of the view undefined.
         */
        override suspend fun subList(fromIndex: Int, toIndex: Int): List<E> {
            TODO("Not yet implemented")
        }

        /**
         * Returns the index of the last occurrence of the specified element in the list, or -1 if
         * the specified element is not contained in the list.
         */
        override suspend fun lastIndexOf(element: E): Int {
            TODO("Not yet implemented")
        }

        /**
         * Returns the index of the first occurrence of the specified element in the list, or -1 if
         * the specified element is not contained in the list.
         */
        override suspend fun indexOf(element: E): Int {
            TODO("Not yet implemented")
        }

        /** Returns the size of the collection. */
        override suspend fun size(): Int {
            TODO("Not yet implemented")
        }

        /** Checks if the specified element is contained in this collection. */
        override suspend fun contains(element: Int): Boolean {
            TODO("Not yet implemented")
        }

        /** Checks if all elements in the specified collection are contained in this collection. */
        override suspend fun containsAll(elements: CollectionSus<Int>): Boolean {
            TODO("Not yet implemented")
        }

        /** Returns `true` if the collection is empty (contains no elements), `false` otherwise. */
        override suspend fun isEmpty(): Boolean {
            TODO("Not yet implemented")
        }

        override suspend fun iterator(): IteratorSus<E> {
            TODO("Not yet implemented")
        }
    }
}
