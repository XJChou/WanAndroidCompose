package com.zxj.wanandroid.compose

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.Snapshot
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val state = mutableStateOf(1)

        val snapshot = Snapshot.takeSnapshot()
        state.value = 2
        println("value = " + state.value)

        snapshot.enter {
            println("value = " + state.value)
        }

        println("value = " + state.value)

        snapshot.dispose()
    }
}