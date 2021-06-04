import Foundation

class Cart {
    // scoped cart item
    struct Item {
        var product: Product
        var quantity: UInt
    }
    
    // properties
    private(set) var id: Int = Int.random(in: 100...1000)
    private(set) var contents: [Item] = []
    
    func add(product: Product, quantity: UInt) {
        // do we already have this product?
        let index = contents.firstIndex { item in
            item.product == product
        }
        if let index = index {
            update(product: product, quantity: quantity + contents[index].quantity)
        }
        else {
            update(product: product, quantity: quantity)
        }
    }
    
    func update(product: Product, quantity: UInt) {
        // do we already have this product?
        let index = contents.firstIndex { item in
            item.product == product
        }
        // if not, insert it
        guard let index = index else {
            if quantity > 0 {
                contents.append(Item(product: product, quantity: quantity))
            }
            return
        }
        // update the existing item
        if quantity > 0 {
            contents[index].quantity = quantity
        }
        else {
            contents.remove(at: index)
        }
    }
}

extension Cart: Encodable {
    // keys for Encodable support
    enum CodingKeys: String, CodingKey {
        case id, subTotal, basket
    }
    enum BasketKeys: String, CodingKey {
        case productId, quantity
    }
    // Encodable support
    func encode(to encoder: Encoder) throws {
        var container = encoder.container(keyedBy: CodingKeys.self)
        try container.encode(id, forKey: .id)
        let total = contents.reduce(0) { $0 + ($1.product.price * Int($1.quantity)) }
        try container.encode(total, forKey: .subTotal)
        
        var cartContents = container.nestedUnkeyedContainer(forKey: .basket)
        for item in contents {
            var basketItem = cartContents.nestedContainer(keyedBy: BasketKeys.self)
            try basketItem.encode(item.product.id, forKey: .productId)
            try basketItem.encode(item.quantity, forKey: .quantity)
        }
    }
}
