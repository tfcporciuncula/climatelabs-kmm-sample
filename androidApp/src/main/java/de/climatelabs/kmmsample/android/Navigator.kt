package de.climatelabs.kmmsample.android

import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.navigation.NavController
import de.climatelabs.kmmsample.Destination
import de.climatelabs.kmmsample.NavigationDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class Navigator(
  coroutineScope: CoroutineScope,
  private val activity: ComponentActivity,
  private val navController: NavController,
) : DefaultLifecycleObserver {
  init {
    coroutineScope.launch {
      NavigationDispatcher.requests.collect { processRequest(it) }
    }
  }

  private fun processRequest(request: NavigationDispatcher.Request) {
    when (request.destination) {
      is Destination.Back -> {
        val popUpTo = request.popUpTo
        val didPop = if (popUpTo == null) {
          navController.popBackStack()
        } else {
          navController.popBackStack(popUpTo.destination.route, popUpTo.inclusive)
        }
        if (!didPop) activity.finish()
      }
      else -> {
        navController.navigate(request.destination.buildRoute()) {
          launchSingleTop = request.launchSingleTop
          request.popUpTo?.let { popUpTo ->
            popUpTo(popUpTo.destination.route) { inclusive = popUpTo.inclusive }
          }
        }
      }
    }
  }
}
