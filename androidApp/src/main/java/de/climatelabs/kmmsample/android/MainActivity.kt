package de.climatelabs.kmmsample.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.climatelabs.kmmsample.Destination
import de.climatelabs.kmmsample.DestinationWithArgPresenter
import de.climatelabs.kmmsample.SamplePresenter
import de.climatelabs.kmmsample.SimpleDestinationPresenter
import de.climatelabs.kmmsample.formatted
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
              SampleScreen(presenter)
            }
            composable(route = Destination.SimpleDestination::class.route) {
              // We normally have an extension to avoid repeating this, but I'm leaving it around for explicitness.
              val presenter by it.retain { entry ->
                SimpleDestinationPresenter().also { presenter ->
                  entry.scope.launch { presenter.start() }
                }
              }
              SimpleDestinationScreen(presenter)
            }
            composable(route = Destination.DestinationWithArg::class.route) {
              val arg = it.arguments?.getString(Destination.DestinationWithArg::class.arg)!!
              val presenter by it.retain { entry ->
                DestinationWithArgPresenter(arg).also { presenter ->
                  entry.scope.launch { presenter.start() }
                }
              }
              DestinationWithArgScreen(presenter)
            }
          }
        }
      }
    }
  }
}

@Composable
private fun SampleScreen(presenter: SamplePresenter) {
  val state by presenter.models.collectAsState()
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(text = "Counter: ${state.secondsElapsed}")
    Text(text = "Today is ${state.today.formatted}")
    Button(onClick = { presenter.onEvent(SamplePresenter.Event.OnMinus10ButtonClicked) }) {
      Text(text = "Subtract 10 to counter")
    }
    Button(onClick = { presenter.onEvent(SamplePresenter.Event.OnPlus10ButtonClicked) }) {
      Text(text = "Add 10 to counter")
    }
    Button(onClick = { presenter.onEvent(SamplePresenter.Event.OnNavigateClicked) }) {
      Text(text = "Navigate to simple destination")
    }
  }
}

@Composable
fun SimpleDestinationScreen(presenter: SimpleDestinationPresenter) {
  val state by presenter.models.collectAsState()
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {

    Text(text = "Simple destination")
    TextField(
      value = state.argText,
      onValueChange = { presenter.onEvent(SimpleDestinationPresenter.Event.OnArgTextChanged(it)) },
      label = { Text("arg text here") },
    )
    Button(
      onClick = { presenter.onEvent(SimpleDestinationPresenter.Event.OnNavigateToDestinationWithArgClicked) },
      enabled = state.argText.isNotBlank(),
    ) {
      Text(text = "Go to destination with arg")
    }
    Button(onClick = { presenter.onEvent(SimpleDestinationPresenter.Event.OnBackClicked) }) {
      Text(text = "Go back")
    }
  }
}

@Composable
fun DestinationWithArgScreen(presenter: DestinationWithArgPresenter) {
  val state by presenter.models.collectAsState()
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(text = "Destination with arg, arg is ${state.arg}")
    Button(onClick = { presenter.onEvent(DestinationWithArgPresenter.Event.OnBackClicked) }) {
      Text(text = "Go back")
    }
  }
}
