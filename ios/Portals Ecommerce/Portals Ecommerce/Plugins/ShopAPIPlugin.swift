import Capacitor
import Combine

@objc (IONShopAPIPlugin)
class ShopAPIPlugin: CAPPlugin {
    private lazy var encoder = JSONEncoder()
    private lazy var decoder = JSONDecoder()
    
    @objc func getCart(_ call: CAPPluginCall) {
        let cart = ShopAPI.dataStore.cart
        guard let cartObject = try? encoder.encodeJsObject(cart) else {
            return call.reject("Cart unavailable!")
        }
        call.resolve(cartObject)
    }
    
    @objc func getUserDetails(_ call: CAPPluginCall) {
        let user = ShopAPI.dataStore.user
        guard let userObject = try? encoder.encodeJsObject(user) else {
            return call.reject("User unavailable!")
        }
        call.resolve(userObject)
    }
    
    @objc func updateUserDetails(_ call: CAPPluginCall) {
        guard let user = try? decoder.decodeJsObject(User.self, from: call.jsObjectRepresentation) else {
           return call.reject("Invalid user details!")
        }
        call.resolve()
        ShopAPI.dataStore.user = user
    }
    
    @objc func checkoutResult(_ call: CAPPluginCall) {
        guard let status = ShopAPI.CheckoutStatus(rawValue: call.getString("result", "")) else {
            return call.reject("Missing result!")
        }
        call.resolve()
        ShopAPI.checkoutStatusSubject.send(status)
    }
    
    @objc func getUserPicture(_ call: CAPPluginCall) {
        let picture = ShopAPI.dataStore.userImage
        call.resolve(["picture": picture])
    }
    
    @objc func setUserPicture(_ call: CAPPluginCall) {
        if let picture = call.getString("picture") {
            ShopAPI.dataStore.userImage = picture
        }
        call.resolve()
    }
}

public enum ShopAPI {
    enum CheckoutStatus: String {
        case completed = "success"
        case canceled = "cancel"
        case failed = "failure"
    }
    
    static let dataStore: DataStoreViewModel = {
        guard
            let url = Bundle.main.url(forResource: "data", withExtension: ".json"),
            let data = try? Data(contentsOf: url),
            let demo = try? JSONDecoder().decode(DemoData.self, from: data)
        else { fatalError("Failed to load demo JSON")}
        
        return DataStoreViewModel(with: demo.user, products: demo.products, imageLoader: ImageLoader())
    }()
    
    fileprivate static let checkoutStatusSubject = PassthroughSubject<CheckoutStatus, Never>()
    static var checkoutStatusPublisher: AnyPublisher<CheckoutStatus, Never> {
        checkoutStatusSubject.eraseToAnyPublisher()
    }
}
