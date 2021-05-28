import UIKit

class ImageLoader: ProductImageLoaderProtocol {
    private var imageCache: [String:UIImage] = [:]
    private let imageWidth: CGFloat = 500
    private let imageHeight: CGFloat = 500
    
    func imageForProduct(_ product: Product) -> UIImage? {
        if let image = imageCache[product.id] {
            return image
        }
        let image = UIGraphicsImageRenderer(size: CGSize(width: imageWidth, height: imageHeight)).image { context in
            UIColor.random.set()
            UIRectFill(CGRect(x: 0, y: 0, width: imageWidth, height: imageHeight))
        }
        imageCache[product.id] = image
        return image
    }
}

private extension UIColor {
    static var random: UIColor {
        return UIColor(red: .random(in: 0...1), green: .random(in: 0...1), blue: .random(in: 0...1), alpha: 1.0)
    }
}
