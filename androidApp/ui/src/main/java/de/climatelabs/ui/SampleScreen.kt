package de.climatelabs.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.climatelabs.kmmsample.formatted
import kotlinx.datetime.Instant

@Composable
fun SampleScreen(
  secondsElapsed: Int,
  today: Instant,
  dialogVisible: Boolean,
  onMinus10ButtonClicked: () -> Unit,
  onPlus10ButtonClicked: () -> Unit,
  onNavigateButtonClicked: () -> Unit,
  onDialogButtonClicked: () -> Unit,
  onDialogDismissed: () -> Unit,
) {
  if (dialogVisible) {
    AlertDialog(
      onDismissRequest = onDialogDismissed,
      buttons = {},
      title = { Text(text = "Hallo") },
      text = { Text(text = "I am a dialog!") },
    )
  }

  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(text = "Counter: $secondsElapsed")
    Text(text = "Today is ${today.formatted}")
    Button(onClick = onMinus10ButtonClicked) {
      Text(text = "Subtract 10 to counter")
    }
    Button(onClick = onPlus10ButtonClicked) {
      Text(text = "Add 10 to counter")
    }
    Button(onClick = onNavigateButtonClicked) {
      Text(text = "Navigate to simple destination")
    }
    Button(onClick = onDialogButtonClicked) {
      Text(text = "Show a dialog")
    }
  }
}
