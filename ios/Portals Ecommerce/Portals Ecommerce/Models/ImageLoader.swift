import UIKit

class ImageLoader: ProductImageLoaderProtocol {
    private let imageWidth: CGFloat = 500
    private let imageHeight: CGFloat = 500
    
    func imageForProduct(_ product: Product) -> UIImage? {
        if let image = UIImage(named: product.imageName) {
            return image
        }
        // generate a random color image
        return UIGraphicsImageRenderer(size: CGSize(width: imageWidth, height: imageHeight)).image { context in
            UIColor.random.set()
            UIRectFill(CGRect(x: 0, y: 0, width: imageWidth, height: imageHeight))
        }
    }
}

private extension UIColor {
    static var random: UIColor {
        return UIColor(red: .random(in: 0...1), green: .random(in: 0...1), blue: .random(in: 0...1), alpha: 1.0)
    }
}
