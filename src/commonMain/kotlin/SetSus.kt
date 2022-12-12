package com.xingpeds.collectionsus

/**
 * A generic unordered collection of elements that does not support duplicate elements. Methods in
 * this interface support only read-only access to the set; read/write access is supported through
 * the [MutableSet] interface.
 *
 * @param E the type of elements contained in the set. The set is covariant in its element type.
 */
interface SetSus<out E> : CollectionSus<E>

/**
 * A generic unordered collection of elements that does not support duplicate elements, and supports
 * adding and removing elements.
 *
 * @param E the type of elements contained in the set. The mutable set is invariant in its element
 *   type.
 */
interface MutableSetSus<E> : SetSus<E>, MutableCollectionSus<E>
