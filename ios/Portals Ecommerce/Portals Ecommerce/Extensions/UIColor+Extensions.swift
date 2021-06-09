import UIKit

extension UIColor {
    internal func buttonImageWith(cornerRadius: CGFloat) -> UIImage {
        let dimension = (cornerRadius * 2) + 1.0
        let image = UIGraphicsImageRenderer(size: CGSize(width: dimension, height: dimension)).image { context in
            let path = UIBezierPath(roundedRect: CGRect(x: 0, y: 0, width: dimension, height: dimension), byRoundingCorners: [.allCorners], cornerRadii: CGSize(width: cornerRadius, height: cornerRadius))
            self.set()
            path.fill()
        }
        return image.stretchableImage(withLeftCapWidth: Int(ceil(cornerRadius)), topCapHeight: Int(ceil(cornerRadius)))
    }
}
