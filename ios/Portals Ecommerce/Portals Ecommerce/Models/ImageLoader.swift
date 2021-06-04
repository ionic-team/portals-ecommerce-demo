import UIKit

class ImageLoader: ImageLoaderProtocol {
    private let imageWidth: CGFloat = 500
    private let imageHeight: CGFloat = 500
    private var generatedImageCache: [String:UIImage] = [:]
    
    func imageFor(_ imageName: String) -> UIImage? {
        if let image = UIImage(named: imageName) {
            return image
        }
        // generate a random color image
        if let image = generatedImageCache[imageName] {
            return image
        }
        generatedImageCache[imageName] = UIGraphicsImageRenderer(size: CGSize(width: imageWidth, height: imageHeight)).image { context in
            UIColor.random.set()
            UIRectFill(CGRect(x: 0, y: 0, width: imageWidth, height: imageHeight))
        }
        return generatedImageCache[imageName]!
    }
}

private extension UIColor {
    static var random: UIColor {
        return UIColor(red: .random(in: 0...1), green: .random(in: 0...1), blue: .random(in: 0...1), alpha: 1.0)
    }
}
