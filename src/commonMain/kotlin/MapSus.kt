package com.xingpeds.collectionsus

import kotlin.collections.Map
import kotlinx.coroutines.flow.Flow

public interface MapSus<K, out V> {
    // Query Operations
    /** Returns the number of key/value pairs in the map. */
    suspend fun size(): Int

    /** Returns `true` if the map is empty (contains no elements), `false` otherwise. */
    suspend fun isEmpty(): Boolean

    /** Returns `true` if the map contains the specified [key]. */
    suspend fun containsKey(key: K): Boolean

    /** Returns `true` if the map maps one or more keys to the specified [value]. */
    suspend fun containsValue(value: @UnsafeVariance V): Boolean

    /**
     * Returns the value corresponding to the given [key], or `null` if such a key is not present in
     * the map.
     */
    operator fun get(key: K): Flow<V?>

    // Views
    /** Returns a read-only [Set] of all keys in this map. */
    suspend fun keys(): SetSus<K>

    /**
     * Returns a read-only [Collection] of all values in this map. Note that this collection may
     * contain duplicate values.
     */
    suspend fun values(): CollectionSus<V>

    /** Returns a read-only [Set] of all key/value pairs in this map. */
    suspend fun entries(): SetSus<MapSus.EntrySus<K, V>>

    /** Represents a key/value pair held by a [Map]. */
    interface EntrySus<out K, out V> {
        /** Returns the key of this key/value pair. */
        suspend fun key(): K

        /** Returns the value of this key/value pair. */
        suspend fun value(): V
    }
}

/**
 * A modifiable collection that holds pairs of objects (keys and values) and supports efficiently
 * retrieving the value corresponding to each key. Map keys are unique; the map holds only one value
 * for each key.
 *
 * @param K the type of map keys. The map is invariant in its key type.
 * @param V the type of map values. The mutable map is invariant in its value type.
 */
public interface MutableMapSus<K, V> : MapSus<K, V> {
    // Modification Operations
    /**
     * Associates the specified [value] with the specified [key] in the map.
     *
     * @return the previous value associated with the key, or `null` if the key was not present in
     *   the map.
     */
    suspend fun put(key: K, value: V): V?

    /**
     * Removes the specified key and its corresponding value from this map.
     *
     * @return the previous value associated with the key, or `null` if the key was not present in
     *   the map.
     */
    suspend fun remove(key: K): V?

    // Bulk Modification Operations
    /** Updates this map with key/value pairs from the specified map [from]. */
    suspend fun putAll(from: MapSus<out K, V>): Unit

    /** Removes all elements from this map. */
    suspend fun clear(): Unit

    /** Returns a [MutableSet] of all keys in this map. */
    override suspend fun keys(): MutableSetSus<K>

    /**
     * Returns a [MutableCollection] of all values in this map. Note that this collection may
     * contain duplicate values.
     */
    override suspend fun values(): MutableCollectionSus<V>

    /** Returns a [MutableSet] of all key/value pairs in this map. */
    override suspend fun entries(): MutableSetSus<MutableMapSus.MutableEntrySus<K, V>>

    /** Represents a key/value pair held by a [MutableMap]. */
    interface MutableEntrySus<K, V> : MapSus.EntrySus<K, V> {
        /**
         * Changes the value associated with the key of this entry.
         *
         * @return the previous value corresponding to the key.
         */
        suspend fun setValue(newValue: V): V
    }
}
