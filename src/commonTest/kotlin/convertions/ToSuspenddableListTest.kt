@file:OptIn(ExperimentalCoroutinesApi::class)

package convertions

import com.xingpeds.collectionsus.ListSus
import com.xingpeds.collectionsus.convertions.toSuspendableList
import io.kotest.property.Arb
import io.kotest.property.PropertyContext
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.orNull
import io.kotest.property.arbitrary.string
import io.kotest.property.forAll
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest as runCoTest

suspend fun listContentsEqual(list: List<Int?>, suspendList: ListSus<Int?>): Boolean {
    for (i in (0..list.lastIndex)) {
        if (list[i] != suspendList[i].first()) {
            return false
        }
    }
    return true
}

class ToSuspenddableListTest {

    @Test
    fun size() = runCoTest {
        forAll(Arb.list(Arb.int())) {
            val susList = it.toSuspendableList()
            it.size == susList.size()
        }
    }

    @Test
    fun nonNullGetOperator() = runCoTest {
        fun <T> compareLists(): suspend PropertyContext.(List<T>) -> Boolean = { list ->
            val suspendList = list.toSuspendableList()
            var result = true
            for (i in (0..list.lastIndex)) {
                result = result && (list[i] == suspendList[i].first())
            }
            result
        }
        forAll(Arb.list(Arb.string()), compareLists<String>())
        forAll(Arb.list(Arb.int()), compareLists<Int>())
    }

    @Test
    fun nullableGetOperator() = runCoTest {
        forAll(Arb.list(Arb.int().orNull())) { list ->
            listContentsEqual(list, list.toSuspendableList())
        }
    }
    val lastIndexRange = (-500..500)
    @Test
    fun lastIndexOf() = runCoTest {
        forAll(Arb.list(Arb.int(lastIndexRange)), Arb.int(lastIndexRange)) { list, element ->
            val expected =
                try {
                    list.lastIndexOf(element)
                } catch (expectException: Throwable) {
                    try {
                        list.toSuspendableList().lastIndexOf(element)
                        return@forAll false
                    } catch (actualException: Throwable) {
                        return@forAll expectException::class.simpleName ==
                            actualException::class.simpleName
                    }
                }
            val actual = list.toSuspendableList().lastIndexOf(element)
            actual == expected
        }
    }
    @Test
    fun at() = runCoTest {
        forAll(Arb.list(Arb.int())) { list ->
            val susList = list.toSuspendableList()
            for ((index, element) in list.withIndex()) {
                if (element != susList.at(index)) return@forAll false
            }
            true
        }
    }
    @Test
    fun indexOf() = runCoTest {
        forAll(Arb.list(Arb.int()), Arb.int()) { list, element ->
            val expected = list.indexOf(element)
            val actual = list.toSuspendableList().indexOf(element)
            if (expected != actual) {
                println("actual: $actual")
                println("expected: $expected")
            }
            expected == actual
        }
    }
    @Test
    fun contains() = runCoTest {
        forAll(Arb.list(Arb.int((-5000..5000))), Arb.int((-5000..5000))) { list, element ->
            list.contains(element) == list.toSuspendableList().contains(element)
        }
    }
    @Test // this also tests the iterator. oops
    fun containsAllIdentical() = runCoTest {
        // this test is only useful if all other tests pass. meh
        forAll(Arb.list(Arb.int())) { list ->
            list.toSuspendableList().containsAll(list.toSuspendableList())
        }
    }
    @Test
    fun containsAllRandom() = runCoTest {
        forAll(Arb.list(Arb.int()), Arb.list(Arb.int())) { list1, list2 ->
            list1.containsAll(list2) ==
                list1.toSuspendableList().containsAll(list2.toSuspendableList())
        }
    }

    @Test
    fun isEmpty() = runCoTest {
        forAll(Arb.list(Arb.int())) { list -> list.isEmpty() == list.toSuspendableList().isEmpty() }
    }
}

class ToSuspendableListIteratorTest {

    @Test
    fun hasPrevious() = runCoTest {
        forAll(Arb.list(Arb.int())) { list ->
            val expected =
                try {
                    list.listIterator().hasPrevious()
                } catch (e: Throwable) {
                    e
                }
            val actual =
                try {
                    list.toSuspendableList().listIterator().hasPrevious()
                } catch (e: Throwable) {
                    e
                }
            when (actual) {
                is Throwable -> {
                    actual as Throwable == expected as Throwable
                }
                is Boolean -> {
                    actual as Boolean == expected as Boolean
                }
                else -> {
                    false
                }
            }
        }
    }
    @Test
    fun previous() = runCoTest {
        forAll(Arb.list(Arb.int())) {
            val expexted =
                try {
                    it.listIterator().previous()
                } catch (e: Throwable) {
                    e
                }
            val actual =
                try {
                    it.toSuspendableList().listIterator().previous()
                } catch (e: Throwable) {
                    e
                }
            when (actual) {
                is Int -> actual as Int == expexted as Int
                is NoSuchElementException -> expexted is NoSuchElementException
                else -> false
            }
        }
    }
    @Test
    fun nextIndex() = runCoTest {
        forAll(Arb.list(Arb.int())) {
            it.listIterator().nextIndex() == it.toSuspendableList().listIterator().nextIndex()
        }
    }
    @Test
    fun previousIndex() = runCoTest {
        forAll(Arb.list(Arb.int())) {
            it.listIterator().previousIndex() ==
                it.toSuspendableList().listIterator().previousIndex()
        }
    }
    @Test
    fun hasNext() = runCoTest {
        forAll(Arb.list(Arb.int())) {
            it.listIterator().hasNext() == it.toSuspendableList().listIterator().hasNext()
        }
    }
    @Test
    fun next() = runCoTest {
        forAll(Arb.list(Arb.int())) {
            val expected =
                try {
                    it.listIterator().next()
                } catch (e: Throwable) {
                    e
                }
            val actual =
                try {
                    it.toSuspendableList().listIterator().next()
                } catch (e: Throwable) {
                    e
                }
            when (actual) {
                is Int -> actual as Int == expected as Int
                is NoSuchElementException -> expected is NoSuchElementException
                else -> false
            }
        }
    }
}

