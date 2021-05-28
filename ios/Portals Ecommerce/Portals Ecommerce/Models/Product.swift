import UIKit

protocol ProductImageLoaderProtocol {
    func imageForProduct(_ product: Product) -> UIImage?
}

enum ProductCategory: String, Codable {
    case mustHaves = "MustHaves"
    case featured = "Featured"
    case onSale = "Sale"
    case recommended = "Recommended"
}

struct Product: Codable {
    enum CodingKeys: String, CodingKey {
        case id, title, description, price, category
        
        case imageName = "image"
    }
    
    var id: String
    var title: String
    var description: String
    var price: Int
    var imageName: String
    var category: ProductCategory
}
