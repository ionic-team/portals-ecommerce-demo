import UIKit
import Combine
import SwiftUI

class CartViewController: UIViewController {
    private lazy var viewModel = CartViewModel(dataStore: ShopAPI.dataStore)
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let cartListHostingController = UIHostingController(rootView: CartListView(viewModel: viewModel))
        cartListHostingController.view.frame = view.frame
        view.addSubview(cartListHostingController.view)
        addChild(cartListHostingController)
        cartListHostingController.didMove(toParent: self)
    }
}
