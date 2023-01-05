package de.climatelabs.kmmsample

import kotlinx.datetime.Clock
import kotlinx.datetime.toKotlinInstant
import platform.Foundation.NSDate

// Since we expose Kotlin datetime directly to iOS, we provide an easy way to create instances of it for previews
object DateTimeFactory {
  fun instant() = Clock.System.now()
  fun instantFrom(date: NSDate) = date.toKotlinInstant()
}
