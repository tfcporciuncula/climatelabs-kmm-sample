package de.climatelabs.kmmsample.android

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.navigation.NavHostController

@Composable @NonRestartableComposable
fun NavigatorSetupEffect(activity: ComponentActivity, navController: NavHostController) {
  LaunchedEffect(Unit) {
    Navigator(
      coroutineScope = this,
      activity = activity,
      navController = navController,
    )
  }
}
