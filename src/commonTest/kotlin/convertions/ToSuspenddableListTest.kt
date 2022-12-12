package convertions

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

class ToSuspenddableListTest {
    @OptIn(ExperimentalCoroutinesApi::class)
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
            val suspendList = list.toSuspendableList()
            for (i in (0..list.lastIndex)) {
                if (list[i] != suspendList[i].first()) {
                    return@forAll false
                }
            }
            true
        }
    }
}
