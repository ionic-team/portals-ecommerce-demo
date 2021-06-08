import UIKit

@objc class CartViewModel: NSObject, UITableViewDataSource {
    var cart: Cart = Cart()
    var imageLoader: ImageLoaderProtocol?
    
    // MARK: - Public
    
    func configure(with tableView: UITableView) {
        tableView.register(UINib(nibName: "CartItemTableViewCell", bundle: nil),
                           forCellReuseIdentifier: CartItemTableViewCell.cellIdentifier)
        tableView.register(UINib(nibName: "CartSummaryTableViewCell", bundle: nil),
                           forCellReuseIdentifier: CartSummaryTableViewCell.cellIdentifier)
        tableView.dataSource = self
        tableView.rowHeight = UITableView.automaticDimension
        tableView.directionalLayoutMargins = .zero
        tableView.layoutMargins = .zero
    }
    
    // MARK: - UITableViewDataSource
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        cart.contents.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let isSummary = (indexPath.row == cart.contents.count)
        let cellIdentifier = isSummary ? CartSummaryTableViewCell.cellIdentifier : CartItemTableViewCell.cellIdentifier
        let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath)
        
        if let itemCell = cell as? CartItemTableViewCell {
            itemCell.configure(with: cart.contents[indexPath.row], loader: imageLoader)
        }
        else if let _ = cell as? CartSummaryTableViewCell {
            
        }
        return cell
    }
}
