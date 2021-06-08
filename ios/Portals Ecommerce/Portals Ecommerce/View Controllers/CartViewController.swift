import UIKit

class CartViewController: UIViewController, ApplicationCoordinationParticipant {
    weak var coordinator: ApplicationCoordinator?
    
    @IBOutlet private weak var tableView: UITableView!
    @IBOutlet private weak var emptyCartContainerView: UIView!
    @IBOutlet private weak var textView: UITextView!
    
    private var viewModel: CartViewModel = CartViewModel()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        textView.text = ""
        emptyCartContainerView.isHidden = true
        
        if let cart = coordinator?.dataStore.cart {
            viewModel.cart = cart
        }
        viewModel.imageLoader = coordinator?.dataStore.imageLoader
        viewModel.configure(with: tableView)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        if let cart = coordinator?.dataStore.cart {
            if let data = try? JSONEncoder().encode(cart), let cartText = String(data: data, encoding: .utf8) {
                textView.text = cartText
            }
        }
    }
}
