import UIKit

protocol ImageLoaderProtocol {
    func imageFor(_ imageName: String) -> UIImage?
}

enum ProductCategory: String, Codable {
    case mustHaves = "MustHaves"
    case featured = "Featured"
    case onSale = "Sale"
    case recommended = "Recommended"
    
    var title: String {
        switch self {
        case .mustHaves:
            return "Must Haves, Bestsellers & More"
        default:
            return "Products"
        }
    }
}

struct Product: Codable, Equatable {
    enum CodingKeys: String, CodingKey {
        case id, title, description, price, category
        
        case imageName = "image"
    }
    
    static let priceFormatter: NumberFormatter = {
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        formatter.maximumFractionDigits = 0
        return formatter
    }()
    
    var formattedPrice: String? {
        return Product.priceFormatter.string(from: NSNumber(value: price))
    }
    
    var id: Int
    var title: String
    var description: String
    var price: Int
    var imageName: String
    var category: ProductCategory
}
