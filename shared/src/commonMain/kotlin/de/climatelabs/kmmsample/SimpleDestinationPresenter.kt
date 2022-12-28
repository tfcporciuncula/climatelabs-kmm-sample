package de.climatelabs.kmmsample

import co.touchlab.kermit.Logger
import kotlinx.coroutines.coroutineScope

class SimpleDestinationPresenter :
  Presenter<SimpleDestinationPresenter.Model, SimpleDestinationPresenter.Event>(Model()) {

  init {
    Logger.d { "SimpleDestinationPresenter (${hashCode()}) created" }
  }

  override suspend fun start(): Unit = coroutineScope {
    val hashCode = this@SimpleDestinationPresenter.hashCode()
    Logger.d { "SimpleDestinationPresenter ($hashCode) started" }

    collectEvents { event ->
      when (event) {
        is Event.OnArgTextChanged -> {
          Logger.d { "SimpleDestinationPresenter ($hashCode}) received OnArgTextChanged" }
          sendModel { copy(argText = event.text) }
        }
        Event.OnBackClicked -> {
          Logger.d { "SimpleDestinationPresenter ($hashCode}) received OnBackClicked" }
          NavigationDispatcher.back()
        }
        Event.OnNavigateToDestinationWithArgClicked -> {
          Logger.d { "SimpleDestinationPresenter ($hashCode}) received OnNavigateToDestinationWithArgClicked" }
          NavigationDispatcher.to(Destination.DestinationWithArg(models.value.argText))
        }
      }
    }
  }

  data class Model(
    val argText: String = "",
  )

  sealed interface Event {
    data class OnArgTextChanged(val text: String) : Event
    object OnNavigateToDestinationWithArgClicked : Event
    object OnBackClicked : Event
  }
}
