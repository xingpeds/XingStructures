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
        override suspend fun at(index: Int): E = list[index]

        private inner class ListIterator(
            private val iter: kotlin.collections.ListIterator<E> = list.listIterator()
        ) : ListIteratorSus<E> {
            /** Returns `true` if there are elements in the iteration before the current element. */
            override suspend fun hasPrevious(): Boolean = iter.hasPrevious()

            /**
             * Returns the previous element in the iteration and moves the cursor position
             * backwards.
             */
            override suspend fun previous(): E = iter.previous()

            /**
             * Returns the index of the element that would be returned by a subsequent call to
             * [next].
             */
            override suspend fun nextIndex(): Int = iter.nextIndex()

            /**
             * Returns the index of the element that would be returned by a subsequent call to
             * [previous].
             */
            override suspend fun previousIndex(): Int = iter.previousIndex()

            override suspend fun hasNext(): Boolean = iter.hasNext()

            override suspend fun next(): E = iter.next()
        }

        /** Returns a list iterator over the elements in this list (in proper sequence). */
        override suspend fun listIterator(): ListIteratorSus<E> = ListIterator(list.listIterator())
        /**
         * Returns a list iterator over the elements in this list (in proper sequence), starting at
         * the specified [index].
         */
        override suspend fun listIterator(index: Int): ListIteratorSus<E> =
            ListIterator(list.listIterator(index))

        /**
         * Returns a view of the portion of this list between the specified [fromIndex] (inclusive)
         * and [toIndex] (exclusive). The returned list is backed by this list, so non-structural
         * changes in the returned list are reflected in this list, and vice-versa.
         *
         * Structural changes in the base list make the behavior of the view undefined.
         */
        override suspend fun subList(fromIndex: Int, toIndex: Int): ListSus<E> {
            return list.subList(fromIndex, toIndex).toSuspendableList()
        }

        /**
         * Returns the index of the last occurrence of the specified element in the list, or -1 if
         * the specified element is not contained in the list.
         */
        override suspend fun lastIndexOf(element: E): Int = list.lastIndexOf(element)

        /**
         * Returns the index of the first occurrence of the specified element in the list, or -1 if
         * the specified element is not contained in the list.
         */
        override suspend fun indexOf(element: E): Int = list.lastIndexOf(element)

        /** Returns the size of the collection. */
        override suspend fun size(): Int = list.size

        /** Checks if the specified element is contained in this collection. */
        override suspend fun contains(element: E): Boolean = list.contains(element)

        /** Checks if all elements in the specified collection are contained in this collection. */
        override suspend fun containsAll(elements: CollectionSus<E>): Boolean {
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
