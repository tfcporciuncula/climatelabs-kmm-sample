package de.climatelabs.kmmsample

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlin.reflect.KClass

object NavigationDispatcher {
  data class Request(
    val destination: Destination,
    val popUpTo: PopUpTo? = null,
    val launchSingleTop: Boolean = false,
  ) {
    data class PopUpTo(val destination: KClass<out Destination>, val inclusive: Boolean = false)
  }

  private val _requests = Channel<Request>()
  val requests: Flow<Request> = _requests.receiveAsFlow()

  fun to(
    destination: Destination,
    popUpTo: Request.PopUpTo? = null,
    launchSingleTop: Boolean = false,
  ) {
    _requests.trySend(Request(destination, popUpTo, launchSingleTop))
  }

  fun back(popUpTo: Request.PopUpTo? = null) {
    _requests.trySend(Request(Destination.Back, popUpTo))
  }
}
