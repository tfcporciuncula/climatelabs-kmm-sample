package de.climatelabs.kmmsample

import co.touchlab.kermit.Logger
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

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
        Event.OnNavigateButtonClicked -> {
          NavigationDispatcher.to(Destination.SimpleDestination)
        }
        Event.OnPromptButtonClicked -> {
          sendModel { copy(promptVisible = true) }
        }
        Event.OnPromptDismissed -> {
          sendModel { copy(promptVisible = false) }
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
    val today: Instant = Clock.System.now(),
    val promptVisible: Boolean = false,
  )

  sealed interface Event {
    object OnMinus10ButtonClicked : Event
    object OnPlus10ButtonClicked : Event
    object OnNavigateButtonClicked : Event
    object OnPromptButtonClicked : Event
    object OnPromptDismissed : Event
  }
}
