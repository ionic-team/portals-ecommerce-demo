import UIKit

class ProductDetailViewController: UIViewController, ApplicationCoordinationParticipant {
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
        addButton.setBackgroundImage(buttonImageWithColor(view.tintColor, radius: 8), for: .normal)
        addButton.setTitleColor(.white, for: .normal)
    }
    
    @IBAction func addToCart(_ sender: Any?) {
        
    }
    
    @IBAction func showHelp(_ sender: Any?) {
        
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
    
    private func buttonImageWithColor(_ color: UIColor, radius: CGFloat) -> UIImage {
        let dimension = (radius * 2) + 1.0
        let image = UIGraphicsImageRenderer(size: CGSize(width: dimension, height: dimension)).image { context in
            let path = UIBezierPath(roundedRect: CGRect(x: 0, y: 0, width: dimension, height: dimension), byRoundingCorners: [.allCorners], cornerRadii: CGSize(width: radius, height: radius))
            color.set()
            path.fill()
        }
        return image.stretchableImage(withLeftCapWidth: Int(ceil(radius)), topCapHeight: Int(ceil(radius)))
    }
}
