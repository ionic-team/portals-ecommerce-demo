import Foundation

struct User: Codable {
    enum CodingKeys: String, CodingKey {
        case id, firstName, lastName, email, addresses, creditCards
        
        case imageName = "image"
    }
    
    var id: Int
    var firstName: String
    var lastName: String
    var email: String
    var imageName: String
    var addresses: [Address] = []
    var creditCards: [CreditCard] = []
}
