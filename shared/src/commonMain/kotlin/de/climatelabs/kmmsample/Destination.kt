package de.climatelabs.kmmsample

sealed interface Destination {
  object Back : Destination
  object Counter : Destination
  object SimpleDestination : Destination
  data class DestinationWithArg(val arg: String) : Destination
}
