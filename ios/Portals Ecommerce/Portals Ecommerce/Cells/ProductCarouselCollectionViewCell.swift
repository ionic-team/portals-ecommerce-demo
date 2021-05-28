import UIKit

class ProductCarouselCollectionViewCell: UICollectionViewCell {
    static let cellIdentifier = "ProductCarouselCell"
    static let priceFormatter: NumberFormatter = {
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        return formatter
    }()
    
    static let fixedWidth: CGFloat = 242 // image width
    static let estimatedHeight: CGFloat = 232 + 44 // image height + labels
    
    @IBOutlet private weak var imageView: UIImageView!
    @IBOutlet private weak var titleLabel: UILabel!
    @IBOutlet private weak var priceLabel: UILabel!
    
    func configure(with product: Product, loader: ProductImageLoaderProtocol?) {
        imageView.image = loader?.imageForProduct(product)
        titleLabel.text = product.title
        priceLabel.text = ProductCollectionViewCell.priceFormatter.string(from: NSNumber(value: Double(product.price) / 100.0))
    }
}
