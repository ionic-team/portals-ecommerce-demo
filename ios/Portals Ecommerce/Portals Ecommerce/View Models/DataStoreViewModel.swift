import Foundation

class DataStoreViewModel: ShopAPIDataProviderProtocol {
    private(set) var imageLoader: ImageLoaderProtocol
    // MARK: - ShopAPIDataProviderProtocol
    private(set) var cart: Cart = Cart()
    var products: [Product] = []
    var user: User
    var userImage: String {
        get {
            return ""
        }
        set {
            
        }
    }
    
    init(with user: User, products: [Product], imageLoader: ImageLoaderProtocol) {
        self.user = user
        self.products = products
        self.imageLoader = imageLoader
    }
}