class ToSuspendableListIteratorTestWithArgs {
    val range = (0..100)
    val listArb = Arb.list(Arb.int(), range)
    val indexArb = Arb.int(range)
    @Test
    fun hasPrevious() = runCoTest {
        forAll<List<Int>, Int>(listArb, indexArb) { list, index ->
            val expected =
                try {
                    list.listIterator(index).hasPrevious()
                } catch (e: Throwable) {
                    e
                }
            val actual =
                try {
                    list.toSuspendableList().listIterator(index).hasPrevious()
                } catch (e: Throwable) {
                    e
                }
            when (actual) {
                is Boolean -> {
                    actual as Boolean == expected as Boolean
                }
                is IndexOutOfBoundsException -> expected is IndexOutOfBoundsException
                else -> {
                    false
                }
            }
        }
    }
    @Test
    fun previous() = runCoTest {
        forAll<List<Int>, Int>(listArb, indexArb) { list, index ->
            val expexted =
                try {
                    list.listIterator(index).previous()
                } catch (e: Throwable) {
                    e
                }
            val actual =
                try {
                    list.toSuspendableList().listIterator(index).previous()
                } catch (e: Throwable) {
                    e
                }
            when (actual) {
                is Int -> actual as Int == expexted as Int
                is NoSuchElementException -> expexted is NoSuchElementException
                is IndexOutOfBoundsException -> expexted is IndexOutOfBoundsException
                else -> false
            }
        }
    }
    @Test
    fun nextIndex() = runCoTest {
        forAll<List<Int>, Int>(listArb, indexArb) { list, index ->
            val expected =
                try {
                    list.listIterator(index).nextIndex()
                } catch (e: Throwable) {
                    e
                }
            val actual =
                try {
                    list.toSuspendableList().listIterator(index).nextIndex()
                } catch (e: Throwable) {
                    e
                }
            when (expected) {
                is Int -> expected as Int == actual as Int
                is IndexOutOfBoundsException -> actual is IndexOutOfBoundsException
                else -> false
            }
        }
    }
    @Test
    fun previousIndex() = runCoTest {
        forAll<List<Int>, Int>(listArb, indexArb) { list, index ->
            val expected =
                try {
                    list.listIterator(index).previousIndex()
                } catch (e: Throwable) {
                    e
                }
            val actual =
                try {
                    list.toSuspendableList().listIterator(index).previousIndex()
                } catch (e: Throwable) {
                    e
                }
            when (actual) {
                is Int -> actual as Int == expected as Int
                is IndexOutOfBoundsException -> expected is IndexOutOfBoundsException
                else -> false
            }
        }
    }
    @Test
    fun hasNext() = runCoTest {
        forAll<List<Int>, Int>(listArb, indexArb) { list, index ->
            val expected =
                try {
                    list.listIterator(index).hasNext()
                } catch (e: Throwable) {
                    e
                }
            val actual =
                try {
                    list.toSuspendableList().listIterator(index).hasNext()
                } catch (e: Throwable) {
                    e
                }
            when (actual) {
                is Boolean -> actual as Boolean == expected as Boolean
                is IndexOutOfBoundsException -> expected is IndexOutOfBoundsException
                else -> false
            }
        }
    }
    @Test
    fun next() = runCoTest {
        forAll<List<Int>, Int>(listArb, indexArb) { list, index ->
            val expected =
                try {
                    list.listIterator(index).next()
                } catch (e: Throwable) {
                    e
                }
            val actual =
                try {
                    list.toSuspendableList().listIterator(index).next()
                } catch (e: Throwable) {
                    e
                }
            when (actual) {
                is Int -> actual as Int == expected as Int
                is NoSuchElementException -> expected is NoSuchElementException
                is IndexOutOfBoundsException -> expected is IndexOutOfBoundsException
                else -> false
            }
        }
    }
}

class ToSuspendingListSubList {
    val range = (0..100)
    @Test
    fun sublist() = runCoTest {
        forAll(Arb.list(Arb.int(), range), Arb.int(range), Arb.int(range)) {
            list,
            fromIndex,
            toIndex ->
            val expected =
                try {
                    list.subList(fromIndex, toIndex)
                } catch (expectedExpection: Throwable) {
                    try {
                        list.toSuspendableList().subList(fromIndex, toIndex)
                        return@forAll false
                    } catch (actualException: Throwable) {
                        return@forAll expectedExpection::class.simpleName ==
                            actualException::class.simpleName
                    }
                    return@forAll false
                }
            val actual = list.toSuspendableList().subList(fromIndex, toIndex)
            listContentsEqual(expected, actual)
        }
    }
}
