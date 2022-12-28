package de.climatelabs.kmmsample

import co.touchlab.kermit.Logger
import kotlinx.coroutines.coroutineScope

class DestinationWithArgPresenter(
  arg: String,
) : Presenter<DestinationWithArgPresenter.Model, DestinationWithArgPresenter.Event>(Model(arg)) {

  init {
    Logger.d { "DestinationWithArgPresenter (${hashCode()}) created" }
  }

  override suspend fun start(): Unit = coroutineScope {
    val hashCode = this@DestinationWithArgPresenter.hashCode()
    Logger.d { "DestinationWithArgPresenter ($hashCode) started" }

    collectEvents { event ->
      when (event) {
        Event.OnBackClicked -> {
          Logger.d { "DestinationWithArgPresenter ($hashCode}) received OnBackClicked" }
          NavigationDispatcher.back()
        }
      }
    }
  }

  data class Model(
    val arg: String,
  )

  sealed interface Event {
    object OnBackClicked : Event
  }
}
