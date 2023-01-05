import KMPNativeCoroutinesAsync
import SwiftUI
import shared

@MainActor
class Navigator: ObservableObject {
  @Published var path = [NavigationDispatcher.Request]()

  init() {
    _ = Task {
      for try await request in asyncStream(for: NavigationDispatcher.shared.requestsNative) {
        switch onEnum(of: request.destination) {
        case .Back: path.removeLast()
        default: path.append(request)
        }
      }
    }
  }
}

extension View {
  @MainActor func navigationDestinationWithNavigator() -> some View {
    return navigationDestination(for: NavigationDispatcher.Request.self) { request in
      switch onEnum(of: request.destination) {
      case .SimpleDestination:
        SimpleDestinationView(viewModel: ViewModel(presenter: SimpleDestinationPresenter()))
      case .DestinationWithArg(let destination):
        DestinationWithArgView(viewModel: ViewModel(presenter: DestinationWithArgPresenter(arg: destination.arg)))
      case .Back:
        fatalError("Back is a special cases handled by the Navigator and doesn't map to any view.")
      default:
        fatalError("Invalid destination.")
      }
    }
  }
}
