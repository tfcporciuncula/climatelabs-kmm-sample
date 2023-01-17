package de.climatelabs.kmmsample

import co.touchlab.kermit.Logger
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SamplePresenter : Presenter<SamplePresenter.Model, SamplePresenter.Event>(Model()) {

  init {
    Logger.d { "SamplePresenter (${hashCode()}) created" }
  }

  override suspend fun start(): Unit = coroutineScope {
    Logger.d { "SamplePresenter (${this@SamplePresenter.hashCode()}) started" }

    collectEvents { event ->
      when (event) {
        Event.OnMinus10ButtonClicked -> {
          Logger.d { "SamplePresenter (${this@SamplePresenter.hashCode()}) received OnMinus10ButtonClicked" }
          sendModel { copy(secondsElapsed = secondsElapsed - 10) }
        }
        Event.OnPlus10ButtonClicked -> {
          Logger.d { "SamplePresenter (${this@SamplePresenter.hashCode()}) received OnPlus10ButtonClicked" }
          sendModel { copy(secondsElapsed = secondsElapsed + 10) }
        }
        Event.OnNavigateClicked -> {
          NavigationDispatcher.to(Destination.SimpleDestination)
        }
      }
    }

    launch {
      while (isActive) {
        delay(1000)
        sendModel { copy(secondsElapsed = secondsElapsed + 1) }
      }
    }
  }

  data class Model(
    val secondsElapsed: Int = 0,
    val status: Status = Status.Idle,
  ) {
    enum class Status {
      Idle, Loading, Error
    }
  }

  sealed interface Event {
    object OnMinus10ButtonClicked : Event
    object OnPlus10ButtonClicked : Event
    object OnNavigateClicked : Event
  }
}
