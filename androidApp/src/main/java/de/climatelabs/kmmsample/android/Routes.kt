package de.climatelabs.kmmsample.android

import de.climatelabs.kmmsample.Destination
import kotlin.reflect.KClass

val KClass<out Destination>.route
  get() = when (this) {
    Destination.Back::class -> "back"
    Destination.Counter::class -> "counter"
    Destination.SimpleDestination::class -> "simpleDestination"
    Destination.DestinationWithArg::class -> "destinationWithArg/{${Destination.DestinationWithArg::class.arg}}"
    else -> error("No route for invalid destination: $qualifiedName")
  }

fun Destination.buildRoute() = when (this) {
  is Destination.DestinationWithArg -> {
    this::class.route.replace("{${Destination.DestinationWithArg::class.arg}}", arg)
  }
  else -> this::class.route
}

val KClass<Destination.DestinationWithArg>.arg get() = "arg"
