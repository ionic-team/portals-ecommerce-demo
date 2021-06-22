import Foundation

class DataStoreViewModel: ShopAPIDataProviderProtocol {
    private(set) var imageLoader: ImageLoaderProtocol
    // MARK: - ShopAPIDataProviderProtocol
    private(set) var cart: Cart = Cart()
    var products: [Product] = []
    var user: User
    var userImage: String {
        get {
            if let override = userImageOverride {
                return override
            }
            let image = imageLoader.imageFor(user.imageName)
            if let data = image?.jpegData(compressionQuality: 1) {
                return "data:image/jpeg;base64,\(data.base64EncodedString())"
            }
            return ""
        }
        set {
            userImageOverride = newValue
        }
    }
    private var userImageOverride: String?
    
    init(with user: User, products: [Product], imageLoader: ImageLoaderProtocol) {
        self.user = user
        self.products = products
        self.imageLoader = imageLoader
    }
}
