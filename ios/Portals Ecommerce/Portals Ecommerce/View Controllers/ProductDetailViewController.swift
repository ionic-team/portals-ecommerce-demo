import UIKit

class ProductDetailViewController: UIViewController, ApplicationCoordinationParticipant {
    var requiresPreloading: Bool { return false }
    
    var product: Product?
    weak var coordinator: ApplicationCoordinator? {
        didSet {
            populateView()
        }
    }
    
    @IBOutlet private weak var imageView: UIImageView!
    @IBOutlet private weak var titleLabel: UILabel!
    @IBOutlet private weak var priceLabel: UILabel!
    @IBOutlet private weak var descriptionLabel: UILabel!
    @IBOutlet private weak var addButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        addButton.setBackgroundImage(view.tintColor.buttonImageWith(cornerRadius: 8), for: .normal)
        addButton.setTitleColor(.white, for: .normal)
    }
    
    @IBAction func addToCart(_ sender: Any?) {
        guard let product = product else {
            return
        }
        coordinator?.dataStore.cart.add(product: product, quantity: 1)
    }
    
    // MARK: - Internal
    
    private func populateView() {
        guard let product = product else {
            assertionFailure("Product detail has no product!")
            return
        }
        imageView.image = coordinator?.dataStore.imageLoader.imageFor(product.imageName)
        titleLabel.text = product.title
        priceLabel.text = product.formattedPrice
        descriptionLabel.text = product.description
        navigationItem.title = product.title
    }
}
