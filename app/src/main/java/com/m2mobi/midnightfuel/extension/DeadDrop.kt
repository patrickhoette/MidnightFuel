package com.m2mobi.midnightfuel.extension

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.State.CREATED
import androidx.lifecycle.whenStateAtLeast
import kotlinx.coroutines.flow.*
import java.util.concurrent.atomic.AtomicBoolean

class MutableDeadDrop<T : Any> private constructor(
    private val backingFlow: MutableStateFlow<Package<T>?>,
) : MutableStateFlow<Package<T>?> by backingFlow {

    constructor() : this(MutableStateFlow(null))

    fun place(data: T) {
        value = Package(data)
    }

    fun asDeadDrop() = DeadDrop(this)
}

class DeadDrop<out T : Any> constructor(
    private val mutable: MutableDeadDrop<T>
) : StateFlow<Package<T>?> by mutable {

    suspend fun retrieveEach(collector: suspend (T) -> Unit) {
        mutable.filterNotNull()
            .filterNot { it.isRetrieved }
            .collectLatest { it.retrieve(collector) }
    }
}

data class Package<out T : Any>(
    private val data: T,
) {

    private val _isRetrieved = AtomicBoolean(false)
    val isRetrieved: Boolean
        get() = _isRetrieved.get()

    suspend fun retrieve(collector: suspend (T) -> Unit) {
        if (!_isRetrieved.getAndSet(true)) collector(data)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Package<*>

        if (data != other.data) return false
        if (isRetrieved != other.isRetrieved) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data.hashCode()
        result = 31 * result + _isRetrieved.hashCode()
        return result
    }
}

@Composable
@SuppressLint("ComposableNaming")
fun <T : Any> DeadDrop<T>.retrieveAsEffect(minState: Lifecycle.State = CREATED, collector: suspend (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner, collector) {
        retrieveEach {
            lifecycleOwner.lifecycle.whenStateAtLeast(minState) { collector(it) }
        }
    }
}
