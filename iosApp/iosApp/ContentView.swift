import SwiftUI
import shared

struct ContentView: View {
  @StateObject var viewModel: ViewModel<SamplePresenter.Model, SamplePresenterEvent>

  var body: some View {
    VStack {
      Text("Counter: \(viewModel.model.secondsElapsed)")
      Text("Today is \(viewModel.model.today.formatted)")
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
      Button {
        viewModel.onEvent(SamplePresenterEventOnNavigateClicked())
      } label: {
        Text("Navigate to simple destination")
      }
    }
    .task { await viewModel.startNative() }
  }
}

struct SimpleDestinationView: View {
  @StateObject var viewModel: ViewModel<SimpleDestinationPresenter.Model, SimpleDestinationPresenterEvent>

  var body: some View {
    VStack {
      Text("Simple destination")
      let argText = Binding {
        viewModel.model.argText
      } set: { text in
        viewModel.onEvent(SimpleDestinationPresenterEventOnArgTextChanged(text: text))
      }
      TextField("arg text here", text: argText)
        .frame(width: 250)
      Button {
        viewModel.onEvent(SimpleDestinationPresenterEventOnNavigateToDestinationWithArgClicked())
      } label: {
        Text("Go to destination with arg")
      }
      .disabled(viewModel.model.argText.count == 0)
      Button {
        viewModel.onEvent(SimpleDestinationPresenterEventOnBackClicked())
      } label: {
        Text("Go back")
      }
    }
    .task { await viewModel.startNative() }
  }
}

struct DestinationWithArgView: View {
  @StateObject var viewModel: ViewModel<DestinationWithArgPresenter.Model, DestinationWithArgPresenterEvent>

  var body: some View {
    VStack {
      Text("Destination with arg, arg is \(viewModel.model.arg)")
      Button {
        viewModel.onEvent(DestinationWithArgPresenterEventOnBackClicked())
      } label: {
        Text("Go back")
      }
    }
    .task { await viewModel.startNative() }
  }
}

struct ContentView_Previews: PreviewProvider {
  static var previews: some View {
    ContentView(viewModel: ViewModel(model: SamplePresenter.Model(secondsElapsed: 20, today: DateTimeFactory.shared.instant())))
  }
}
