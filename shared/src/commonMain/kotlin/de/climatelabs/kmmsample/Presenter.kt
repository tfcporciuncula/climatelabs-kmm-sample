package de.climatelabs.kmmsample

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class Presenter<ModelT : Any, EventT : Any>(initialModel: ModelT) {
  private val _models = MutableStateFlow(initialModel)
  val models: StateFlow<ModelT> = _models.asStateFlow()

  private val _events = Channel<EventT>()
  fun onEvent(event: EventT) {
    _events.trySend(event)
  }

  abstract suspend fun start()

  protected fun sendModel(transform: (ModelT.() -> ModelT)) {
    _models.update(transform)
  }

  protected suspend fun Flow<ModelT>.collectAndSend() = collect {
    _models.value = it
  }

  protected fun CoroutineScope.collectEvents(func: suspend (EventT) -> Unit) {
    launch {
      _events.receiveAsFlow().collect { func(it) }
    }
  }
}
