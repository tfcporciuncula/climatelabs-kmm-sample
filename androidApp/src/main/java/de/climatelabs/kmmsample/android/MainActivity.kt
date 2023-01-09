package de.climatelabs.kmmsample.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.climatelabs.kmmsample.Destination
import de.climatelabs.kmmsample.DestinationWithArgPresenter
import de.climatelabs.kmmsample.SamplePresenter
import de.climatelabs.kmmsample.SimpleDestinationPresenter
import de.climatelabs.ui.DestinationWithArgScreen
import de.climatelabs.ui.SampleScreen
import de.climatelabs.ui.SimpleDestinationScreen
import dev.marcellogalhardo.retained.activity.retain
import dev.marcellogalhardo.retained.navigation.retain
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
  // We use Retained to take advantage of ViewModels without having any direct ViewModel references
  // (so we're able to keep our presenters multiplatform)
  // https://github.com/marcellogalhardo/retained
  private val presenter by retain { entry ->
    SamplePresenter().also { presenter ->
      entry.scope.launch { presenter.start() } // This starts the presenter on the ViewModel scope.
      // The `entry` also has the `savedStateHandle` and a hook for `onCleared()`.
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      val navController = rememberNavController()
      NavigatorSetupEffect(navController)

      MaterialTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colors.background
        ) {
          NavHost(
            navController = navController,
            startDestination = Destination.Counter::class.route,
          ) {
            composable(route = Destination.Counter::class.route) {
              val state by presenter.models.collectAsState()
              SampleScreen(
                secondsElapsed = state.secondsElapsed,
                today = state.today,
                onMinus10ButtonClicked = { presenter.onEvent(SamplePresenter.Event.OnMinus10ButtonClicked) },
                onPlus10ButtonClicked = { presenter.onEvent(SamplePresenter.Event.OnPlus10ButtonClicked) },
                onNavigateButtonClicked = { presenter.onEvent(SamplePresenter.Event.OnNavigateClicked) },
              )
            }
            composable(route = Destination.SimpleDestination::class.route) {
              // We normally have an extension to avoid repeating this, but I'm leaving it around for explicitness.
              val presenter by it.retain { entry ->
                SimpleDestinationPresenter().also { presenter ->
                  entry.scope.launch { presenter.start() }
                }
              }
              val state by presenter.models.collectAsState()
              SimpleDestinationScreen(
                argText = state.argText,
                onArgTextChanged = { presenter.onEvent(SimpleDestinationPresenter.Event.OnArgTextChanged(it)) },
                onNavigateClicked = {
                  presenter.onEvent(SimpleDestinationPresenter.Event.OnNavigateToDestinationWithArgClicked)
                },
                onBackClicked = { presenter.onEvent(SimpleDestinationPresenter.Event.OnBackClicked) },
              )
            }
            composable(route = Destination.DestinationWithArg::class.route) {
              val arg = it.arguments?.getString(Destination.DestinationWithArg::class.arg)!!
              val presenter by it.retain { entry ->
                DestinationWithArgPresenter(arg).also { presenter ->
                  entry.scope.launch { presenter.start() }
                }
              }
              val state by presenter.models.collectAsState()
              DestinationWithArgScreen(
                arg = state.arg,
                onBackClicked = { presenter.onEvent(DestinationWithArgPresenter.Event.OnBackClicked) }
              )
            }
          }
        }
      }
    }
  }
}
