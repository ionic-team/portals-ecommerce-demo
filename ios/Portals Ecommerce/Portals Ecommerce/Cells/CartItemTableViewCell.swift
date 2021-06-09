import UIKit

class CartItemTableViewCell: UITableViewCell {
    static let cellIdentifier = "CartItemTableViewCell"
    
    @IBOutlet private weak var productImageView: UIImageView!
    @IBOutlet private weak var titleLabel: UILabel!
    @IBOutlet private weak var quantityLabel: UILabel!
    @IBOutlet private weak var priceLabel: UILabel!
    
    func configure(with item: Cart.Item, loader: ImageLoaderProtocol?) {
        productImageView.image = loader?.imageFor(item.product.imageName)
        titleLabel.text = item.product.title
        priceLabel.text = item.product.formattedPrice
        quantityLabel.text = String(format: NSLocalizedString("Qty %d", comment: "Item quantity in cart"), Int(item.quantity))
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        directionalLayoutMargins = .zero
        layoutMargins = .zero
        contentView.directionalLayoutMargins = .zero
        contentView.layoutMargins = .zero
        selectionStyle = .none
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
}
