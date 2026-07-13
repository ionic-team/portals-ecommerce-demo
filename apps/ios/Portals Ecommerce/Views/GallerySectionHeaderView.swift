import UIKit

final class GallerySectionHeaderView: UICollectionReusableView {
    static let viewIdentifier = "GallerySectionHeaderView"
    
    @IBOutlet private weak var label: UILabel!
    
    var title: String {
        get {
            return label.text ?? ""
        }
        set {
            label.text = newValue
        }
    }
}
