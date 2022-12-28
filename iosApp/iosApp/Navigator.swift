import KMPNativeCoroutinesAsync
import SwiftUI
import shared

@MainActor
class Navigator: ObservableObject {
  @Published var path = [NavigationDispatcher.Request]()

  init() {
    _ = Task {
      for try await request in asyncStream(for: NavigationDispatcher.shared.requestsNative) {
        switch request.destination {
        case is DestinationBack: path.removeLast()
        default: path.append(request)
        }
      }
    }
  }
}

extension View {
  @MainActor func navigationDestinationWithNavigator() -> some View {
    return navigationDestination(for: NavigationDispatcher.Request.self) { request in
      switch request.destination {
      case is DestinationSimpleDestination:
        SimpleDestinationView(viewModel: ViewModel(presenter: SimpleDestinationPresenter()))
      case let destinationWithArg as DestinationDestinationWithArg:
        DestinationWithArgView(viewModel: ViewModel(presenter: DestinationWithArgPresenter(arg: destinationWithArg.arg)))
      case is DestinationBack:
        fatalError("Back is a special cases handled by the Navigator and doesn't map to any view.")
      default:
        fatalError("Invalid destination.")
      }
    }
  }
}
