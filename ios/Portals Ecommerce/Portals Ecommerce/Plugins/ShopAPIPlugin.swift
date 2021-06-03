import Capacitor

enum ShopAPICheckoutStatus: String {
    case completed = "success"
    case canceled = "cancel"
    case failed = "failure"
}

protocol ShopAPIDataProviderProtocol: AnyObject {
    var cart: Cart { get }
    var user: User { get set }
    var userImage: String { get set }
}

protocol ShopAPIActionDelegateProtocol: AnyObject {
    func completeCheckout(with status: ShopAPICheckoutStatus)
}

@objc (IONShopAPIPlugin)
class ShopAPIPlugin: CAPPlugin {
    weak var dataProvider: ShopAPIDataProviderProtocol?
    weak var actionDelegate: ShopAPIActionDelegateProtocol?
    
    @objc func getCart(_ call: CAPPluginCall) {
        if let cart = dataProvider?.cart, let object = encode(CartWrapper(cart)) {
            call.resolve(object)
        }
        call.reject("Cart unavailable!")
    }
    
    @objc func getUserDetails(_ call: CAPPluginCall) {
        if let user = dataProvider?.user, let object = encode(UserWrapper(user)) {
            call.resolve(object)
        }
        call.reject("User unavailable!")
    }
    
    @objc func updateUserDetails(_ call: CAPPluginCall) {
        if let wrapper: UserWrapper = decode(object: call.jsObjectRepresentation) {
            call.resolve()
            dataProvider?.user = wrapper.user
        }
        else {
            call.reject("Invalid user details!")
        }
    }
    
    @objc func checkoutResult(_ call: CAPPluginCall) {
        guard let status = ShopAPICheckoutStatus(rawValue: call.getString("result", "")) else {
            call.reject("Missing result!")
            return
        }
        call.resolve()
        actionDelegate?.completeCheckout(with: status)
    }
    
    @objc func getUserPicture(_ call: CAPPluginCall) {
        call.resolve()
    }
    
    @objc func setUserPicture(_ call: CAPPluginCall) {
        call.resolve()
    }
}

private struct CartWrapper: Encodable {
    var cart: Cart
    
    init(_ cart: Cart) {
        self.cart = cart
    }
}

private struct UserWrapper: Codable {
    var user: User
    
    init(_ user: User) {
        self.user = user
    }
}

private func encode<T: Encodable>(_ object: T) -> JSObject? {
    do {
        let data = try JSONEncoder().encode(object)
        if let json = try JSONSerialization.jsonObject(with: data, options: []) as? JSObject {
            return json
        }
    }
    catch {
        assertionFailure("Failed to encode object to JSON")
    }
    return nil
}

private func decode<T: Decodable>(object: JSObject) -> T? {
    do {
        let data = try JSONSerialization.data(withJSONObject: object, options: [])
        let result = try JSONDecoder().decode(T.self, from: data)
        return result
    }
    catch {
        assertionFailure("Failed to decode object from JSON")
    }
    return nil
}
