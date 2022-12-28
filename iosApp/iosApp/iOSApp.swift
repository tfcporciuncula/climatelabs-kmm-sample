import SwiftUI
import shared

@main
struct iOSApp: App {
  @StateObject private var navigator = Navigator()

  var body: some Scene {
    WindowGroup {
      NavigationStack(path: $navigator.path) {
        ContentView(viewModel: ViewModel(presenter: SamplePresenter()))
          .navigationDestinationWithNavigator()
      }
    }
  }
}

