import UIKit

class ProductCollectionViewCell: UICollectionViewCell {
    static let cellIdentifier = "ProductGalleryCell"
    static let priceFormatter: NumberFormatter = {
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        return formatter
    }()
    
    enum GallerySize {
        case small, large
        var width: CGFloat {
            switch self {
            case .small:
                return 167
            case .large:
                return 242
            }
        }
        var height: CGFloat {
            switch self {
            case .small:
                return 149
            case .large:
                return 232
            }
        }
    }
    
    static let fixedWidth: CGFloat = 167 // image width
    static let estimatedHeight: CGFloat = 149 + 60 // image height + labels
    
    @IBOutlet var imageView: UIImageView!
    @IBOutlet var titleLabel: UILabel!
    @IBOutlet var priceLabel: UILabel!
    
    func configure(with product: Product, loader: ProductImageLoaderProtocol?) {
        imageView.image = loader?.imageForProduct(product)
        titleLabel.text = product.title
        priceLabel.text = ProductCollectionViewCell.priceFormatter.string(from: NSNumber(value: Double(product.price) / 100.0))
    }
}
