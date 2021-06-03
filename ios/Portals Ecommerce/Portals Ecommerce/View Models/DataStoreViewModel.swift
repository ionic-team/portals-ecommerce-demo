import Foundation

class DataStoreViewModel: ShopAPIDataProviderProtocol {
    var products: [Product] = []
    var user: User
    private(set) var cart: Cart = Cart()
    var userImage: String {
        get {
            return ""
        }
        set {
            
        }
    }
    private var loader: ImageLoaderProtocol?
    
    init(with user: User, products: [Product], loader: ImageLoaderProtocol) {
        self.user = user
        self.products = products
        self.loader = loader
    }
}
