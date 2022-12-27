import SwiftUI
import KMPNativeCoroutinesAsync
import KMPNativeCoroutinesCore
import shared

class ViewModel<ModelT: AnyObject, EventT: AnyObject>: ObservableObject {
  @Published var model: ModelT
  let onEvent: (EventT) -> Void
  private let presenter: Presenter<ModelT, EventT>?

  init(presenter: Presenter<ModelT, EventT>) {
    self.model = presenter.modelsNativeValue
    self.onEvent = presenter.onEvent
    self.presenter = presenter
  }

  init(model: ModelT) {
    self.model = model
    self.onEvent = {_ in }
    self.presenter = nil
  }

  @MainActor func startNative() async {
    guard let presenter = presenter else {
      return
    }

    let modelsTask = Task {
      for try await model in asyncStream(for: presenter.modelsNative) {
        self.model = model
      }
    }
    defer { modelsTask.cancel() }

    _ = await asyncResult(for: presenter.startNative())
  }
}
