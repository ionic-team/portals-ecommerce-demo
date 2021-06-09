import UIKit

protocol CartInteractionDelegate: AnyObject {
    func didSelect(item: Cart.Item)
    func requestCheckout()
}

@objc class CartViewModel: NSObject, UITableViewDataSource, UITableViewDelegate {
    var cart: Cart = Cart()
    var imageLoader: ImageLoaderProtocol?
    weak var interactionDelegate: CartInteractionDelegate?
    
    // MARK: - Public
    
    func configure(with tableView: UITableView) {
        tableView.register(UINib(nibName: "CartItemTableViewCell", bundle: nil),
                           forCellReuseIdentifier: CartItemTableViewCell.cellIdentifier)
        tableView.register(UINib(nibName: "CartSummaryTableViewCell", bundle: nil),
                           forCellReuseIdentifier: CartSummaryTableViewCell.cellIdentifier)
        tableView.dataSource = self
        tableView.delegate = self
        tableView.rowHeight = UITableView.automaticDimension
        tableView.directionalLayoutMargins = .zero
        tableView.layoutMargins = .zero
    }
    
    func update(product: Product, quantity: UInt, in tableView: UITableView?) {
        if let row = cart.contents.firstIndex(where: { $0.product.id == product.id }) {
            cart.update(product: product, quantity: quantity)
            if quantity == 0 {
                tableView?.deleteRows(at: [IndexPath(row: row, section: 0)], with: .fade)
            }
        }
    }
    
    // MARK: - UITableViewDataSource
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        cart.contents.count + 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let isSummary = (indexPath.row == cart.contents.count)
        let cellIdentifier = isSummary ? CartSummaryTableViewCell.cellIdentifier : CartItemTableViewCell.cellIdentifier
        let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath)
        
        if let itemCell = cell as? CartItemTableViewCell {
            itemCell.configure(with: cart.contents[indexPath.row], loader: imageLoader)
        }
        else if let summaryCell = cell as? CartSummaryTableViewCell {
            summaryCell.configure(with: cart) { [weak self] in
                self?.interactionDelegate?.requestCheckout()
            }
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return (indexPath.row < cart.contents.count)
    }
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            let item = cart.contents[indexPath.row]
            cart.update(product: item.product, quantity: 0)
            tableView.deleteRows(at: [indexPath], with: .fade)
        }
    }
    
    // MARK: - UITableViewDelegate
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: false)
        if indexPath.row < cart.contents.count {
            interactionDelegate?.didSelect(item: cart.contents[indexPath.row])
        }
    }
}
