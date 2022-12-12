package convertions

import com.xingpeds.collectionsus.convertions.toSuspendableList
import io.kotest.property.Arb
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.string
import io.kotest.property.forAll
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest as runCoTest

class ToSuspenddableListTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun aList() = runCoTest {
        forAll(Arb.list(Arb.string())) { list ->
            val suspendList = list.toSuspendableList()
            var result = true
            for (i in (0..list.lastIndex)) {
                result = result && (list[i] == suspendList[i].first())
            }
            result
        }
    }
}
