import UIKit

class ProductDetailViewController: UIViewController {
    var product: Product?
    
    @IBOutlet private weak var imageView: UIImageView!
    @IBOutlet private weak var titleLabel: UILabel!
    @IBOutlet private weak var priceLabel: UILabel!
    @IBOutlet private weak var descriptionLabel: UILabel!
    @IBOutlet private weak var addButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        populateView()
        // style the button
        addButton.setBackgroundImage(view.tintColor.buttonImageWith(cornerRadius: 8), for: .normal)
        addButton.setTitleColor(.white, for: .normal)
    }
    
    @IBAction func addToCart(_ sender: Any?) {
        guard let product = product else {
            return
        }
        ShopAPI.dataStore.cart.add(product: product, quantity: 1)
    }
    
    @IBAction func showHelp(_ sender: Any?) {
        let controller = HelpPageViewController(nibName: nil, bundle: nil)
        self.navigationController?.pushViewController(controller, animated: true)
    }
    
    // MARK: - Internal
    
    private func populateView() {
        guard let product = product else {
            assertionFailure("Product detail has no product!")
            return
        }
        imageView.image = ShopAPI.dataStore.imageLoader.imageFor(product.imageName)
        titleLabel.text = product.title
        priceLabel.text = product.formattedPrice
        descriptionLabel.text = product.description
        navigationItem.title = product.title
    }
}
