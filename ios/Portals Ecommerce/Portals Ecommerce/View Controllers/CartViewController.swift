import UIKit
import Combine

class CartViewController: UIViewController, ApplicationCoordinationParticipant, CartInteractionDelegate {
    weak var coordinator: ApplicationCoordinator?
    var requiresPreloading: Bool { return true }
    
    @IBOutlet private weak var tableView: UITableView!
    @IBOutlet private weak var emptyCartContainerView: UIView!
    
    private var cancellables: Set<AnyCancellable> = []
    private var viewModel: CartViewModel = CartViewModel()
    private var pickerInput = QuantityPickerInputView()
    private var selectedItem: Cart.Item?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // we don't have an easy way to customize the badge appearance, so instead we will make
        // the badge clear and customize the appearance of the string that we set in it
        tabBarItem.badgeColor = .clear
        tabBarItem.setBadgeTextAttributes([.foregroundColor: UIColor(displayP3Red: 255/255.0, green: 184/255.0, blue: 0/255.0, alpha: 1), .font : UIFont.systemFont(ofSize: 8)], for: .normal)
        // add the hidden picker input
        pickerInput.selectionAction = { [weak self] (newQuantity) in
            if let item = self?.selectedItem {
                self?.viewModel.update(product: item.product, quantity: newQuantity, in: self?.tableView)
            }
        }
        view.insertSubview(pickerInput, at: 0)
        // update the view model with the cart from the coordinator
        if let cart = coordinator?.dataStore.cart {
            viewModel.cart = cart
        }
        viewModel.imageLoader = coordinator?.dataStore.imageLoader
        viewModel.configure(with: tableView)
        viewModel.interactionDelegate = self
        // now bind to its contents to catch when it changes
        viewModel.cart.$contents
            .delay(for: 0.3, scheduler: RunLoop.main)
            .sink { [weak self] items in self?.cartUpdated(reloading: true)}
            .store(in: &cancellables)
        cartUpdated(reloading: false)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        cartUpdated(reloading: true)
    }
    
    // MARK: - CartInteractionDelegate
    
    func didSelect(item: Cart.Item) {
        selectedItem = item
        pickerInput.selectedQuantity = item.quantity
        pickerInput.show()
    }
    
    func requestCheckout() {
        let controller = CheckoutViewController(nibName: nil, bundle: nil)
        controller.coordinator = coordinator
//        controller.prerender { [weak self] in
            controller.modalPresentationStyle = .pageSheet
            self.present(controller, animated: true, completion: nil)
//        }
    }
    
    // MARK: - Private
    
    private func cartUpdated(reloading: Bool) {
        if viewModel.cart.contents.count > 0 {
            emptyCartContainerView.isHidden = true
            tableView.isHidden = false
            tabBarItem.badgeValue = "●"
        }
        else {
            emptyCartContainerView.isHidden = false
            tableView.isHidden = true
            tabBarItem.badgeValue = nil
        }
        if reloading {
            tableView.reloadData()
        }
    }
}
