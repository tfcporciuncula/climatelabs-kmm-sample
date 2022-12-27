import SwiftUI
import shared

struct ContentView: View {
  @StateObject var viewModel: ViewModel<SamplePresenter.Model, SamplePresenterEvent>

  var body: some View {
    VStack {
      Text("Counter: \(viewModel.model.secondsElapsed)")
      Button {
        viewModel.onEvent(SamplePresenterEventOnMinus10ButtonClicked())
      } label: {
        Text("Subtract 10 to counter")
      }
      Button {
        viewModel.onEvent(SamplePresenterEventOnPlus10ButtonClicked())
      } label: {
        Text("Add 10 to counter")
      }
    }
    .task { await viewModel.startNative() }
  }
}

struct ContentView_Previews: PreviewProvider {
  static var previews: some View {
    ContentView(viewModel: ViewModel(model: SamplePresenter.Model(secondsElapsed: 20)))
  }
}

