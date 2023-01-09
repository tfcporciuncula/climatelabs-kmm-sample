package de.climatelabs.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SimpleDestinationScreen(
  argText: String,
  onArgTextChanged: (String) -> Unit,
  onNavigateClicked: () -> Unit,
  onBackClicked: () -> Unit,
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {

    Text(text = "Simple destination")
    TextField(
      value = argText,
      onValueChange = onArgTextChanged,
      label = { Text("arg text here") },
    )
    Button(
      onClick = onNavigateClicked,
      enabled = argText.isNotBlank(),
    ) {
      Text(text = "Go to destination with arg")
    }
    Button(onClick = onBackClicked) {
      Text(text = "Go back")
    }
  }
}
