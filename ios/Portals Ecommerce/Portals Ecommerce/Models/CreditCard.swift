import Foundation

struct CreditCard: Codable {
    enum CodingKeys: String, CodingKey {
        case id, company, number
        
        case expiration = "expirationDate"
        case isPreferred = "preferred"
    }
    
    var id: Int
    var company: String
    var number: String
    var expiration: String
    var isPreferred: Bool = false
}
