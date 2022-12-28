package de.climatelabs.kmmsample.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.navigation.NavHostController

@Composable
@NonRestartableComposable
fun NavigatorSetupEffect(navController: NavHostController) {
  LaunchedEffect(Unit) {
    Navigator(coroutineScope = this, navController = navController)
  }
}
