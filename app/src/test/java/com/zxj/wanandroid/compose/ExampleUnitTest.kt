package com.zxj.wanandroid.compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.runtime.snapshots.StateObject
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
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
        var state by mutableStateOf("1")
//        val snapshot = Snapshot.takeSnapshot()
//        snapshot.dispose()
//        state.value = "2"
//        val head = (state as StateObject).firstStateRecord
//        head.toString()
//
        Snapshot.registerApplyObserver { anies, snapshot ->
            println("snapshot[${snapshot.id}]: ${anies}")
        }
        Snapshot.sendApplyNotifications()
        Snapshot.withMutableSnapshot {
            Snapshot.withMutableSnapshot {
                state = "2"
            }
//            state = "1"
        }
        runBlocking { delay(1000) }
    }
}