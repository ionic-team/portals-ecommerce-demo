import UIKit
import Combine
import SwiftUI

class CartViewController: UIViewController, ApplicationCoordinationParticipant {
    weak var coordinator: ApplicationCoordinator?
    var requiresPreloading: Bool { return true }
    
    private lazy var viewModel: CartViewModel = {
        guard let coordinator = coordinator else { fatalError("Coordinator must be set") }
        return CartViewModel(coordinator: coordinator)
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
               
        let cartListHostingController = UIHostingController(rootView: CartListView(viewModel: viewModel))
        cartListHostingController.view.frame = view.frame
        view.addSubview(cartListHostingController.view)
        addChild(cartListHostingController)
        cartListHostingController.didMove(toParent: self)
    }
}
