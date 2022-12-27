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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.climatelabs.kmmsample.SamplePresenter
import dev.marcellogalhardo.retained.activity.retain
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
      MaterialTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colors.background
        ) {
          SampleScreen(presenter)
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
    Button(onClick = { presenter.onEvent(SamplePresenter.Event.OnMinus10ButtonClicked) }) {
      Text(text = "Subtract 10 to counter")
    }
    Button(onClick = { presenter.onEvent(SamplePresenter.Event.OnPlus10ButtonClicked) }) {
      Text(text = "Add 10 to counter")
    }
  }
}
