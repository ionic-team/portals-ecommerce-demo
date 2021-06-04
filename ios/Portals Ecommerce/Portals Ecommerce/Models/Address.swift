import Foundation

struct Address: Codable {
    enum CodingKeys: String, CodingKey {
        case id, street, city, state
        
        case postalCode = "postal"
        case isPreferred = "preferred"
    }
    
    var id: Int
    var street: String
    var city: String
    var state: String
    var postalCode: String
    var isPreferred: Bool = false
}
