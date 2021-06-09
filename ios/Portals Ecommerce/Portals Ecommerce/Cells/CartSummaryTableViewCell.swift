import UIKit

class CartSummaryTableViewCell: UITableViewCell {
    static let cellIdentifier = "CartSummaryTableViewCell"
    
    @IBOutlet private weak var separatorHeightConstraint: NSLayoutConstraint!
    @IBOutlet private weak var subtotalLabel: UILabel!
    @IBOutlet private weak var totalDescriptionLabel: UILabel!
    @IBOutlet private weak var totalLabel: UILabel!
    @IBOutlet private weak var checkoutButton: UIButton!
    var checkoutAction: (() -> Void)?

    func configure(with cart: Cart, action: (() -> Void)?) {
        let subtotal = cart.formattedSubtotal ?? ""
        subtotalLabel.text = subtotal
        totalLabel.text = String(format: NSLocalizedString("%@ + Tax", comment: "Estimated cart total"), subtotal)
        checkoutAction = action
    }
    
    @IBAction func checkout(_ sender: Any?) {
        checkoutAction?()
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // adjust the line thickness
        separatorHeightConstraint.constant = 1.0/UIScreen.main.scale
        // style the button
        checkoutButton.setBackgroundImage(tintColor.buttonImageWith(cornerRadius: 8), for: .normal)
        checkoutButton.setTitleColor(.white, for: .normal)
        selectionStyle = .none
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        // Configure the view for the selected state
    }
}
