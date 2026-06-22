import UIKit

class ProductCollectionViewCell: UICollectionViewCell {
    static let cellIdentifier = "ProductGalleryCell"
    
    static let fixedWidth: CGFloat = 167 // image width
    static let estimatedHeight: CGFloat = 149 + 60 // image height + labels
    
    @IBOutlet private weak var imageView: UIImageView!
    @IBOutlet private weak var titleLabel: UILabel!
    @IBOutlet private weak var priceLabel: UILabel!
    
    func configure(with product: Product, loader: ImageLoaderProtocol?) {
        imageView.image = loader?.imageFor(product.imageName)
        titleLabel.text = product.title
        priceLabel.text = product.formattedPrice
    }
}
